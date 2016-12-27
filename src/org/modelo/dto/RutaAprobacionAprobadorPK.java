package org.modelo.dto;

import java.io.Serializable;

import javax.persistence.Id;

public class RutaAprobacionAprobadorPK implements Serializable {

	private String aprobadorid;
	  private Integer tipodocumentoid;
	  private String areaid;
	public String getAprobadorid() {
		return aprobadorid;
	}
	public void setAprobadorid(String aprobadorid) {
		this.aprobadorid = aprobadorid;
	}
	public Integer getTipodocumentoid() {
		return tipodocumentoid;
	}
	public void setTipodocumentoid(Integer tipodocumentoid) {
		this.tipodocumentoid = tipodocumentoid;
	}
	public String getAreaid() {
		return areaid;
	}
	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}
	  
	  
}
