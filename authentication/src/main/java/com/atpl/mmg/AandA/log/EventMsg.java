package com.atpl.mmg.AandA.log;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventMsg<D> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Date              timestamp        = null;
    
    private String            ver              = null;
    
    private String            service          = null;
    
    @NotNull
    private int               sev              = -1;
    
    private String            msg              = null;
    
    private D                 data             = null;
    
    private String            txnId            = null;
    
    public D getData() {
        return data;
    }
    
    public void setData(final D data) {
        this.data = data;
    }
        
    public Date getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(final Date timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getVer() {
        return ver;
    }
    
    public void setVer(final String ver) {
        this.ver = ver;
    }
    
    public String getService() {
        return service;
    }
    
    public void setService(final String service) {
        this.service = service;
    }
        
    public int getSev() {
        return sev;
    }
    
    public void setSev(final int sev) {
        this.sev = sev;
    }
    
    public String getMsg() {
        return msg;
    }
    
    public void setMsg(final String msg) {
        this.msg = msg;
    }

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	@Override
	public String toString() {
		return "EventMsg [timestamp=" + timestamp + ", ver=" + ver + ", service=" + service + ", sev=" + sev + ", msg="
				+ msg + ", data=" + data + ", txnId=" + txnId + "]";
	}
	
}
