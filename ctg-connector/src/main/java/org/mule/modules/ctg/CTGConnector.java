package org.mule.modules.ctg;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.mule.api.MuleContext;
import org.mule.api.annotations.Config;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.MetaDataScope;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.display.UserDefinedMetaData;
import org.mule.api.annotations.lifecycle.OnException;
import org.mule.api.annotations.param.Default;
import org.mule.api.context.MuleContextAware;
import org.mule.api.transformer.DataType;
import org.mule.devkit.api.transformer.DefaultTranformingValue;
import org.mule.devkit.api.transformer.TransformingValue;
import org.mule.modules.ctg.config.ConnectorConfig;
import org.mule.modules.ctg.error.ErrorHandler;
import org.mule.modules.ctg.processor.CTGProcessor;
import org.mule.modules.ctg.transaction.CTGTransactionFactory;
import org.mule.transformer.types.DataTypeFactory;
import org.mule.transformer.types.MimeTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Connector(name="ctg", friendlyName="CTG")
@MetaDataScope( DataSenseResolver.class )
@OnException(handler=ErrorHandler.class)

public class CTGConnector {

    private static final Logger logger = LoggerFactory.getLogger(CTGConnector.class);
    private static final String MIME_TYPE="text/plain";
    
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
    @UserDefinedMetaData
	public TransformingValue<ByteArrayInputStream, DataType<ByteArrayInputStream>> execute(String channel,
			String requestContainer,
			String responseContainer,
			String errorContainer,
			String programName, 
			String tpnName,
			@Default("ibm037") String encoding,
			@Default("#[payload]") ByteArrayOutputStream payload) {

    	logger.debug("execute(); " + programName);
    	DataType<InputStream> dataType = DataTypeFactory.create(InputStream.class, MimeTypes.TEXT);
        dataType.setEncoding(Charset.forName(encoding).name());
    
        dataType.setMimeType(MIME_TYPE);
        
        return new DefaultTranformingValue(getCtgProcessor().execute(channel, requestContainer, 
    			responseContainer, errorContainer, programName, tpnName, payload, false), dataType);
    }
    
    @Processor(name = "ExecuteUsingCommarea", friendlyName = "Execute using COMMAREA")
    @UserDefinedMetaData
	public TransformingValue<ByteArrayInputStream, DataType<ByteArrayInputStream>> executeUsingCommarea(String programName, 
			String tpnName,
			@Default("ibm037") String encoding,
			@Default("-1") int commareaLength,
			@Default("-1") int replyLength,
			@Default("#[payload]") ByteArrayOutputStream payload) {

    	logger.debug("execute(); " + programName);
    	DataType<InputStream> dataType = DataTypeFactory.create(InputStream.class, MimeTypes.TEXT);
        dataType.setEncoding(Charset.forName(encoding).name());
    
        dataType.setMimeType(MIME_TYPE);
        
        return new DefaultTranformingValue(getCtgProcessor().execute(programName, 
        		tpnName, commareaLength, replyLength, payload), dataType);
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