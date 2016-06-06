
package org.mule.modules.ctg.generated.connectivity;

import javax.annotation.Generated;
import org.mule.devkit.internal.connection.management.ConnectionManagementConnectionKey;

@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.8.0", date = "2016-06-06T12:17:29-04:00", comments = "Build UNNAMED.2762.e3b1307")
public class ConnectionManagementConfigCTGConnectorConnectionKey implements ConnectionManagementConnectionKey
{

    /**
     * 
     */
    private String host;
    /**
     * 
     */
    private String port;
    /**
     * 
     */
    private String serverName;
    /**
     * 
     */
    private String username;
    /**
     * 
     */
    private String password;

    public ConnectionManagementConfigCTGConnectorConnectionKey(String host, String port, String serverName, String username, String password) {
        this.host = host;
        this.port = port;
        this.serverName = serverName;
        this.username = username;
        this.password = password;
    }

    /**
     * Sets port
     * 
     * @param value Value to set
     */
    public void setPort(String value) {
        this.port = value;
    }

    /**
     * Retrieves port
     * 
     */
    public String getPort() {
        return this.port;
    }

    /**
     * Sets username
     * 
     * @param value Value to set
     */
    public void setUsername(String value) {
        this.username = value;
    }

    /**
     * Retrieves username
     * 
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Sets host
     * 
     * @param value Value to set
     */
    public void setHost(String value) {
        this.host = value;
    }

    /**
     * Retrieves host
     * 
     */
    public String getHost() {
        return this.host;
    }

    /**
     * Sets serverName
     * 
     * @param value Value to set
     */
    public void setServerName(String value) {
        this.serverName = value;
    }

    /**
     * Retrieves serverName
     * 
     */
    public String getServerName() {
        return this.serverName;
    }

    /**
     * Sets password
     * 
     * @param value Value to set
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Retrieves password
     * 
     */
    public String getPassword() {
        return this.password;
    }

    @Override
    public int hashCode() {
        int result = ((this.host!= null)?this.host.hashCode(): 0);
        result = ((31 *result)+((this.port!= null)?this.port.hashCode(): 0));
        result = ((31 *result)+((this.serverName!= null)?this.serverName.hashCode(): 0));
        result = ((31 *result)+((this.username!= null)?this.username.hashCode(): 0));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConnectionManagementConfigCTGConnectorConnectionKey)) {
            return false;
        }
        ConnectionManagementConfigCTGConnectorConnectionKey that = ((ConnectionManagementConfigCTGConnectorConnectionKey) o);
        if (((this.host!= null)?(!this.host.equals(that.host)):(that.host!= null))) {
            return false;
        }
        if (((this.port!= null)?(!this.port.equals(that.port)):(that.port!= null))) {
            return false;
        }
        if (((this.serverName!= null)?(!this.serverName.equals(that.serverName)):(that.serverName!= null))) {
            return false;
        }
        if (((this.username!= null)?(!this.username.equals(that.username)):(that.username!= null))) {
            return false;
        }
        return true;
    }

}
