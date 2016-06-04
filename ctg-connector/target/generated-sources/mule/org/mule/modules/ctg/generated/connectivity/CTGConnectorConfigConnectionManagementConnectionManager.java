
package org.mule.modules.ctg.generated.connectivity;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.apache.commons.pool.KeyedObjectPool;
import org.mule.api.MetadataAware;
import org.mule.api.MuleContext;
import org.mule.api.MuleEvent;
import org.mule.api.config.MuleProperties;
import org.mule.api.context.MuleContextAware;
import org.mule.api.devkit.ProcessAdapter;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.devkit.capability.Capabilities;
import org.mule.api.devkit.capability.ModuleCapability;
import org.mule.api.lifecycle.Disposable;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.retry.RetryPolicyTemplate;
import org.mule.common.DefaultResult;
import org.mule.common.DefaultTestResult;
import org.mule.common.Result;
import org.mule.common.TestResult;
import org.mule.common.Testable;
import org.mule.common.metadata.ConnectorMetaDataEnabled;
import org.mule.common.metadata.DefaultMetaDataKey;
import org.mule.common.metadata.MetaData;
import org.mule.common.metadata.MetaDataFailureType;
import org.mule.common.metadata.MetaDataKey;
import org.mule.common.metadata.key.property.TypeDescribingProperty;
import org.mule.common.metadata.property.StructureIdentifierMetaDataModelProperty;
import org.mule.config.PoolingProfile;
import org.mule.devkit.api.lifecycle.LifeCycleManager;
import org.mule.devkit.api.lifecycle.MuleContextAwareManager;
import org.mule.devkit.internal.connection.management.ConnectionManagementConnectionAdapter;
import org.mule.devkit.internal.connection.management.ConnectionManagementConnectionManager;
import org.mule.devkit.internal.connection.management.ConnectionManagementConnectorAdapter;
import org.mule.devkit.internal.connection.management.ConnectionManagementConnectorFactory;
import org.mule.devkit.internal.connection.management.ConnectionManagementProcessTemplate;
import org.mule.devkit.internal.connection.management.UnableToAcquireConnectionException;
import org.mule.devkit.internal.connectivity.ConnectivityTestingErrorHandler;
import org.mule.devkit.internal.metadata.MetaDataGeneratorUtils;
import org.mule.devkit.processor.ExpressionEvaluatorSupport;
import org.mule.modules.ctg.CTGConnector;
import org.mule.modules.ctg.DataSenseResolver;
import org.mule.modules.ctg.config.ConnectorConfig;
import org.mule.modules.ctg.generated.adapters.CTGConnectorConnectionManagementAdapter;
import org.mule.modules.ctg.generated.pooling.DevkitGenericKeyedObjectPool;


