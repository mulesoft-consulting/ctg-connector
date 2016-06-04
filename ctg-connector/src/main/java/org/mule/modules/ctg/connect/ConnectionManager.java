package org.mule.modules.ctg.connect;

import javax.resource.ResourceException;
import javax.resource.cci.Connection;
import javax.resource.cci.ConnectionFactory;
import javax.resource.cci.ConnectionSpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.connector2.cics.ECIManagedConnectionFactory;

public class ConnectionManager {

	private static final Logger logger = LoggerFactory.getLogger(ConnectionManager.class);
	private static String CTG_DEFAULT_PORT="1425";
	private static String CTG_LOCAL_URL="local:";
	
	private ConnectionProfile connectionProfile;
	private ConnectionFactory connectionFactory;
	
	public ConnectionManager(ConnectionProfile connectionProfile) throws ResourceException {
		init(connectionProfile);
	}
	
	public Connection getConnection() throws ResourceException {
		return getConnectionFactory().getConnection();
	}
	
	public Connection getConnection(ConnectionSpec connectionSpec) throws ResourceException {
		return getConnectionFactory().getConnection(connectionSpec);
	}
	
	protected void init(ConnectionProfile connectionProfile) throws ResourceException {
		setConnectionProfile(connectionProfile);
		getConnectionFactory();
	}
	
	public ConnectionProfile getConnectionProfile() {
		return connectionProfile;
	}

	protected void setConnectionProfile(ConnectionProfile connectionProfile) {
		this.connectionProfile = connectionProfile;
	}

	public ConnectionFactory getConnectionFactory() throws ResourceException {
		
		if (connectionFactory == null) {
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
	    	
    		setConnectionFactory((ConnectionFactory) managedCF.createConnectionFactory());
    		
    		logger.debug("connection ok");
		}
		
		return connectionFactory;
	}

	protected void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}
	
	
}
