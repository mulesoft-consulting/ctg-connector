
package org.mule.modules.ctg.generated.adapters;

import javax.annotation.Generated;
import org.mule.api.MetadataAware;
import org.mule.modules.ctg.CTGConnector;


/**
 * A <code>CTGConnectorMetadataAdapter</code> is a wrapper around {@link CTGConnector } that adds support for querying metadata about the extension.
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.8.0", date = "2016-06-03T10:16:30-05:00", comments = "Build UNNAMED.2762.e3b1307")
public class CTGConnectorMetadataAdapter
    extends CTGConnectorCapabilitiesAdapter
    implements MetadataAware
{

    private final static String MODULE_NAME = "CTG";
    private final static String MODULE_VERSION = "1.0.1-SNAPSHOT";
    private final static String DEVKIT_VERSION = "3.8.0";
    private final static String DEVKIT_BUILD = "UNNAMED.2762.e3b1307";
    private final static String MIN_MULE_VERSION = "3.5.0";

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

}
