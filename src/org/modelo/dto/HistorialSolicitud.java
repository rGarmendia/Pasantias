package org.modelo.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class HistorialSolicitud
  implements Serializable
{
	@javax.persistence.Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="my_entity_seq_gen")
	@SequenceGenerator(name="my_entity_seq_gen", sequenceName="idHistorialSolicitud_seq")
	private Integer codigo;
  @ManyToOne(fetch=FetchType.EAGER)
  @JoinColumn(name="solicitud")
  private Solicitud solicitud;
  private String ocurrencia;
  private String comentario;
  private Date fecha;
  @ManyToOne(fetch=FetchType.EAGER)
  @JoinColumn(name="usuario")
  private Usuario usuario;
public Integer getCodigo() {
	return codigo;
}
public void setCodigo(Integer codigo) {
	this.codigo = codigo;
}

public Solicitud getSolicitud() {
	return solicitud;
}
public void setSolicitud(Solicitud solicitud) {
	this.solicitud = solicitud;
}
public String getOcurrencia() {
	return ocurrencia;
}
public void setOcurrencia(String ocurrencia) {
	this.ocurrencia = ocurrencia;
}
public String getComentario() {
	return comentario;
}
public void setComentario(String comentario) {
	this.comentario = comentario;
}
public Date getFecha() {
	return fecha;
}
public void setFecha(Date fecha) {
	this.fecha = fecha;
}
public Usuario getUsuario() {
	return usuario;
}
public void setUsuario(Usuario usuario) {
	this.usuario = usuario;
}
  
  
}
