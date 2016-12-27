package org.modelo.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class Campo implements Serializable{
	
	@javax.persistence.Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="my_entity_seq_gen")
	@SequenceGenerator(name="my_entity_seq_gen", sequenceName="idCampo_seq")
	private Integer codigo;
	private String nombre;
	private String tipo;
	private String tipoDato;
	private String tipoReferencia;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="tipoDocumento")
	private TipoDocumento tipoDocumento;
	@OneToMany(cascade={javax.persistence.CascadeType.ALL}, fetch=FetchType.EAGER)
	@JoinColumn(name="campo")
	private Set<Opcion> opciones = new HashSet<Opcion>();
	private String estatus = "Activo";
	private Integer longAncho;
	private Integer longAlto;
	private Integer cantMax;
	private Integer orden;
	private boolean obligatorio = false;
	private Integer cantidadReferencia = 0;
	
	@ManyToMany(cascade={javax.persistence.CascadeType.MERGE}, fetch=FetchType.EAGER)
	  @JoinTable(name="campo_tipodocumento", joinColumns={@JoinColumn(name="campo", referencedColumnName="codigo")}, inverseJoinColumns={@JoinColumn(name="tipodocumento", referencedColumnName="codigo")})
	  private Set<TipoDocumento> tipos = new HashSet();
	
	
 public String getTipoReferencia() {
		return tipoReferencia;
	}
	public void setTipoReferencia(String tipoReferencia) {
		this.tipoReferencia = tipoReferencia;
	}
	public Integer getCantidadReferencia() {
		return cantidadReferencia;
	}
	public void setCantidadReferencia(Integer cantidadReferencia) {
		this.cantidadReferencia = cantidadReferencia;
	}
public Set<TipoDocumento> getTipos() {
		return tipos;
	}
	public void setTipos(Set<TipoDocumento> tipos) {
		this.tipos = tipos;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Campo other = (Campo) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
public boolean isObligatorio() {
		return obligatorio;
	}
	public void setObligatorio(boolean obligatorio) {
		this.obligatorio = obligatorio;
	}
public void asignarOrden() {
	 this.orden = 1;
		if (this.tipoDocumento != null)
		{
			List<Campo> lista = new ArrayList<Campo>(this.tipoDocumento.getCampos());
			for (Campo ca : lista)
			{
				if (ca.getOrden() >= this.orden)
				{
					this.orden = ca.getOrden() + 1; 
				}
			}
		}
	}
public String getTipoDato() {
		return tipoDato;
	}
	public void setTipoDato(String tipoDato) {
		this.tipoDato = tipoDato;
	}
	public Integer getOrden() {
		return orden;
	}
	public void setOrden(Integer orden) {
		this.orden = orden;
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
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public Set<Opcion> getOpciones() {
		return opciones;
	}
	public void setOpciones(Set<Opcion> opciones) {
		this.opciones = opciones;
	}
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	public Integer getLongAncho() {
		return longAncho;
	}
	public void setLongAncho(Integer longAncho) {
		this.longAncho = longAncho;
	}
	public Integer getLongAlto() {
		return longAlto;
	}
	public void setLongAlto(Integer longAlto) {
		this.longAlto = longAlto;
	}
	public Integer getCantMax() {
		return cantMax;
	}
	public void setCantMax(Integer cantMax) {
		this.cantMax = cantMax;
	}
}
