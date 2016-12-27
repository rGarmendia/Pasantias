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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class Valor
  implements Serializable
{
	@javax.persistence.Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="my_entity_seq_gen")
	@SequenceGenerator(name="my_entity_seq_gen", sequenceName="idValor_seq")
	private Integer codigo;
  @ManyToOne(fetch=FetchType.EAGER)
  @JoinColumn(name="documento")
  private Documento documento;
  @ManyToOne(fetch=FetchType.EAGER)
  @JoinColumn(name="campo")
  private Campo campo;
  private String valor;
  @OneToMany(cascade={javax.persistence.CascadeType.ALL}, fetch=FetchType.EAGER)
	@JoinColumn(name="valor")
	private Set<ValorOpcion> valoresOpciones = new HashSet<ValorOpcion>();
  
  @ManyToMany(cascade={javax.persistence.CascadeType.ALL}, fetch=FetchType.EAGER)
  @JoinTable(name="campo_tipodocumento", joinColumns={@JoinColumn(name="campo", referencedColumnName="codigo")}, inverseJoinColumns={@JoinColumn(name="tipodocumento", referencedColumnName="codigo")})
  private Set<TipoDocumento> tipos = new HashSet();
@ManyToMany(cascade={javax.persistence.CascadeType.ALL}, fetch=FetchType.EAGER)
  @JoinTable(name="campo_usuario", joinColumns={@JoinColumn(name="campo", referencedColumnName="codigo")}, inverseJoinColumns={@JoinColumn(name="usuario", referencedColumnName="login")})
  private Set<Usuario> usuarios = new HashSet();
@ManyToMany(cascade={javax.persistence.CascadeType.ALL}, fetch=FetchType.EAGER)
  @JoinTable(name="campo_glosario", joinColumns={@JoinColumn(name="campo", referencedColumnName="codigo")}, inverseJoinColumns={@JoinColumn(name="glosario", referencedColumnName="codigo")})
  private Set<Glosario> terminos = new HashSet();


public Set<ValorOpcion> getValoresOpciones() {
	return valoresOpciones;
}
public void setValoresOpciones(Set<ValorOpcion> valoresOpciones) {
	this.valoresOpciones = valoresOpciones;
}
public Integer getCodigo() {
	return codigo;
}
public void setCodigo(Integer codigo) {
	this.codigo = codigo;
}
public Documento getDocumento() {
	return documento;
}
public void setDocumento(Documento documento) {
	this.documento = documento;
}
public Campo getCampo() {
	return campo;
}
public void setCampo(Campo campo) {
	this.campo = campo;
}
public String getValor() {
	return valor;
}
public void setValor(String valor) {
	this.valor = valor;
}
}
