package org.modelo.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.zkoss.zul.AbstractTreeModel;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Row;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;

@Entity
public class Solicitud
  implements Serializable
{
	@Id
	  @GeneratedValue(strategy=GenerationType.AUTO, generator="my_entity_seq_gen")
	  @SequenceGenerator(name="my_entity_seq_gen", sequenceName="idSolicitud_seq")
	  private Integer idCodigo;
	private String tipoCambio;
	@ManyToOne(fetch=FetchType.EAGER)
	  @JoinColumn(name="tipoDocumento")
	  private TipoDocumento tipoDocumento;
	
	@ManyToOne(cascade={javax.persistence.CascadeType.MERGE}, fetch=FetchType.EAGER)
	  @JoinColumn(name="padre")
	  private Documento padre;
	
	@ManyToOne(cascade={javax.persistence.CascadeType.MERGE}, fetch=FetchType.EAGER)
	  @JoinColumn(name="documentoCambio")
	  private Documento documentoCambio;
	
	@ManyToOne(fetch=FetchType.EAGER)
	  @JoinColumn(name="area")
	  private Area area;
	@ManyToOne( fetch=FetchType.EAGER)
	  @JoinColumn(name="subArea")
	  private Area subArea;
	
  
  @OneToMany(cascade={javax.persistence.CascadeType.ALL}, fetch=FetchType.EAGER)
  @JoinColumn(name="documento")
  private Set<HistorialSolicitud> ocurrencias = new HashSet();
  
  private String estatus = "Nueva";
  
  @ManyToOne(fetch=FetchType.EAGER)
  @JoinColumn(name="elaborador")
  private Usuario usuarioElaborador;
  @ManyToOne(fetch=FetchType.EAGER)
  @JoinColumn(name="usuarioActual")
  private Usuario usuarioActual;
  
  private String cargoElaborador;
  private Date fechaElaboracion = new Date();
  private Date fechaAprobacion;
  
  @OneToMany(mappedBy="solicitud", fetch=FetchType.EAGER, cascade={javax.persistence.CascadeType.ALL})
  @Fetch(FetchMode.SELECT)
  @NotFound(action=NotFoundAction.IGNORE)
  private Set<SolicitudAprobador> aprobadores = new HashSet();
  
  @OneToMany(mappedBy="solicitud", fetch=FetchType.EAGER, cascade={javax.persistence.CascadeType.ALL})
  @Fetch(FetchMode.SELECT)
  @NotFound(action=NotFoundAction.IGNORE)
  private Set<HistorialSolicitudAprobador> historicoAprobadores = new HashSet();
  
  
public Integer getIdCodigo() {
	return idCodigo;
}

public void setIdCodigo(Integer idCodigo) {
	this.idCodigo = idCodigo;
}

public String getTipoCambio() {
	return tipoCambio;
}

public void setTipoCambio(String tipoCambio) {
	this.tipoCambio = tipoCambio;
}

public TipoDocumento getTipoDocumento() {
	return tipoDocumento;
}

public void setTipoDocumento(TipoDocumento tipoDocumento) {
	this.tipoDocumento = tipoDocumento;
}

public Documento getPadre() {
	return padre;
}

public void setPadre(Documento padre) {
	this.padre = padre;
}

public Documento getDocumentoCambio() {
	return documentoCambio;
}

public void setDocumentoCambio(Documento documentoCambio) {
	this.documentoCambio = documentoCambio;
}

public Area getArea() {
	return area;
}

public void setArea(Area area) {
	this.area = area;
}

public Area getSubArea() {
	return subArea;
}

public void setSubArea(Area subArea) {
	this.subArea = subArea;
}

public Set<HistorialSolicitud> getOcurrencias() {
	return ocurrencias;
}

public void setOcurrencias(Set<HistorialSolicitud> ocurrencias) {
	this.ocurrencias = ocurrencias;
}

public String getEstatus() {
	return estatus;
}

public void setEstatus(String estatus) {
	this.estatus = estatus;
}

public Usuario getUsuarioElaborador() {
	return usuarioElaborador;
}

public void setUsuarioElaborador(Usuario usuarioElaborador) {
	this.usuarioElaborador = usuarioElaborador;
}

public Usuario getUsuarioActual() {
	return usuarioActual;
}

public void setUsuarioActual(Usuario usuarioActual) {
	this.usuarioActual = usuarioActual;
}

public String getCargoElaborador() {
	return cargoElaborador;
}

public void setCargoElaborador(String cargoElaborador) {
	this.cargoElaborador = cargoElaborador;
}

public Date getFechaElaboracion() {
	return fechaElaboracion;
}

public void setFechaElaboracion(Date fechaElaboracion) {
	this.fechaElaboracion = fechaElaboracion;
}

public Date getFechaAprobacion() {
	return fechaAprobacion;
}

public void setFechaAprobacion(Date fechaAprobacion) {
	this.fechaAprobacion = fechaAprobacion;
}

public Set<SolicitudAprobador> getAprobadores() {
	return aprobadores;
}

public void setAprobadores(Set<SolicitudAprobador> aprobadores) {
	this.aprobadores = aprobadores;
}

public Set<HistorialSolicitudAprobador> getHistoricoAprobadores() {
	return historicoAprobadores;
}

public void setHistoricoAprobadores(Set<HistorialSolicitudAprobador> historicoAprobadores) {
	this.historicoAprobadores = historicoAprobadores;
}

public SolicitudAprobador aprobadorActual(Usuario actual)
{
	ordenarAprobadores();
	for(SolicitudAprobador da : getAprobadores())
	{
		if (da.getAprobador().equals(actual))
		{
			return da;
		}
	}
	return null;
}

public SolicitudAprobador proximoAprobador()
{
	ordenarAprobadores();
	for(SolicitudAprobador da : getAprobadores())
	{
		if (da.getResultado() == null)
		{
			return da;
		}
	}
	return null;
}

public SolicitudAprobador ultimoAprobador()
{
	ordenarAprobadores();
	List lista = new ArrayList(getAprobadores());
	for(int i = 0; i < lista.size(); i++)
	{
		SolicitudAprobador da = (SolicitudAprobador) lista.get(i); 
		if (da.getResultado() == null)
		{
			if (i > 0)
			{
				return (SolicitudAprobador) lista.get(i - 1);
			}
			else
			{
				return null;
			}
		}
	}
	
	return (SolicitudAprobador) lista.get(lista.size() - 1);
}

public void ordenarAprobadores()
{
	  List listaAprobadores= new ArrayList(this.aprobadores);
		Collections.sort(listaAprobadores, new Comparator(){
			
			@Override
			public int compare(Object o1, Object o2) {
				org.modelo.dto.SolicitudAprobador dca1 = (org.modelo.dto.SolicitudAprobador) o1;
				org.modelo.dto.SolicitudAprobador dca2 = (org.modelo.dto.SolicitudAprobador) o2;
				return dca1.getOrden().compareTo(dca2.getOrden());
			}});
		
		this.setAprobadores(new LinkedHashSet(listaAprobadores));
}
}
