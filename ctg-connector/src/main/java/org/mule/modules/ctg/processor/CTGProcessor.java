package org.mule.modules.ctg.processor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.resource.ResourceException;

import javax.resource.cci.Connection;
import javax.resource.cci.Interaction;

import org.mule.api.transaction.TransactionException;
import org.mule.modules.ctg.connect.ConnectionManager;
import org.mule.modules.ctg.utils.E2AConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.connector2.cics.ECIChannelRecord;
import com.ibm.connector2.cics.ECIInteractionSpec;

public class CTGProcessor {
    private static final Logger logger = LoggerFactory.getLogger(CTGProcessor.class);
    
    private ConnectionManager connectionManager;
	
	protected ConnectionManager getConnectionManager() {
		return connectionManager;
	}

	protected void setConnectionManager(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}

	public CTGProcessor(ConnectionManager connMgr) {
		setConnectionManager(connMgr);
	}
	
	public ByteArrayInputStream execute(String programName,
			String tpnName,
			int commareaLength,
			int replyLength,
			ByteArrayOutputStream payload) {
		Interaction interaction = null;
		ByteArrayInputStream response = null;
				
		try {
			Connection conn = getConnection();
			interaction = conn.createInteraction();
			
			ECIInteractionSpec interactionSpec = getInteractionSpec(programName, tpnName);
			
			if (commareaLength > 0) {
				interactionSpec.setCommareaLength(commareaLength);
			}
			if (replyLength > 0) {
				interactionSpec.setReplyLength(replyLength);
			}
			
			ByteArrayRecord in = new ByteArrayRecord(), out = new ByteArrayRecord();
			
			in.setBytes(payload.toByteArray());
			
			logPayload(programName, true, in.getBytes());
			
			if (interaction.execute(interactionSpec, in, out)) {
				logPayload(programName, false, out.getBytes());
				
				return new ByteArrayInputStream(out.getBytes());
			}
		} catch (Exception e) {
			logger.error("execute() failed: ", e);
		}
		finally {
			if (interaction != null) { 
				try {
					interaction.close();
				} catch (ResourceException e) {
					logger.error("interaction.close() failed: ", e);
				}
			}
		}
		return null;
	}
	
	public ByteArrayInputStream execute(String channelName, 
			String requestContainer,
			String responseContainer,
			String errorContainer,
			String programName, 
			String tpnName,
			ByteArrayOutputStream payload,
			boolean convert) {
		
		Interaction interaction = null;
		ByteArrayInputStream response = null;
				
		try {
			Connection conn = getConnection();
			
			interaction = conn.createInteraction();

			ECIChannelRecord channel = createChannel(channelName, requestContainer, programName, payload, convert);
			ECIInteractionSpec interactionSpec = getInteractionSpec(programName, tpnName);
			
			interaction.execute(interactionSpec, channel, channel); 
			
			response = getResponseFromChannel(channel, responseContainer, errorContainer, programName, convert);
		} catch (Exception e) {
			logger.error("execute() failed: ", e);
		}
		finally {
			if (interaction != null) { 
				try {
					interaction.close();
				} catch (ResourceException e) {
					logger.error("interaction.close() failed: ", e);
				}
			}
		}
		return response;
	}
	
	protected ECIChannelRecord createChannel(String channelName, 
			String requestContainer,
			String programName,
			ByteArrayOutputStream payload,
			boolean convert) throws ResourceException, IOException {
		ECIChannelRecord channel = new ECIChannelRecord(channelName);
		byte [] bytes = payload.toByteArray();
		logPayload(programName, true, bytes);
		
		if (convert) {
			byte[] convertedBytes = E2AConverter.a2e(bytes);
			channel.put(requestContainer, convertedBytes);  // BIT container
		} else {
			channel.put(requestContainer, bytes);
		}
		
		return channel;
	}
	
	protected ByteArrayInputStream getResponseFromChannel(ECIChannelRecord channel, 
			String responseContainer,
			String errorContainer,
			String programName,
			boolean convert) throws IOException {
		Object response = null;
		
		if (channel.containsKey(responseContainer)) {
			response = channel.get(responseContainer);
		} else if (channel.containsKey(errorContainer)) {
			response = channel.get(errorContainer);
		}
		
		if (response != null) {
			logPayload(programName, false, (byte[]) response);
			
			return convert ? 
				new ByteArrayInputStream(E2AConverter.e2a((byte[]) response)): 
				new ByteArrayInputStream((byte []) response);
		} else {
			logger.warn("unknown container name(s) " + responseContainer + " " + errorContainer);
		}
		
		return null;
	}
	
	protected ECIInteractionSpec getInteractionSpec(String programName, String tpnName) throws ResourceException {
		ECIInteractionSpec interactionSpec =  new ECIInteractionSpec();
		interactionSpec.setFunctionName(programName);
		interactionSpec.setTPNName(tpnName);
		interactionSpec.setInteractionVerb(ECIInteractionSpec.SYNC_SEND_RECEIVE);
		return interactionSpec;
	}
	
	protected Connection getConnection() throws ResourceException, TransactionException {
		return getConnectionManager().getConnection();
	}
	
	protected void logPayload(String programName, boolean isInput, byte [] payload) throws IOException {
		if (logger.isDebugEnabled()) {
			String debugFileName = programName + "-" + 
					Thread.currentThread().getId() + 
					((isInput) ? "-input.out" : "-output.out");
			
			FileOutputStream fOs = new FileOutputStream(new File(debugFileName));
			
			fOs.write(payload);
			fOs.close();
		}
	}
}
