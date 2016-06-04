
package org.mule.modules.ctg.generated.processors;

import java.io.InputStream;
import java.util.List;
import javax.annotation.Generated;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.config.ConfigurationException;
import org.mule.api.devkit.ProcessAdapter;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.registry.RegistrationException;
import org.mule.common.DefaultResult;
import org.mule.common.FailureType;
import org.mule.common.Result;
import org.mule.common.metadata.ConnectorMetaDataEnabled;
import org.mule.common.metadata.DefaultMetaData;
import org.mule.common.metadata.DefaultMetaDataKey;
import org.mule.common.metadata.DefaultPojoMetaDataModel;
import org.mule.common.metadata.DefaultSimpleMetaDataModel;
import org.mule.common.metadata.MetaData;
import org.mule.common.metadata.MetaDataKey;
import org.mule.common.metadata.MetaDataModel;
import org.mule.common.metadata.OperationMetaDataEnabled;
import org.mule.common.metadata.datatype.DataType;
import org.mule.common.metadata.datatype.DataTypeFactory;
import org.mule.devkit.internal.metadata.fixes.STUDIO7157;
import org.mule.devkit.processor.DevkitBasedMessageProcessor;
import org.mule.modules.ctg.CTGConnector;
import org.mule.modules.ctg.error.ErrorHandler;
import org.mule.security.oauth.callback.ProcessCallback;


