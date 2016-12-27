package org.modelo.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class Area
  implements Serializable
{
  @Id
  private String codigo;
  private String nombre;
  private String descripcion;
  @ManyToOne(cascade={javax.persistence.CascadeType.MERGE}, fetch=FetchType.EAGER)
  @JoinColumn(name="areaPadre")
  private Area areaPadre;
  @OneToMany(cascade={javax.persistence.CascadeType.ALL}, fetch=FetchType.EAGER)
  @JoinColumn(name="areaPadre")
  private Set<Area> subAreas = new HashSet();
  private String estatus = "Activa";
  
  
public String getEstatus() {
	return estatus;
}
public void setEstatus(String estatus) {
	this.estatus = estatus;
}
public String getCodigo() {
	return codigo;
}
public void setCodigo(String codigo) {
	this.codigo = codigo;
}
public String getNombre() {
	return nombre;
}
public void setNombre(String nombre) {
	this.nombre = nombre;
}
public String getDescripcion() {
	return descripcion;
}
public void setDescripcion(String descripcion) {
	this.descripcion = descripcion;
}
public Area getAreaPadre() {
	return areaPadre;
}
public void setAreaPadre(Area areaPadre) {
	this.areaPadre = areaPadre;
}
public Set<Area> getSubAreas() {
	return subAreas;
}
public void setSubAreas(Set<Area> subAreas) {
	this.subAreas = subAreas;
}
  
}
