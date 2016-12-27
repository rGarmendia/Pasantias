package org.modelo.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class ValorOpcion {
	@javax.persistence.Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="my_entity_seq_gen")
	@SequenceGenerator(name="my_entity_seq_gen", sequenceName="idValorOpcion_seq")
	private Integer codigo;
	private String valorResul;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="valor")
	private Valor valor;
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public String getValorResul() {
		return valorResul;
	}
	public void setValorResul(String valorResul) {
		this.valorResul = valorResul;
	}
	public Valor getValor() {
		return valor;
	}
	public void setValor(Valor valor) {
		this.valor = valor;
	}
}