/**
 * ExecuteMessageProcessor invokes the {@link org.mule.modules.ctg.CTGConnector#execute(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.io.InputStream)} method in {@link CTGConnector }. For each argument there is a field in this processor to match it.  Before invoking the actual method the processor will evaluate and transform where possible to the expected argument type.
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.8.0", date = "2016-06-03T10:16:30-05:00", comments = "Build UNNAMED.2762.e3b1307")
public class ExecuteMessageProcessor
    extends DevkitBasedMessageProcessor
    implements MessageProcessor, OperationMetaDataEnabled
{

    protected Object channel;
    protected String _channelType;
    protected Object requestContainer;
    protected String _requestContainerType;
    protected Object responseContainer;
    protected String _responseContainerType;
    protected Object errorContainer;
    protected String _errorContainerType;
    protected Object programName;
    protected String _programNameType;
    protected Object tpnName;
    protected String _tpnNameType;
    protected Object payload;
    protected InputStream _payloadType;

    public ExecuteMessageProcessor(String operationName) {
        super(operationName);
    }

    /**
     * Obtains the expression manager from the Mule context and initialises the connector. If a target object  has not been set already it will search the Mule registry for a default one.
     * 
     * @throws InitialisationException
     */
    public void initialise()
        throws InitialisationException
    {
    }

    @Override
    public void start()
        throws MuleException
    {
        super.start();
    }

    @Override
    public void stop()
        throws MuleException
    {
        super.stop();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    /**
     * Sets requestContainer
     * 
     * @param value Value to set
     */
    public void setRequestContainer(Object value) {
        this.requestContainer = value;
    }

    /**
     * Sets errorContainer
     * 
     * @param value Value to set
     */
    public void setErrorContainer(Object value) {
        this.errorContainer = value;
    }

    /**
     * Sets responseContainer
     * 
     * @param value Value to set
     */
    public void setResponseContainer(Object value) {
        this.responseContainer = value;
    }

    /**
     * Sets tpnName
     * 
     * @param value Value to set
     */
    public void setTpnName(Object value) {
        this.tpnName = value;
    }

    /**
     * Sets payload
     * 
     * @param value Value to set
     */
    public void setPayload(Object value) {
        this.payload = value;
    }

    /**
     * Sets channel
     * 
     * @param value Value to set
     */
    public void setChannel(Object value) {
        this.channel = value;
    }

    /**
     * Sets programName
     * 
     * @param value Value to set
     */
    public void setProgramName(Object value) {
        this.programName = value;
    }

    /**
     * Invokes the MessageProcessor.
     * 
     * @param event MuleEvent to be processed
     * @throws Exception
     */
    public MuleEvent doProcess(final MuleEvent event)
        throws Exception
    {
        Object moduleObject = null;
        try {
            moduleObject = findOrCreate(null, false, event);
            final String _transformedChannel = ((String) evaluateAndTransform(getMuleContext(), event, ExecuteMessageProcessor.class.getDeclaredField("_channelType").getGenericType(), null, channel));
            final String _transformedRequestContainer = ((String) evaluateAndTransform(getMuleContext(), event, ExecuteMessageProcessor.class.getDeclaredField("_requestContainerType").getGenericType(), null, requestContainer));
            final String _transformedResponseContainer = ((String) evaluateAndTransform(getMuleContext(), event, ExecuteMessageProcessor.class.getDeclaredField("_responseContainerType").getGenericType(), null, responseContainer));
            final String _transformedErrorContainer = ((String) evaluateAndTransform(getMuleContext(), event, ExecuteMessageProcessor.class.getDeclaredField("_errorContainerType").getGenericType(), null, errorContainer));
            final String _transformedProgramName = ((String) evaluateAndTransform(getMuleContext(), event, ExecuteMessageProcessor.class.getDeclaredField("_programNameType").getGenericType(), null, programName));
            final String _transformedTpnName = ((String) evaluateAndTransform(getMuleContext(), event, ExecuteMessageProcessor.class.getDeclaredField("_tpnNameType").getGenericType(), null, tpnName));
            final InputStream _transformedPayload = ((InputStream) evaluateAndTransform(getMuleContext(), event, ExecuteMessageProcessor.class.getDeclaredField("_payloadType").getGenericType(), null, payload));
            Object resultPayload;
            final ProcessTemplate<Object, Object> processTemplate = ((ProcessAdapter<Object> ) moduleObject).getProcessTemplate();
            resultPayload = processTemplate.execute(new ProcessCallback<Object,Object>() {


                public List<Class<? extends Exception>> getManagedExceptions() {
                    return null;
                }

                public boolean isProtected() {
                    return false;
                }

                public Object process(Object object)
                    throws Exception
                {
                    try {
                        return ((CTGConnector) object).execute(_transformedChannel, _transformedRequestContainer, _transformedResponseContainer, _transformedErrorContainer, _transformedProgramName, _transformedTpnName, _transformedPayload);
                    } catch (Exception e) {
                        ErrorHandler handler = new ErrorHandler();
                        handler.handle(e);
                        throw e;
                    }
                }

            }
            , this, event);
            event.getMessage().setPayload(resultPayload);
            return event;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Result<MetaData> getInputMetaData() {
        MetaDataModel metaDataPayload = getPojoOrSimpleModel(InputStream.class);
        DefaultMetaDataKey keyForStudio = new DefaultMetaDataKey("INPUT_METADATA", null);
        keyForStudio.setCategory("DataSenseResolver");
        metaDataPayload.addProperty(STUDIO7157 .getStructureIdentifierMetaDataModelProperty(keyForStudio, false, false));
        return new DefaultResult<MetaData>(new DefaultMetaData(metaDataPayload));
    }

    @Override
    public Result<MetaData> getOutputMetaData(MetaData inputMetadata) {
        MetaDataModel metaDataPayload = getPojoOrSimpleModel(InputStream.class);
        DefaultMetaDataKey keyForStudio = new DefaultMetaDataKey("OUTPUT_METADATA", null);
        keyForStudio.setCategory("DataSenseResolver");
        metaDataPayload.addProperty(STUDIO7157 .getStructureIdentifierMetaDataModelProperty(keyForStudio, false, false));
        return new DefaultResult<MetaData>(new DefaultMetaData(metaDataPayload));
    }

    private MetaDataModel getPojoOrSimpleModel(Class clazz) {
        DataType dataType = DataTypeFactory.getInstance().getDataType(clazz);
        if (DataType.POJO.equals(dataType)) {
            return new DefaultPojoMetaDataModel(clazz);
        } else {
            return new DefaultSimpleMetaDataModel(dataType);
        }
    }

    public Result<MetaData> getGenericMetaData(MetaDataKey metaDataKey) {
        ConnectorMetaDataEnabled connector;
        try {
            connector = ((ConnectorMetaDataEnabled) findOrCreate(null, false, null));
            try {
                Result<MetaData> metadata = connector.getMetaData(metaDataKey);
                if ((Result.Status.FAILURE).equals(metadata.getStatus())) {
                    return metadata;
                }
                if (metadata.get() == null) {
                    return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), "There was an error processing metadata at CTGConnector at execute retrieving was successful but result is null");
                }
                return metadata;
            } catch (Exception e) {
                return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
            }
        } catch (ClassCastException cast) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), "There was an error getting metadata, there was no connection manager available. Maybe you're trying to use metadata from an Oauth connector");
        } catch (ConfigurationException e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        } catch (RegistrationException e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        } catch (IllegalAccessException e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        } catch (InstantiationException e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        } catch (Exception e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        }
    }

}
