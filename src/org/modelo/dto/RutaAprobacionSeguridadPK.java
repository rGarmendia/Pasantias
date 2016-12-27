package org.modelo.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class RutaAprobacionSeguridadPK
  implements Serializable
{
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="tipoDocumento")
	private TipoDocumento tipoDocumento;
  @ManyToOne(fetch=FetchType.EAGER)
  @JoinColumn(name="area")
  private Area area;
  @ManyToOne(fetch=FetchType.EAGER)
  @JoinColumn(name="subarea")
  private Area subArea;
  
  
public Area getSubArea() {
	return subArea;
}
public void setSubArea(Area subArea) {
	this.subArea = subArea;
}
public TipoDocumento getTipoDocumento() {
	return tipoDocumento;
}
public void setTipoDocumento(TipoDocumento tipoDocumento) {
	this.tipoDocumento = tipoDocumento;
}
public Area getArea() {
	return area;
}
public void setArea(Area area) {
	this.area = area;
}
  
  
}
