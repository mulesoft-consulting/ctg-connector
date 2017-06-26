package org.mule.modules.ctg.connect;

import javax.resource.ResourceException;
import javax.resource.cci.Connection;
import javax.resource.cci.ConnectionFactory;
import javax.resource.cci.ConnectionSpec;

import org.mule.api.transaction.Transaction;
import org.mule.api.transaction.TransactionException;
import org.mule.modules.ctg.ra.ResourceAdapterUtils;
import org.mule.modules.ctg.xa.CTGXAResourceWrapper;
import org.mule.transaction.TransactionCoordination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.connector2.cics.ECIManagedConnectionFactory;

public class ConnectionManager {

	private static final Logger logger = LoggerFactory.getLogger(ConnectionManager.class);
	private static String CTG_DEFAULT_PORT="1425";
	private static String CTG_LOCAL_URL="local:";
	
	private ConnectionProfile connectionProfile;
	private ConnectionFactory connectionFactory;
	private Connection cachedConnection;
	
	public ConnectionManager(ConnectionProfile connectionProfile) throws ResourceException {
		init(connectionProfile);
	}
	
	public Connection getConnection() throws ResourceException, TransactionException {
		return getBoundConnection(null);
	}
	
	public boolean isConnected() {
		return cachedConnection != null;
	}
	
	public String connectionId() {
		return String.valueOf(cachedConnection.hashCode());
	}
	
	public void disconnect() {
		try {
			cachedConnection.close();
		} catch (ResourceException e) {
			// TODO Auto-generated catch block
			logger.error("disconnect(): ", e);
		}
	}
	
	public Connection getConnection(ConnectionSpec connectionSpec) throws ResourceException, TransactionException {
		return getBoundConnection(connectionSpec);
	}
	
	public ConnectionProfile getConnectionProfile() {
		return connectionProfile;
	}
	
	protected Connection getBoundConnection(ConnectionSpec connectionSpec) throws ResourceException, TransactionException {
		Transaction tx = TransactionCoordination.getInstance().getTransaction();
		
		if (tx == null) {
			  return getCachedConnection(connectionSpec);
		} else {
	         if (tx.hasResource(getConnectionFactory())) {
	        	 CTGXAResourceWrapper xaWrapper = (CTGXAResourceWrapper) tx.getResource(getConnectionFactory());
	        	 
	        	 return xaWrapper.getConnection();
	         } else {
	        	 Connection conn = getCachedConnection(connectionSpec);
	        	  
	        	 if (tx.isXA()) {
	        		 tx.bindResource(getConnectionFactory(), ResourceAdapterUtils.getXAResource(conn));
	        	 } else {
	        		 tx.bindResource(getConnectionFactory(), conn);
	        	 }
	        	 return conn;
	        }
		}
	}
	
	protected void bindResource(Connection conn) throws ResourceException, TransactionException {
		Transaction tx = TransactionCoordination.getInstance().getTransaction();
		
		if (tx != null) {
			if (tx.isXA()) {
				tx.bindResource(getConnectionFactory(), ResourceAdapterUtils.getXAResource(conn));
			} else {
				tx.bindResource(getConnectionFactory(), conn);
			}
		}
	}
	
	protected void init(ConnectionProfile connectionProfile) throws ResourceException {
		setConnectionProfile(connectionProfile);
		getConnectionFactory();
	}
	
	protected void setConnectionProfile(ConnectionProfile connectionProfile) {
		this.connectionProfile = connectionProfile;
	}

	public ConnectionFactory getConnectionFactory() throws ResourceException {
		
		if (connectionFactory == null) {
			//com.ibm.ctg.client.T.setDebugOn(true);
			ECIManagedConnectionFactory managedCF = new  ECIManagedConnectionFactory();
	    	
	    	logger.debug("acquiring connection to: " + 
	    			getConnectionProfile().getCtgURL() + 
	    			" on port " +
	    			getConnectionProfile().getPort());

	    	managedCF.setConnectionURL(getConnectionProfile().isLocal() 
	    			? CTG_LOCAL_URL : getConnectionProfile().getCtgURL());
	    
	    	if (getConnectionProfile().isSecure()) {
	    		managedCF.setKeyRingClass(getConnectionProfile().getKeyStore());
	    		managedCF.setKeyRingPassword(getConnectionProfile().getKeyStorePassword());	
	    	}
	    	
	    	managedCF.setPortNumber( 
	    			getConnectionProfile().getPort() == 0 ? 
	    				CTG_DEFAULT_PORT : 
	    				"" + getConnectionProfile().getPort());
	    	
	    	managedCF.setServerName(getConnectionProfile().getServerName());
	    	managedCF.setUserName(getConnectionProfile().getUserName());
	    	managedCF.setPassword(getConnectionProfile().getPassword());
	    	// hard-coded enabled XA Support
	    	managedCF.setXaSupport("on");
	    	
	    	if (getConnectionProfile().getConenctionTimeout() > 0) {
	    		managedCF.setSocketConnectTimeout(String.valueOf(getConnectionProfile().getConenctionTimeout()));
	    	}
	    	
    		setConnectionFactory((ConnectionFactory) managedCF.createConnectionFactory());
    		
    		logger.debug("connection ok");
		}
		
		return connectionFactory;
	}

	protected void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}
	
	protected Connection getCachedConnection(ConnectionSpec connectionSpec) throws ResourceException {
		if (this.cachedConnection == null) {
		
			this.cachedConnection = (connectionSpec == null ?
	       		 getConnectionFactory().getConnection() :
	       		 getConnectionFactory().getConnection(connectionSpec));
		}
		
		return this.cachedConnection;
	}
}
