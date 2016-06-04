
package org.mule.modules.ctg.generated.adapters;

import javax.annotation.Generated;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.devkit.ProcessAdapter;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.routing.filter.Filter;
import org.mule.modules.ctg.CTGConnector;
import org.mule.security.oauth.callback.ProcessCallback;


/**
 * A <code>CTGConnectorProcessAdapter</code> is a wrapper around {@link CTGConnector } that enables custom processing strategies.
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.8.0", date = "2016-06-03T10:16:30-05:00", comments = "Build UNNAMED.2762.e3b1307")
public class CTGConnectorProcessAdapter
    extends CTGConnectorLifecycleInjectionAdapter
    implements ProcessAdapter<CTGConnectorCapabilitiesAdapter>
{


    public<P >ProcessTemplate<P, CTGConnectorCapabilitiesAdapter> getProcessTemplate() {
        final CTGConnectorCapabilitiesAdapter object = this;
        return new ProcessTemplate<P,CTGConnectorCapabilitiesAdapter>() {


            @Override
            public P execute(ProcessCallback<P, CTGConnectorCapabilitiesAdapter> processCallback, MessageProcessor messageProcessor, MuleEvent event)
                throws Exception
            {
                return processCallback.process(object);
            }

            @Override
            public P execute(ProcessCallback<P, CTGConnectorCapabilitiesAdapter> processCallback, Filter filter, MuleMessage message)
                throws Exception
            {
                return processCallback.process(object);
            }

        }
        ;
    }

}
