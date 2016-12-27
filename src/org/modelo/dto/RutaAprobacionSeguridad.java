package org.modelo.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
public class RutaAprobacionSeguridad
  implements Serializable
{
	@EmbeddedId
	private RutaAprobacionSeguridadPK pk = new RutaAprobacionSeguridadPK();
	
	@OneToMany(mappedBy="rutaAprobacionSolicitud", fetch=FetchType.EAGER, cascade={javax.persistence.CascadeType.ALL})
	  @Fetch(FetchMode.SELECT)
	  @NotFound(action=NotFoundAction.IGNORE)
	  private Set<RutaAprobacionAprobadorSolicitud> aprobadoresSolicitud = new HashSet();
	
	@OneToMany(mappedBy="rutaAprobacion", fetch=FetchType.EAGER, cascade={javax.persistence.CascadeType.ALL})
	  @Fetch(FetchMode.SELECT)
	  @NotFound(action=NotFoundAction.IGNORE)
	  private Set<RutaAprobacionAprobador> aprobadores = new HashSet();
	
	@ManyToMany(cascade={javax.persistence.CascadeType.ALL}, fetch=FetchType.EAGER)
	  @JoinTable(name="rutaAprobacionSeguridad_usuarioSeguridad", 
	  joinColumns={@JoinColumn(name="area", referencedColumnName="area"), 
			  @JoinColumn(name="tipodocumento", referencedColumnName="tipodocumento"), 
			  @JoinColumn(name="subarea", referencedColumnName="subarea")}, 
	  inverseJoinColumns={@JoinColumn(name="usuario", referencedColumnName="login")})
	  private Set<Usuario> usuariosSeguridad = new HashSet();
	
	@ManyToMany(cascade={javax.persistence.CascadeType.ALL}, fetch=FetchType.EAGER)
	  @JoinTable(name="rutaAprobacionSeguridad_usuarioPermiso", 
	  joinColumns={@JoinColumn(name="area", referencedColumnName="area"), 
			  @JoinColumn(name="tipodocumento", referencedColumnName="tipodocumento"), 
			  @JoinColumn(name="subarea", referencedColumnName="subarea")}, 
	  inverseJoinColumns={@JoinColumn(name="usuario", referencedColumnName="login")})
	  private Set<Usuario> usuariosPermiso = new HashSet();
	
	@ManyToMany(cascade={javax.persistence.CascadeType.ALL}, fetch=FetchType.EAGER)
	  @JoinTable(name="rutaAprobacionSeguridad_grupoSeguridad", 
	  joinColumns={@JoinColumn(name="area", referencedColumnName="area"), 
			  @JoinColumn(name="tipodocumento", referencedColumnName="tipodocumento"), 
			  @JoinColumn(name="subarea", referencedColumnName="subarea")}, 
	  inverseJoinColumns={@JoinColumn(name="grupo", referencedColumnName="codigo")})
	  private Set<Grupo> gruposSeguridad = new HashSet();
	
	@ManyToMany(cascade={javax.persistence.CascadeType.ALL}, fetch=FetchType.EAGER)
	  @JoinTable(name="rutaAprobacionSeguridad_grupoPermiso", 
	  joinColumns={@JoinColumn(name="area", referencedColumnName="area"), 
			  @JoinColumn(name="tipodocumento", referencedColumnName="tipodocumento"), 
			  @JoinColumn(name="subarea", referencedColumnName="subarea")}, 
	  inverseJoinColumns={@JoinColumn(name="grupo", referencedColumnName="codigo")})
	  private Set<Grupo> gruposPermiso = new HashSet();
	
	
	public Set<RutaAprobacionAprobadorSolicitud> getAprobadoresSolicitud() {
		return aprobadoresSolicitud;
	}
	public void setAprobadoresSolicitud(Set<RutaAprobacionAprobadorSolicitud> aprobadoresSolicitud) {
		this.aprobadoresSolicitud = aprobadoresSolicitud;
	}
	public Set<Usuario> getUsuariosSeguridad() {
		return usuariosSeguridad;
	}
	public void setUsuariosSeguridad(Set<Usuario> usuariosSeguridad) {
		this.usuariosSeguridad = usuariosSeguridad;
	}
	public Set<Usuario> getUsuariosPermiso() {
		return usuariosPermiso;
	}
	public void setUsuariosPermiso(Set<Usuario> usuariosPermiso) {
		this.usuariosPermiso = usuariosPermiso;
	}
	public Set<Grupo> getGruposSeguridad() {
		return gruposSeguridad;
	}
	public void setGruposSeguridad(Set<Grupo> gruposSeguridad) {
		this.gruposSeguridad = gruposSeguridad;
	}
	public Set<Grupo> getGruposPermiso() {
		return gruposPermiso;
	}
	public void setGruposPermiso(Set<Grupo> gruposPermiso) {
		this.gruposPermiso = gruposPermiso;
	}
	public RutaAprobacionSeguridadPK getPk() {
		return pk;
	}
	public void setPk(RutaAprobacionSeguridadPK pk) {
		this.pk = pk;
	}
	public Set<RutaAprobacionAprobador> getAprobadores() {
		return aprobadores;
	}
	public void setAprobadores(Set<RutaAprobacionAprobador> aprobadores) {
		this.aprobadores = aprobadores;
	}
	
	public void ordenarAprobadores()
	  {
		  List listaAprobadores= new ArrayList(this.getAprobadores());
			Collections.sort(listaAprobadores, new Comparator(){
				
				@Override
				public int compare(Object o1, Object o2) {
					org.modelo.dto.RutaAprobacionAprobador raa1 = (org.modelo.dto.RutaAprobacionAprobador) o1;
					org.modelo.dto.RutaAprobacionAprobador raa2 = (org.modelo.dto.RutaAprobacionAprobador) o2;
					return raa1.getOrden().compareTo(raa2.getOrden());
				}});
			
			this.setAprobadores(new LinkedHashSet(listaAprobadores));
	  }
    
}
