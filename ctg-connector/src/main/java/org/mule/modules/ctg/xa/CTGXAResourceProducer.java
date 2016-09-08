package org.mule.modules.ctg.xa;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.StringRefAddr;
import javax.transaction.xa.XAResource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bitronix.tm.internal.BitronixRuntimeException;
import bitronix.tm.internal.XAResourceHolderState;
import bitronix.tm.recovery.RecoveryException;
import bitronix.tm.resource.ResourceObjectFactory;
import bitronix.tm.resource.ResourceRegistrar;
import bitronix.tm.resource.common.RecoveryXAResourceHolder;
import bitronix.tm.resource.common.ResourceBean;
import bitronix.tm.resource.common.XAResourceHolder;
import bitronix.tm.resource.common.XAResourceProducer;
import bitronix.tm.resource.common.XAStatefulHolder;

public class CTGXAResourceProducer extends ResourceBean implements XAResourceProducer {

	private static final Logger log = LoggerFactory.getLogger(CTGXAResourceProducer.class);
	
	private static final ConcurrentMap<String, CTGXAResourceProducer> producers = new ConcurrentHashMap<String, CTGXAResourceProducer>();
	private final ConcurrentMap<Integer, CTGXAResourceHolder> xaResourceHolders = new ConcurrentHashMap<Integer, CTGXAResourceHolder>();

	private final AtomicInteger xaResourceHolderCounter = new AtomicInteger();
    private volatile RecoveryXAResourceHolder recoveryXAResourceHolder;
    
    private CTGXAResourceProducer() {
    	setApplyTransactionTimeout(true);
    }
    
    /**
     * Register an XAResource of a cache with BTM. The first time a XAResource is registered a new
     * CTGXAResourceProducer is created to hold it.
     * @param uniqueName the uniqueName of this XAResourceProducer
     * @param xaResource the XAResource to be registered
     */
    public static void registerXAResource(String uniqueName, XAResource xaResource) {
        CTGXAResourceProducer xaResourceProducer = producers.get(uniqueName);
        if (xaResourceProducer == null) {
            xaResourceProducer = new CTGXAResourceProducer();
            xaResourceProducer.setUniqueName(uniqueName);
            // the initial xaResource must be added before init() can be called
            xaResourceProducer.addXAResource(xaResource);

            CTGXAResourceProducer previous = producers.putIfAbsent(uniqueName, xaResourceProducer);
            if (previous == null) {
                xaResourceProducer.init();
            } else {
                previous.addXAResource(xaResource);
            }
        } else {
            xaResourceProducer.addXAResource(xaResource);
        }
    }

    /**
     * Unregister an XAResource of a cache from BTM.
     * @param uniqueName the uniqueName of this XAResourceProducer
     * @param xaResource the XAResource to be registered
     */
    public static void unregisterXAResource(String uniqueName, XAResource xaResource) {
        CTGXAResourceProducer xaResourceProducer = producers.get(uniqueName);

        if (xaResourceProducer != null) {
            boolean found = xaResourceProducer.removeXAResource(xaResource);
            if (!found) {
                log.error("no XAResource " + xaResource + " found in XAResourceProducer with name " + uniqueName);
            }
            if (xaResourceProducer.xaResourceHolders.isEmpty()) {
                xaResourceProducer.close();
                producers.remove(uniqueName);
            }
        } else {
            log.error("no XAResourceProducer registered with name " + uniqueName);
        }
    }


    private void addXAResource(XAResource xaResource) {
        CTGXAResourceHolder xaResourceHolder = new CTGXAResourceHolder(xaResource, this);
        int key = xaResourceHolderCounter.incrementAndGet();

        xaResourceHolders.put(key, xaResourceHolder);
    }

    private boolean removeXAResource(XAResource xaResource) {
        for (Map.Entry<Integer, CTGXAResourceHolder> entry : xaResourceHolders.entrySet()) {
            Integer key = entry.getKey();
            CTGXAResourceHolder xaResourceHolder = entry.getValue();
            if (xaResourceHolder.getXAResource() == xaResource) {
                xaResourceHolders.remove(key);
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public XAResourceHolderState startRecovery() throws RecoveryException {
        if (recoveryXAResourceHolder != null) {
            throw new RecoveryException("recovery already in progress on " + this);
        }

        if (xaResourceHolders.isEmpty()) {
            throw new RecoveryException("no XAResource registered, recovery cannot be done on " + this);
        }

        recoveryXAResourceHolder = new RecoveryXAResourceHolder(xaResourceHolders.values().iterator().next());
        return new XAResourceHolderState(recoveryXAResourceHolder, this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void endRecovery() throws RecoveryException {
        recoveryXAResourceHolder = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFailed(boolean failed) {
        // cache cannot fail as it's not connection oriented
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CTGXAResourceHolder findXAResourceHolder(XAResource xaResource) {
        for (CTGXAResourceHolder xaResourceHolder : xaResourceHolders.values()) {
            if (xaResource == xaResourceHolder.getXAResource()) {
                return xaResourceHolder;
            }
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() {
        try {
            ResourceRegistrar.register(this);
        } catch (RecoveryException ex) {
            throw new BitronixRuntimeException("error recovering " + this, ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        xaResourceHolders.clear();
        xaResourceHolderCounter.set(0);
        ResourceRegistrar.unregister(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CTGXAResourceHolder createPooledConnection(Object xaFactory, ResourceBean bean) throws Exception {
        throw new UnsupportedOperationException("CTG is not connection-oriented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Reference getReference() throws NamingException {
        return new Reference(CTGXAResourceProducer.class.getName(),
                new StringRefAddr("uniqueName", getUniqueName()),
                ResourceObjectFactory.class.getName(), null);
    }

    @Override
    public String toString() {
        return "a CTGXAResourceProducer with uniqueName " + getUniqueName();
    }
}
