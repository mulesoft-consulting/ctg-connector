package org.mule.modules.ctg.xa;

import javax.resource.cci.Connection;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CTGXAResourceWrapper implements XAResource {
	private static final Logger logger = LoggerFactory.getLogger(CTGXAResourceWrapper.class);
	private Connection connection;
	private XAResource delegate;
	
	public CTGXAResourceWrapper(Connection connection, XAResource delegate) {
		this.connection = connection;
		this.delegate = delegate;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	@Override
	public void commit(Xid arg0, boolean arg1) throws XAException {
		// TODO Auto-generated method stub
		debug("Commit Xid: ", arg0, 0);
		delegate.commit(arg0, arg1);
	}

	@Override
	public void end(Xid arg0, int arg1) throws XAException {
		// TODO Auto-generated method stub
		debug("End Xid: ", arg0, arg1);
		delegate.end(arg0, arg1);
	}

	@Override
	public void forget(Xid arg0) throws XAException {
		// TODO Auto-generated method stub
		debug("Forget Xid: ", arg0, 0);
		delegate.forget(arg0);
	}

	@Override
	public int getTransactionTimeout() throws XAException {
		// TODO Auto-generated method stub
		return delegate.getTransactionTimeout();
	}

	@Override
	public boolean isSameRM(XAResource arg0) throws XAException {
		// TODO Auto-generated method stub
		logger.debug("isSameRM: ", arg0);
		return delegate.isSameRM(arg0);
	}

	@Override
	public int prepare(Xid arg0) throws XAException {
		// TODO Auto-generated method stub
		debug("Prepared Xid: ", arg0, 0);
		return delegate.prepare(arg0);
	}

	@Override
	public Xid[] recover(int arg0) throws XAException {
		// TODO Auto-generated method stub
		//return delegate.recover(arg0);
		
		return new Xid[0];
	}

	@Override
	public void rollback(Xid arg0) throws XAException {
		// TODO Auto-generated method stub
		delegate.rollback(arg0);
		debug("Rollback Xid: ", arg0, 0);
	}

	@Override
	public boolean setTransactionTimeout(int arg0) throws XAException {
		// TODO Auto-generated method stub
		return delegate.setTransactionTimeout(arg0);
	}

	@Override
	public void start(Xid arg0, int arg1) throws XAException {
		// TODO Auto-generated method stub
		delegate.start(arg0, arg1);
		debug("Started Xid: ", arg0, arg1);
	}
	
	protected void debug(String msg, Xid xid, int arg) {
		if (logger.isDebugEnabled()) {
			logger.debug(msg, xid.getGlobalTransactionId(), " FLAGS " + getFlags(arg));
		}
	}
	
	protected String getFlags(int tmFlags) {
		String sTmFlags = "";
		
		switch (tmFlags) {
		case XAResource.TMFAIL:
			sTmFlags = "TMFAIL";
			break;
		case XAResource.TMJOIN:
			sTmFlags = "TNJOIN";
			break;
		}
		
		return sTmFlags;
	}
}
