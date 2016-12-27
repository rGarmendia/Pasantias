package org.modelo.dto;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.Date;

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
@IdClass(HistorialAprobadorPK.class)
public class HistorialAprobador
  implements Serializable
{
  @Id
  private String aprobadorid;
  @Id
  private Integer documentoid;
  
  private Date fechaRecepcion;
  private Date fechaAprobacion;
  private Integer diasAprobacion;
  private Integer orden;
  private String resultado;
  private String comentario;
  @ManyToOne(fetch=FetchType.EAGER, cascade={javax.persistence.CascadeType.ALL})
  @NotFound(action=NotFoundAction.IGNORE)
  @PrimaryKeyJoinColumn(name="docuentoid", referencedColumnName="idCodigo")
  private Documento documento;
  @ManyToOne(fetch=FetchType.EAGER, cascade={javax.persistence.CascadeType.ALL})
  @NotFound(action=NotFoundAction.IGNORE)
  @PrimaryKeyJoinColumn(name="aprobadorid", referencedColumnName="login")
  private Usuario aprobador;
  
  
  
public String getComentario() {
	return comentario;
}
public void setComentario(String comentario) {
	this.comentario = comentario;
}
public String getResultado() {
	return resultado;
}
public void setResultado(String resultado) {
	this.resultado = resultado;
}
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
public Date getFechaRecepcion() {
	return fechaRecepcion;
}
public void setFechaRecepcion(Date fechaRecepcion) {
	this.fechaRecepcion = fechaRecepcion;
}
public Date getFechaAprobacion() {
	return fechaAprobacion;
}
public void setFechaAprobacion(Date fechaAprobacion) {
	this.fechaAprobacion = fechaAprobacion;
}
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
public Documento getDocumento() {
	return documento;
}
public void setDocumento(Documento documento) {
	this.documento = documento;
}
public Usuario getAprobador() {
	return aprobador;
}
public void setAprobador(Usuario aprobador) {
	this.aprobador = aprobador;
}
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((aprobadorid == null) ? 0 : aprobadorid.hashCode());
	result = prime * result + ((documentoid == null) ? 0 : documentoid.hashCode());
	return result;
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	HistorialAprobador other = (HistorialAprobador) obj;
	if (aprobadorid.equals(other.aprobadorid)  && documentoid.equals(other.documentoid)) {
			return true;
	} else 
	{
		return false;
	}
}
  
  /*public Long getParienteid()
  {
    return this.parienteid == null ? this.pariente.getCodigo() : this.parienteid;
  }
  
  public void setParienteid(Long parienteid) { this.parienteid = (this.pariente != null ? this.pariente.getCodigo() : parienteid); }
  
  public Paciente getPariente() {
    this.parienteid = (this.pariente != null ? this.pariente.getCodigo() : this.parienteid);
    return this.pariente;
  }
  
  public void setPariente(Paciente pariente) { this.parienteid = (pariente != null ? pariente.getCodigo() : this.parienteid);
    this.pariente = pariente;
  }
  
  public Integer getVentaid() { return this.ventaid == null ? this.venta.getCodigoVenta() : this.ventaid; }
  

  public void setVentaid(Integer ventaid) { this.ventaid = (this.venta != null ? this.venta.getCodigoVenta() : ventaid); }
  
  public Venta getVenta() {
    this.ventaid = (this.venta != null ? this.venta.getCodigoVenta() : this.ventaid);
    return this.venta;
  }
  
  public void setVenta(Venta venta) { this.ventaid = (venta != null ? venta.getCodigoVenta() : this.ventaid);
    this.venta = venta;
  }
  
  @PreUpdate
  public void preupdate() {
    System.out.println("=========");
    System.out.println(this.venta);
    System.out.println(this.pariente);
    this.ventaid = (this.venta != null ? this.venta.getCodigoVenta() : this.ventaid);
    this.parienteid = (this.pariente != null ? this.pariente.getCodigo() : this.parienteid);
  }
  
  @PrePersist
  public void prePersist() {
    System.out.println("=========");
    System.out.println(this.venta);
    System.out.println(this.pariente);
    this.ventaid = (this.venta != null ? this.venta.getCodigoVenta() : this.ventaid);
    this.parienteid = (this.pariente != null ? this.pariente.getCodigo() : this.parienteid);
  }*/
  
  
}
