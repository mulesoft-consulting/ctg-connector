package org.mule.modules.ctg.xa;

import javax.resource.ResourceException;
import javax.resource.cci.Connection;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

public class CTGXAResourceWrapper implements XAResource {

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
		delegate.commit(arg0, arg1);
	}

	@Override
	public void end(Xid arg0, int arg1) throws XAException {
		// TODO Auto-generated method stub
		delegate.end(arg0, arg1);
	}

	@Override
	public void forget(Xid arg0) throws XAException {
		// TODO Auto-generated method stub
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
		return delegate.isSameRM(arg0);
	}

	@Override
	public int prepare(Xid arg0) throws XAException {
		// TODO Auto-generated method stub
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
	}
}
