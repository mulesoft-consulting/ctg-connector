package org.mule.modules.ctg.processor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class ExecuteContext {
	private String channelName; 
	private String requestContainer;
	private String responseContainer;
	private String errorContainer;
	private String programName; 
	private String tpnName;
	private ByteArrayOutputStream requestPayload;
	private ByteArrayInputStream responsePayload;
	
	private boolean convert;
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getRequestContainer() {
		return requestContainer;
	}
	public void setRequestContainer(String requestContainer) {
		this.requestContainer = requestContainer;
	}
	public String getResponseContainer() {
		return responseContainer;
	}
	public void setResponseContainer(String responseContainer) {
		this.responseContainer = responseContainer;
	}
	public String getErrorContainer() {
		return errorContainer;
	}
	public void setErrorContainer(String errorContainer) {
		this.errorContainer = errorContainer;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public String getTpnName() {
		return tpnName;
	}
	public void setTpnName(String tpnName) {
		this.tpnName = tpnName;
	}
	public ByteArrayOutputStream getRequestPayload() {
		return requestPayload;
	}
	public void setRequestPayload(ByteArrayOutputStream requestPayload) {
		this.requestPayload = requestPayload;
	}
	public ByteArrayInputStream getResponsePayload() {
		return responsePayload;
	}
	public void setResponsePayload(ByteArrayInputStream responsePayload) {
		this.responsePayload = responsePayload;
	}
	public boolean isConvert() {
		return convert;
	}
	public void setConvert(boolean convert) {
		this.convert = convert;
	}
	
}