/**
 * A {@code CTGConnectorConfigConnectionManagementConnectionManager} is a wrapper around {@link CTGConnector } that adds connection management capabilities to the pojo.
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.8.0", date = "2016-06-03T10:16:30-05:00", comments = "Build UNNAMED.2762.e3b1307")
public class CTGConnectorConfigConnectionManagementConnectionManager
    extends ExpressionEvaluatorSupport
    implements MetadataAware, MuleContextAware, ProcessAdapter<CTGConnectorConnectionManagementAdapter> , Capabilities, Disposable, Initialisable, Testable, ConnectorMetaDataEnabled, ConnectionManagementConnectionManager<ConnectionManagementConfigCTGConnectorConnectionKey, CTGConnectorConnectionManagementAdapter, ConnectorConfig>
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
    private String keyStoreLocation;
    private String keyStorePassword;
    /**
     * Mule Context
     * 
     */
    protected MuleContext muleContext;
    /**
     * Connector Pool
     * 
     */
    private KeyedObjectPool connectionPool;
    protected PoolingProfile poolingProfile;
    protected RetryPolicyTemplate retryPolicyTemplate;
    private final static String MODULE_NAME = "CTG";
    private final static String MODULE_VERSION = "1.0.1-SNAPSHOT";
    private final static String DEVKIT_VERSION = "3.8.0";
    private final static String DEVKIT_BUILD = "UNNAMED.2762.e3b1307";
    private final static String MIN_MULE_VERSION = "3.5.0";

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

    /**
     * Sets keyStoreLocation
     * 
     * @param value Value to set
     */
    public void setKeyStoreLocation(String value) {
        this.keyStoreLocation = value;
    }

    /**
     * Retrieves keyStoreLocation
     * 
     */
    public String getKeyStoreLocation() {
        return this.keyStoreLocation;
    }

    /**
     * Sets keyStorePassword
     * 
     * @param value Value to set
     */
    public void setKeyStorePassword(String value) {
        this.keyStorePassword = value;
    }

    /**
     * Retrieves keyStorePassword
     * 
     */
    public String getKeyStorePassword() {
        return this.keyStorePassword;
    }

    /**
     * Sets muleContext
     * 
     * @param value Value to set
     */
    public void setMuleContext(MuleContext value) {
        this.muleContext = value;
    }

    /**
     * Retrieves muleContext
     * 
     */
    public MuleContext getMuleContext() {
        return this.muleContext;
    }

    /**
     * Sets poolingProfile
     * 
     * @param value Value to set
     */
    public void setPoolingProfile(PoolingProfile value) {
        this.poolingProfile = value;
    }

    /**
     * Retrieves poolingProfile
     * 
     */
    public PoolingProfile getPoolingProfile() {
        return this.poolingProfile;
    }

    /**
     * Sets retryPolicyTemplate
     * 
     * @param value Value to set
     */
    public void setRetryPolicyTemplate(RetryPolicyTemplate value) {
        this.retryPolicyTemplate = value;
    }

    /**
     * Retrieves retryPolicyTemplate
     * 
     */
    public RetryPolicyTemplate getRetryPolicyTemplate() {
        return this.retryPolicyTemplate;
    }

    public void initialise() {
        connectionPool = new DevkitGenericKeyedObjectPool(new ConnectionManagementConnectorFactory(this), poolingProfile);
        if (retryPolicyTemplate == null) {
            retryPolicyTemplate = muleContext.getRegistry().lookupObject(MuleProperties.OBJECT_DEFAULT_RETRY_POLICY_TEMPLATE);
        }
    }

    @Override
    public void dispose() {
        try {
            connectionPool.close();
        } catch (Exception e) {
        }
    }

    public CTGConnectorConnectionManagementAdapter acquireConnection(ConnectionManagementConfigCTGConnectorConnectionKey key)
        throws Exception
    {
        return ((CTGConnectorConnectionManagementAdapter) connectionPool.borrowObject(key));
    }

    public void releaseConnection(ConnectionManagementConfigCTGConnectorConnectionKey key, CTGConnectorConnectionManagementAdapter connection)
        throws Exception
    {
        connectionPool.returnObject(key, connection);
    }

    public void destroyConnection(ConnectionManagementConfigCTGConnectorConnectionKey key, CTGConnectorConnectionManagementAdapter connection)
        throws Exception
    {
        connectionPool.invalidateObject(key, connection);
    }

    /**
     * Returns true if this module implements such capability
     * 
     */
    public boolean isCapableOf(ModuleCapability capability) {
        if (capability == ModuleCapability.LIFECYCLE_CAPABLE) {
            return true;
        }
        if (capability == ModuleCapability.CONNECTION_MANAGEMENT_CAPABLE) {
            return true;
        }
        return false;
    }

    @Override
    public<P >ProcessTemplate<P, CTGConnectorConnectionManagementAdapter> getProcessTemplate() {
        return new ConnectionManagementProcessTemplate(this, muleContext);
    }

    @Override
    public ConnectionManagementConfigCTGConnectorConnectionKey getDefaultConnectionKey() {
        return new ConnectionManagementConfigCTGConnectorConnectionKey(getHost(), getPort(), getServerName(), getUsername(), getPassword());
    }

    @Override
    public ConnectionManagementConfigCTGConnectorConnectionKey getEvaluatedConnectionKey(MuleEvent event)
        throws Exception
    {
        if (event!= null) {
            final String _transformedHost = ((String) evaluateAndTransform(muleContext, event, this.getClass().getDeclaredField("host").getGenericType(), null, getHost()));
            if (_transformedHost == null) {
                throw new UnableToAcquireConnectionException("Parameter host in method connect can't be null because is not @Optional");
            }
            final String _transformedPort = ((String) evaluateAndTransform(muleContext, event, this.getClass().getDeclaredField("port").getGenericType(), null, getPort()));
            if (_transformedPort == null) {
                throw new UnableToAcquireConnectionException("Parameter port in method connect can't be null because is not @Optional");
            }
            final String _transformedServerName = ((String) evaluateAndTransform(muleContext, event, this.getClass().getDeclaredField("serverName").getGenericType(), null, getServerName()));
            if (_transformedServerName == null) {
                throw new UnableToAcquireConnectionException("Parameter serverName in method connect can't be null because is not @Optional");
            }
            final String _transformedUsername = ((String) evaluateAndTransform(muleContext, event, this.getClass().getDeclaredField("username").getGenericType(), null, getUsername()));
            if (_transformedUsername == null) {
                throw new UnableToAcquireConnectionException("Parameter username in method connect can't be null because is not @Optional");
            }
            final String _transformedPassword = ((String) evaluateAndTransform(muleContext, event, this.getClass().getDeclaredField("password").getGenericType(), null, getPassword()));
            if (_transformedPassword == null) {
                throw new UnableToAcquireConnectionException("Parameter password in method connect can't be null because is not @Optional");
            }
            return new ConnectionManagementConfigCTGConnectorConnectionKey(_transformedHost, _transformedPort, _transformedServerName, _transformedUsername, _transformedPassword);
        }
        return getDefaultConnectionKey();
    }

    public String getModuleName() {
        return MODULE_NAME;
    }

    public String getModuleVersion() {
        return MODULE_VERSION;
    }

    public String getDevkitVersion() {
        return DEVKIT_VERSION;
    }

    public String getDevkitBuild() {
        return DEVKIT_BUILD;
    }

    public String getMinMuleVersion() {
        return MIN_MULE_VERSION;
    }

    @Override
    public ConnectionManagementConfigCTGConnectorConnectionKey getConnectionKey(MessageProcessor messageProcessor, MuleEvent event)
        throws Exception
    {
        return getEvaluatedConnectionKey(event);
    }

    @Override
    public ConnectionManagementConnectionAdapter newConnection() {
        ConnectorConfigCTGConnectorAdapter connection = new ConnectorConfigCTGConnectorAdapter();
        connection.setKeyStoreLocation(getKeyStoreLocation());
        connection.setKeyStorePassword(getKeyStorePassword());
        return connection;
    }

    @Override
    public ConnectionManagementConnectorAdapter newConnector(ConnectionManagementConnectionAdapter<ConnectorConfig, ConnectionManagementConfigCTGConnectorConnectionKey> connection) {
        CTGConnectorConnectionManagementAdapter connector = new CTGConnectorConnectionManagementAdapter();
        connector.setConfig(connection.getStrategy());
        return connector;
    }

    public ConnectionManagementConnectionAdapter getConnectionAdapter(ConnectionManagementConnectorAdapter adapter) {
        CTGConnectorConnectionManagementAdapter connector = ((CTGConnectorConnectionManagementAdapter) adapter);
        ConnectionManagementConnectionAdapter strategy = ((ConnectionManagementConnectionAdapter) connector.getConfig());
        return strategy;
    }

    public TestResult test() {
        try {
            ConnectorConfigCTGConnectorAdapter strategy = ((ConnectorConfigCTGConnectorAdapter) newConnection());
            MuleContextAwareManager.setMuleContext(strategy, this.muleContext);
            LifeCycleManager.executeInitialiseAndStart(strategy);
            ConnectionManagementConnectorAdapter connectorAdapter = newConnector(strategy);
            MuleContextAwareManager.setMuleContext(connectorAdapter, this.muleContext);
            LifeCycleManager.executeInitialiseAndStart(connectorAdapter);
            strategy.test(getDefaultConnectionKey());
            return new DefaultTestResult(Result.Status.SUCCESS);
        } catch (Exception e) {
            return ((DefaultTestResult) ConnectivityTestingErrorHandler.buildFailureTestResult(e));
        }
    }

    @Override
    public Result<List<MetaDataKey>> getMetaDataKeys() {
        CTGConnectorConnectionManagementAdapter connection = null;
        ConnectionManagementConfigCTGConnectorConnectionKey key = getDefaultConnectionKey();
        try {
            connection = acquireConnection(key);
            try {
                List<MetaDataKey> gatheredMetaDataKeys = new ArrayList<MetaDataKey>();
                DataSenseResolver dataSenseResolver = new DataSenseResolver();
                dataSenseResolver.setConnector(connection);
                gatheredMetaDataKeys.addAll(MetaDataGeneratorUtils.fillCategory(dataSenseResolver.getMetaDataKeys(), "DataSenseResolver"));
                return new DefaultResult<List<MetaDataKey>>(gatheredMetaDataKeys, (Result.Status.SUCCESS));
            } catch (Exception e) {
                return new DefaultResult<List<MetaDataKey>>(null, (Result.Status.FAILURE), "There was an error retrieving the metadata keys from service provider after acquiring connection, for more detailed information please read the provided stacktrace", MetaDataFailureType.ERROR_METADATA_KEYS_RETRIEVER, e);
            }
        } catch (Exception e) {
            try {
                destroyConnection(key, connection);
            } catch (Exception ie) {
            }
            return ((DefaultResult<List<MetaDataKey>> ) ConnectivityTestingErrorHandler.buildFailureTestResult(e));
        } finally {
            if (connection!= null) {
                try {
                    releaseConnection(key, connection);
                } catch (Exception ie) {
                }
            }
        }
    }

    @Override
    public Result<MetaData> getMetaData(MetaDataKey metaDataKey) {
        CTGConnectorConnectionManagementAdapter connection = null;
        ConnectionManagementConfigCTGConnectorConnectionKey key = getDefaultConnectionKey();
        try {
            connection = acquireConnection(key);
            try {
                MetaData metaData = null;
                TypeDescribingProperty property = metaDataKey.getProperty(TypeDescribingProperty.class);
                String category = ((DefaultMetaDataKey) metaDataKey).getCategory();
                if (category!= null) {
                    if (category.equals("DataSenseResolver")) {
                        DataSenseResolver dataSenseResolver = new DataSenseResolver();
                        dataSenseResolver.setConnector(connection);
                        metaData = dataSenseResolver.getMetaData(metaDataKey);
                    } else {
                        throw new Exception(((("Invalid key type. There is no matching category for ["+ metaDataKey.getId())+"]. All keys must contain a category with any of the following options:[DataSenseResolver]")+((", but found ["+ category)+"] instead")));
                    }
                } else {
                    throw new Exception((("Invalid key type. There is no matching category for ["+ metaDataKey.getId())+"]. All keys must contain a category with any of the following options:[DataSenseResolver]"));
                }
                metaData.getPayload().addProperty(new StructureIdentifierMetaDataModelProperty(metaDataKey, false));
                return new DefaultResult<MetaData>(metaData);
            } catch (Exception e) {
                return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), MetaDataGeneratorUtils.getMetaDataException(metaDataKey), MetaDataFailureType.ERROR_METADATA_RETRIEVER, e);
            }
        } catch (Exception e) {
            try {
                destroyConnection(key, connection);
            } catch (Exception ie) {
            }
            return ((DefaultResult<MetaData> ) ConnectivityTestingErrorHandler.buildFailureTestResult(e));
        } finally {
            if (connection!= null) {
                try {
                    releaseConnection(key, connection);
                } catch (Exception ie) {
                }
            }
        }
    }

}
