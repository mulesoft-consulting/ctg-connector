package org.mule.modules.ctg.processor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.resource.ResourceException;

import javax.resource.cci.Connection;
import javax.resource.cci.Interaction;

import org.mule.api.transaction.Transaction;
import org.mule.api.transaction.TransactionException;
import org.mule.modules.ctg.connect.ConnectionManager;
import org.mule.transaction.TransactionCoordination;
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
	
	public InputStream execute(String channelName, 
			String requestContainer,
			String responseContainer,
			String errorContainer,
			String programName, 
			String tpnName,
			InputStream payload) {
		
		Interaction interaction = null;
		InputStream response = null;
				
		try {
			Connection conn = getConnection();
			
			interaction = conn.createInteraction();

			ECIChannelRecord channel = createChannel(channelName, requestContainer, payload);
			ECIInteractionSpec interactionSpec = getInteractionSpec(programName, tpnName);
			
			interaction.execute(interactionSpec, channel, channel); 
			
			response = getResponseFromChannel(channel, responseContainer, errorContainer);
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
			InputStream payload) throws ResourceException, IOException {
		ECIChannelRecord channel = new ECIChannelRecord(channelName);
		byte[] bytes = new byte[payload.available()];
		payload.read(bytes);
		logger.debug("input payload: " + new String(bytes));
		channel.put(requestContainer, bytes);  // BIT container
		
		return channel;
	}
	
	protected InputStream getResponseFromChannel(ECIChannelRecord channel, 
			String responseContainer,
			String errorContainer) {
		Object response = null;
		
		if (channel.containsKey(responseContainer)) {
			response = channel.get(responseContainer);
		} else if (channel.containsKey(errorContainer)) {
			response = channel.get(errorContainer);
		}
		
		if (response != null) {
			logger.debug("output payload: " + response);
			return new ByteArrayInputStream((byte []) response);
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
		Connection conn = getConnectionManager().getConnection();
		Transaction tx = TransactionCoordination.getInstance().getTransaction();
		
		if (tx != null) {
			tx.bindResource(getConnectionManager().getConnectionFactory(), conn);
			tx.begin();
		}
		
		return conn;
	}
}
