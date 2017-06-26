package org.mule.modules.ctg.ra;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

import javax.inject.Inject;
import javax.resource.ResourceException;
import javax.resource.cci.Connection;
import javax.transaction.xa.XAResource;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.mule.api.MuleContext;
import org.mule.api.config.MuleProperties;
import org.mule.modules.ctg.xa.CTGXAResourceProducer;
import org.mule.modules.ctg.xa.CTGXAResourceWrapper;
import org.mule.util.NetworkUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.connector2.cics.ECIConnection;
import com.ibm.connector2.cics.ECIManagedConnection;

public class ResourceAdapterUtils {
	private static final Logger logger = LoggerFactory.getLogger(ResourceAdapterUtils.class);
	private static final ResourceAdapterUtils resourceAdapterUtils = new ResourceAdapterUtils();
	public static final String BITRONIX_SERVER_ID = "mule.bitronix.serverId";
	public static final String CLUSTER_NODE_ID_PROPERTY = MuleProperties.SYSTEM_PROPERTY_PREFIX + "clusterNodeId";
	
	@Inject
    private MuleContext muleContext;
	
	protected static MuleContext getMuleContext() {
		return resourceAdapterUtils.muleContext;
	}
	
	public static XAResource getXAResource(Connection connection) {
		XAResource xaResource = null;
		ECIConnection eciConnection = (ECIConnection) connection;
		
		try {
			Method getXAResourceMethod = eciConnection.getClass().getDeclaredMethod("getManagedConnection");
			getXAResourceMethod.setAccessible(true);
			ECIManagedConnection eciManagedConenction = (ECIManagedConnection) getXAResourceMethod.invoke(eciConnection);
			
			CTGXAResourceWrapper xaResourceWrapper = new CTGXAResourceWrapper(
					connection, eciManagedConenction.getXAResource());
			
			xaResource = xaResourceWrapper;
			
			CTGXAResourceProducer.registerXAResource(createUniqueResorceName(), xaResourceWrapper);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("", e);
		}
		
		return xaResource;
	}
	
	public static String createUniqueResorceName() {
		String configuredBitronixServerId = System.getProperty(BITRONIX_SERVER_ID);
        if (configuredBitronixServerId != null)
        {
            return configuredBitronixServerId;
        }
        else
        {
            try
            {
                InetAddress address = NetworkUtils.getLocalHost();
                String uniqueIdForServer = String.valueOf(Math.abs(new HashCodeBuilder(17, 37).append(address.getHostName()).append(address.getHostName()).append(System.getProperty(CLUSTER_NODE_ID_PROPERTY, "0")).toHashCode()));
                String uniqueResourceId = uniqueIdForServer + "-" + UUID.randomUUID() + "-ctg";
                
                return uniqueResourceId;
            }
            catch (UnknownHostException e)
            {
                throw new RuntimeException(e);
            }
        }
		
	}
}
