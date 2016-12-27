package org.modelo.dto;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;



@Entity
@IdClass(RutaAprobacionAprobadorSolicitudPK.class)
public class RutaAprobacionAprobadorSolicitud
  implements Serializable
{
  @Id
  private String aprobadorid;
  @Id
  private Integer tipodocumentoid;
  @Id
  private String areaid;
  @Id
  private String[] tiposCambio;
  private Integer orden;
  private Integer diasAprobacion;
  
  @ManyToOne(fetch=FetchType.EAGER, cascade={javax.persistence.CascadeType.ALL})
  @NotFound(action=NotFoundAction.IGNORE)
  @PrimaryKeyJoinColumns({  
	  @PrimaryKeyJoinColumn(name = "tipodocumentoid", referencedColumnName = "tipoDocumento"),  
	  @PrimaryKeyJoinColumn(name = "areaid", referencedColumnName = "area") , 
	  @PrimaryKeyJoinColumn(name = "tiposCambio", referencedColumnName = "tiposCambio")
	  })
  private RutaAprobacionSeguridad rutaAprobacionSolicitud;
  @ManyToOne(fetch=FetchType.EAGER)
  @NotFound(action=NotFoundAction.IGNORE)
  @PrimaryKeyJoinColumn(name="aprobadorid", referencedColumnName="login")
  private Usuario aprobador;
  
  
public Integer getDiasAprobacion() {
	return diasAprobacion;
}
public void setDiasAprobacion(Integer diasAprobacion) {
	this.diasAprobacion = diasAprobacion;
}
public Integer getOrden() {
	return orden;
}
public void setOrden(Integer orden) {
	this.orden = orden;
}

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

public Usuario getAprobador() {
	return aprobador;
}
public void setAprobador(Usuario aprobador) {
	this.aprobador = aprobador;
}

public RutaAprobacionSeguridad getRutaAprobacionSolicitud() {
	return rutaAprobacionSolicitud;
}
public void setRutaAprobacionSolicitud(RutaAprobacionSeguridad rutaAprobacionSolicitud) {
	this.rutaAprobacionSolicitud = rutaAprobacionSolicitud;
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	RutaAprobacionAprobadorSolicitud other = (RutaAprobacionAprobadorSolicitud) obj;
	if (aprobadorid.equals(other.aprobadorid) && areaid.equals(other.areaid) && tipodocumentoid.equals(other.tipodocumentoid)) {
			return true;
	} else 
	{
		return false;
	}
}
public String[] getTiposCambio() {
	return tiposCambio;
}
public void setTiposCambio(String[] tiposCambio) {
	this.tiposCambio = tiposCambio;
}

  
}
