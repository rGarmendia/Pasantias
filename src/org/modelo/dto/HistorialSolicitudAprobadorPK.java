package org.modelo.dto;

import java.io.Serializable;

import javax.persistence.Id;

public class HistorialSolicitudAprobadorPK implements Serializable {

	private String aprobadorid;
	  private Integer solicitudid;
	  
	public String getAprobadorid() {
		return aprobadorid;
	}
	public void setAprobadorid(String aprobadorid) {
		this.aprobadorid = aprobadorid;
	}
	public Integer getSolicitudid() {
		return solicitudid;
	}
	public void setSolicitudid(Integer solicitudid) {
		this.solicitudid = solicitudid;
	}
	
}
