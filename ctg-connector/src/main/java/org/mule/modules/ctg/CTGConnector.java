package org.mule.modules.ctg;

import java.io.InputStream;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.mule.api.MuleContext;
import org.mule.api.annotations.Config;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.MetaDataScope;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.lifecycle.OnException;
import org.mule.api.annotations.param.Default;
import org.mule.api.context.MuleContextAware;
import org.mule.modules.ctg.config.ConnectorConfig;
import org.mule.modules.ctg.error.ErrorHandler;
import org.mule.modules.ctg.processor.CTGProcessor;
import org.mule.modules.ctg.transaction.CTGTransactionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Connector(name="ctg", friendlyName="CTG")
@MetaDataScope( DataSenseResolver.class )
@OnException(handler=ErrorHandler.class)

public class CTGConnector {

    private static final Logger logger = LoggerFactory.getLogger(CTGConnector.class);
    @Config
    ConnectorConfig config;
    @Inject
    private MuleContext muleContext;
    
    private CTGProcessor ctgProcessor;
    
	public CTGConnector() {
    }
	
	@PostConstruct
	public void init() {
		muleContext.getTransactionFactoryManager().registerTransactionFactory(
				com.ibm.connector2.cics.ECIConnectionFactory.class, new CTGTransactionFactory());
	}
	
    @Processor(name = "Execute", friendlyName = "Execute")
	public InputStream execute(String channel, 
			String requestContainer,
			String responseContainer,
			String errorContainer,
			String programName, 
			String tpnName,
			@Default("#[payload]") InputStream payload) {

    	logger.debug("execute(); " + programName);
    	return getCtgProcessor().execute(channel, requestContainer, 
    			responseContainer, errorContainer, programName, tpnName, payload);
	}
    
    public MuleContext getMuleContext() {
		return muleContext;
	}

	public void setMuleContext(MuleContext muleContext) {
		this.muleContext = muleContext;
	}

	public ConnectorConfig getConfig() {
        return config;
    }

    public void setConfig(ConnectorConfig config) {
        this.config = config;
        setCtgProcessor(new CTGProcessor(config.getConnectionManager()));
        
    }

    public CTGProcessor getCtgProcessor() {
		return ctgProcessor;
	}

	public void setCtgProcessor(CTGProcessor ctgProcessor) {
		this.ctgProcessor = ctgProcessor;
	}
}