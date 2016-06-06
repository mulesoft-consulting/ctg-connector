
package org.mule.modules.ctg.generated.connectivity;

import javax.annotation.Generated;
import org.mule.api.ConnectionException;
import org.mule.api.MuleContext;
import org.mule.api.context.MuleContextAware;
import org.mule.devkit.internal.connection.management.ConnectionManagementConnectionAdapter;
import org.mule.devkit.internal.connection.management.TestableConnection;
import org.mule.modules.ctg.config.ConnectorConfig;

@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.8.0", date = "2016-06-06T12:17:29-04:00", comments = "Build UNNAMED.2762.e3b1307")
public class ConnectorConfigCTGConnectorAdapter
    extends ConnectorConfig
    implements MuleContextAware, ConnectionManagementConnectionAdapter<ConnectorConfig, ConnectionManagementConfigCTGConnectorConnectionKey> , TestableConnection<ConnectionManagementConfigCTGConnectorConnectionKey>
{


    @Override
    public ConnectorConfig getStrategy() {
        return this;
    }

    @Override
    public void setMuleContext(MuleContext muleContext) {
        super.setMuleContext(muleContext);
    }

    @Override
    public void test(ConnectionManagementConfigCTGConnectorConnectionKey connectionKey)
        throws ConnectionException
    {
        super.connect(connectionKey.getHost(), connectionKey.getPort(), connectionKey.getServerName(), connectionKey.getUsername(), connectionKey.getPassword());
    }

    @Override
    public void connect(ConnectionManagementConfigCTGConnectorConnectionKey connectionKey)
        throws ConnectionException
    {
        super.connect(connectionKey.getHost(), connectionKey.getPort(), connectionKey.getServerName(), connectionKey.getUsername(), connectionKey.getPassword());
    }

    @Override
    public void disconnect() {
        super.disconnect();
    }

    @Override
    public String connectionId() {
        return super.connectionId();
    }

    @Override
    public boolean isConnected() {
        return super.isConnected();
    }

}
