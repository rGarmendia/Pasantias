package org.modelo.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
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

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
public class TipoDocumento
  implements Serializable
{
	public static final int[] TIPOS_PRIVADOS = {1};
	@javax.persistence.Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="my_entity_seq_gen")
	@SequenceGenerator(name="my_entity_seq_gen", sequenceName="idTipoDocumento_seq")
	private Integer codigo;
  private String nombre;
  private String patronCodigo;
  private boolean principal = false;
  @OneToMany(cascade={javax.persistence.CascadeType.ALL}, fetch=FetchType.EAGER, orphanRemoval=true)
  @JoinColumn(name="tipoDocumento")
  private Set<Campo> campos = new HashSet();
  private String estatus = "Activo";
  private TipoDocumento tipoPadre;
  
public TipoDocumento getTipoPadre() {
	return tipoPadre;
}

public void setTipoPadre(TipoDocumento tipoPadre) {
	this.tipoPadre = tipoPadre;
}

public boolean isPrincipal() {
	return principal;
}

public void setPrincipal(boolean principal) {
	this.principal = principal;
}

public void ordenarCamposDesc()
{
	  List listaCampos= new ArrayList(this.getCampos());
		Collections.sort(listaCampos, new Comparator(){
			
			@Override
			public int compare(Object o1, Object o2) {
				org.modelo.dto.Campo camp1 = (org.modelo.dto.Campo) o1;
				org.modelo.dto.Campo camp2 = (org.modelo.dto.Campo) o2;
				return camp2.getOrden().compareTo(camp1.getOrden());
			}});
		
		this.setCampos(new LinkedHashSet(listaCampos));
}

public void ordenarCamposAsc()
  {
	  List listaCampos= new ArrayList(this.getCampos());
		Collections.sort(listaCampos, new Comparator(){
			
			@Override
			public int compare(Object o1, Object o2) {
				org.modelo.dto.Campo camp1 = (org.modelo.dto.Campo) o1;
				org.modelo.dto.Campo camp2 = (org.modelo.dto.Campo) o2;
				return camp1.getOrden().compareTo(camp2.getOrden());
			}});
		
		this.setCampos(new LinkedHashSet(listaCampos));
  }
  
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	TipoDocumento other = (TipoDocumento) obj;
	if (codigo == null) {
		if (other.codigo != null)
			return false;
	} else if (!codigo.equals(other.codigo))
		return false;
	return true;
}

public String getPatronCodigo() {
	return patronCodigo;
}
public void setPatronCodigo(String patronCodigo) {
	this.patronCodigo = patronCodigo;
}
public String getEstatus() {
	return estatus;
}
public void setEstatus(String estatus) {
	this.estatus = estatus;
}
public Integer getCodigo() {
	return codigo;
}
public void setCodigo(Integer codigo) {
	this.codigo = codigo;
}
public String getNombre() {
	return nombre;
}
public void setNombre(String nombre) {
	this.nombre = nombre;
}
public Set<Campo> getCampos() {
	return campos;
}
public void setCampos(Set<Campo> campos) {
	this.campos = campos;
}
  
}
