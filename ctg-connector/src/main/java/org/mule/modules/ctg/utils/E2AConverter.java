package org.mule.modules.ctg.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

public class E2AConverter {

	private static String IBM_EBCDIC_ENC_500="ibm500";
	private static String IBM_EBCDIC_ENC_037="ibm037";
	
	public static byte[] e2a(byte[] ebcdicIn) throws IOException {
		InputStreamReader reader = new InputStreamReader(
				new ByteArrayInputStream(trim(ebcdicIn)), Charset.forName(IBM_EBCDIC_ENC_037));
		StringBuilder builder = new StringBuilder();
		
		int iCh;
		while ((iCh = reader.read()) != -1) {
			builder.append((char)iCh);
		}
		reader.close();
		
		return builder.toString().getBytes();
	}
	
	public static byte[] a2e(byte[] asciiIn) throws IOException {
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		OutputStreamWriter writer = new OutputStreamWriter(bOut, Charset.forName(IBM_EBCDIC_ENC_037));
		
		for (int iCh: asciiIn) {
			writer.append((char)iCh); 
		}
		writer.close();
		
		return bOut.toByteArray();
	}
	
	public static void e2a(ByteArrayInputStream ebcdicIn, ByteArrayOutputStream asciiOut) throws IOException {
		InputStreamReader reader = new InputStreamReader(ebcdicIn, Charset.forName(IBM_EBCDIC_ENC_037));
		
		int iCh;
		while ((iCh = reader.read()) != -1) {
			asciiOut.write(iCh);
		}
		reader.close();
	}
	
	public static void a2e(ByteArrayInputStream asciiIn, ByteArrayOutputStream ebcdicOut) throws IOException {
		OutputStreamWriter writer = new OutputStreamWriter(ebcdicOut, Charset.forName(IBM_EBCDIC_ENC_037));
		
		int iCh;
		while ((iCh = asciiIn.read()) != -1) {
			writer.write(iCh);
		}
		writer.close();
	}
	
	public static byte[] trim(byte [] bytes) {
		int size=0;
		for (byte b : bytes) {
			if (b == 0) {
				break;
			} else {
				size++;
			}
		}
		byte [] trimmedBytes = new byte[size];
		
		System.arraycopy(bytes, 0, trimmedBytes, 0, size);
		return trimmedBytes;
	}
}
