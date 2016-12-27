package org.modelo.dto;

import java.io.Serializable;

import javax.persistence.Id;

public class DocumentoAprobadorPK implements Serializable {

	private String aprobadorid;
	  private Integer documentoid;
	  
	public String getAprobadorid() {
		return aprobadorid;
	}
	public void setAprobadorid(String aprobadorid) {
		this.aprobadorid = aprobadorid;
	}
	public Integer getDocumentoid() {
		return documentoid;
	}
	public void setDocumentoid(Integer documentoid) {
		this.documentoid = documentoid;
	}  
}
