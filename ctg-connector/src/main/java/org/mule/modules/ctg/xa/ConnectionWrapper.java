package org.mule.modules.ctg.xa;

import javax.resource.ResourceException;
import javax.resource.cci.Connection;

import org.mule.api.transaction.TransactionException;
import org.mule.transaction.XaTransaction.MuleXaObject;

public class ConnectionWrapper implements MuleXaObject {

	private Connection connection;
	
	public ConnectionWrapper(Connection connection) {
		setConnection(connection);
	}
	
	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		getConnection().close();
	}

	@Override
	public boolean delist() throws Exception {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean enlist() throws TransactionException {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Object getTargetObject() {
		// TODO Auto-generated method stub
		try {
			return getConnection();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean isReuseObject() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setReuseObject(boolean arg0) {
		// TODO Auto-generated method stub

	}

	public void commit() throws ResourceException {
		getConnection().getLocalTransaction().commit();
	}
	
	public void rollback() throws ResourceException {
		getConnection().getLocalTransaction().rollback();
	}
}
