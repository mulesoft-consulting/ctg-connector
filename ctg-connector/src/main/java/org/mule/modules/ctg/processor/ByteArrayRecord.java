package org.mule.modules.ctg.processor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.resource.cci.Record;
import javax.resource.cci.Streamable;

@SuppressWarnings("serial")
public class ByteArrayRecord implements Record, Streamable {
	
	private String recordName;
    private String desc;
    private byte bytes[];
    
	@Override
	public void read(InputStream is) throws IOException {
		int total = is.available();
            
        if (total > 0) {
           this.bytes = new byte[total];
           is.read(this.bytes);
        }
	}

	@Override
	public void write(OutputStream os) throws IOException {
		// TODO Auto-generated method stub
		os.write(this.bytes);
	}

	@Override
	public String getRecordName() {
		// TODO Auto-generated method stub
		return this.recordName;
	}

	@Override
	public String getRecordShortDescription() {
		// TODO Auto-generated method stub
		return this.desc;
	}

	@Override
	public void setRecordName(String recordName) {
		// TODO Auto-generated method stub
		this.recordName = recordName;
	}

	@Override
	public void setRecordShortDescription(String desc) {
		// TODO Auto-generated method stub
		this.desc = desc;
	}

	public java.lang.Object clone() throws CloneNotSupportedException {
        return super.clone();
     }

	public int hashCode() {
		if (this.bytes != null) {
			return bytes.hashCode();
		}
		return super.hashCode();
	}
	
	public boolean equals(Object obj) {
		if (this.bytes != null && obj instanceof ByteArrayRecord) {
			return this.bytes.equals(((ByteArrayRecord)obj).getBytes());
		}
		return false;
	}
	
	public byte[] getBytes() {
		return this.bytes;
	}
	
	public void setBytes(byte bytes[]) {
		this.bytes = bytes;
	}
}
