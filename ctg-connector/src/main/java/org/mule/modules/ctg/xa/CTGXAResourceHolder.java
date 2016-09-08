package org.mule.modules.ctg.xa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.transaction.xa.XAResource;

import bitronix.tm.resource.common.AbstractXAResourceHolder;
import bitronix.tm.resource.common.ResourceBean;
import bitronix.tm.resource.common.XAResourceHolder;

public class CTGXAResourceHolder extends AbstractXAResourceHolder {

	private XAResource resource;
    private ResourceBean bean;
    
    public CTGXAResourceHolder(XAResource xaResource, ResourceBean bean) {
		// TODO Auto-generated constructor stub
    	this.resource = xaResource;
        this.bean = bean;
	}
    
	@Override
	public ResourceBean getResourceBean() {
		// TODO Auto-generated method stub
		return bean;
	}

	@Override
	public XAResource getXAResource() {
		// TODO Auto-generated method stub
		return resource;
	}

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getConnectionHandle() throws Exception {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public Date getLastReleaseDate() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public List<XAResourceHolder> getXAResourceHolders() {
		// TODO Auto-generated method stub
		ArrayList<XAResourceHolder> list = new ArrayList<XAResourceHolder>();
		list.add(this);
		
		return list;
	}

	public CTGXAResourceHolder getXAResourceHolderForXaResource(XAResource xaResource) {
        if (xaResource == resource) {
            return this;
        }
        return null;
    }
}
