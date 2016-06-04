package org.mule.modules.ctg.config;

import javax.inject.Inject;
import javax.resource.ResourceException;
import org.mule.api.ConnectionException;
import org.mule.api.ConnectionExceptionCode;
import org.mule.api.MuleContext;
import org.mule.api.annotations.components.ConnectionManagement;
import org.mule.api.annotations.display.FriendlyName;
import org.mule.api.annotations.display.Password;
import org.mule.api.annotations.display.Placement;
import org.mule.api.annotations.param.ConnectionKey;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.Optional;
import org.mule.modules.ctg.connect.ConnectionManager;
import org.mule.modules.ctg.connect.ConnectionProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.mule.api.annotations.Configurable;
//import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.Connect;
import org.mule.api.annotations.ConnectionIdentifier;
import org.mule.api.annotations.Disconnect;
import org.mule.api.annotations.TestConnectivity;
import org.mule.api.annotations.ValidateConnection;

@ConnectionManagement(friendlyName = "Configuration")
public class ConnectorConfig {
	private static final Logger logger = LoggerFactory.getLogger(ConnectorConfig.class);
	
    private ConnectionManager connectionManager;

    @Inject
    private MuleContext muleContext;
    
    @Configurable
    @FriendlyName("Key Store Location")
    @Optional
    @Placement(group = "SSL Settings", order=1)
    private String keyStoreLocation;
    
    @Configurable
    @FriendlyName("Key Store Password")
    @Password
    @Optional
    @Placement(group = "SSL Settings", order=1)
    private String keyStorePassword;
    
    public ConnectionManager getConnectionManager() {
		return connectionManager;
	}

    public void setMuleContext(MuleContext muleContext) {
    	this.muleContext = muleContext;
    }
    
	public String getKeyStoreLocation() {
		return keyStoreLocation;
	}

	public void setKeyStoreLocation(String keyStoreLocation) {
		this.keyStoreLocation = keyStoreLocation;
	}

	public String getKeyStorePassword() {
		return keyStorePassword;
	}

	public void setKeyStorePassword(String keyStorePassword) {
		this.keyStorePassword = keyStorePassword;
	}

	protected void setConnectionManager(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}

	@Connect
    @TestConnectivity(active=true)
    public void connect(
    		@ConnectionKey @FriendlyName("CTG Host") String host,
    		@ConnectionKey @FriendlyName("CTG Port") String port, 
    		@ConnectionKey @FriendlyName("CTG Server Name") String serverName,
    		@ConnectionKey String username, 
    		@Password String password) throws ConnectionException {
    	
    	try {
    		ConnectionProfile profile = new ConnectionProfile();
    		
    		profile.setHost(host);
    		profile.setPort(Integer.parseInt(port));
    		profile.setServerName(serverName);
    		profile.setUserName(username);
    		profile.setPassword(password);
    		profile.setMuleContext(muleContext);
    		
    		profile.setSecure(getKeyStoreLocation() != null && getKeyStorePassword() != null);
    		
    		if (profile.isSecure()) {
    			profile.setKeyStore(getKeyStoreLocation());
    			profile.setKeyStorePassword(getKeyStorePassword());
    		}
    		
    		ConnectionManager connMgr = new ConnectionManager(profile);
    		
    		logger.debug(connMgr.getConnection().getMetaData().getEISProductName());
    
    		setConnectionManager(connMgr);
    		
    		if (!isConnected()) {
    			throw new ConnectionException(ConnectionExceptionCode.UNKNOWN, "CTG", "conection unavailable");
    		}
    	} catch(ResourceException e) {
    		throw new ConnectionException(ConnectionExceptionCode.UNKNOWN, "CTG", e.getMessage());
    	}
    }
    
    /**
     * Disconnect
     */
    @Disconnect
    public void disconnect() {
        setConnectionManager(null);
    }
 
    /**
     * Are we connected
     */
    @ValidateConnection
    public boolean isConnected() {
    	
    	if (getConnectionManager() != null) { 
	    	try {
	    		return getConnectionManager().getConnection() != null;
	    	} catch (ResourceException e) {
	    		logger.error("isConnection(); failed: ", e);
	    	}
    	}
    	
	    return false;
    }
 
    /**
     * Id used only when debuging.
     */
    @ConnectionIdentifier
    public String connectionId() {
    	try {
    		return getConnectionManager().getConnection().getMetaData().getEISProductName();
    	} catch(ResourceException e) {
    		
    	}
    	return null;
    }
}