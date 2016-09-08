package org.mule.modules.ctg.connect;

public class ConnectionProfile {
	private static String NON_SECURE_URL_PREFIX="tcp://";
	private static String SECURE_URL_PREFIX="ssl://";
	
	private String host;
	private int port;
	private boolean isLocal;
	private boolean isSecure;
	private String serverName;
	private String userName;
	private String password;
	private String keyStore;
	private String keyStorePassword;
	private int conenctionTimeout;
	private int minConnections;
	private int maxConnections;
	
	public int getConenctionTimeout() {
		return conenctionTimeout;
	}
	public void setConenctionTimeout(int conenctionTimeout) {
		this.conenctionTimeout = conenctionTimeout;
	}
	public boolean isSecure() {
		return isSecure;
	}
	public void setSecure(boolean isSecure) {
		this.isSecure = isSecure;
	}
	public String getKeyStore() {
		return keyStore;
	}
	public void setKeyStore(String keyStore) {
		this.keyStore = keyStore;
	}
	public String getKeyStorePassword() {
		return keyStorePassword;
	}
	public void setKeyStorePassword(String keyStorePassword) {
		this.keyStorePassword = keyStorePassword;
	}
	public String getCtgURL() {
		return isSecure() ? SECURE_URL_PREFIX + getHost() : NON_SECURE_URL_PREFIX + getHost();
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public boolean isLocal() {
		return isLocal;
	}
	public void setLocal(boolean isLocal) {
		this.isLocal = isLocal;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getMinConnections() {
		return minConnections;
	}
	public void setMinConnections(int minConnections) {
		this.minConnections = minConnections;
	}
	public int getMaxConnections() {
		return maxConnections;
	}
	public void setMaxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
	}
}
