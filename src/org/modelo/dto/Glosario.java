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
import javax.persistence.SequenceGenerator;
import javax.persistence.UniqueConstraint;


@Entity
public class Glosario
  implements Serializable
{
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO, generator="my_entity_seq_gen")
  @SequenceGenerator(name="my_entity_seq_gen", sequenceName="idGlosario_seq")
  private Integer codigo;
  private String nombre;
  private String descripcion;
  private String estatus="Activo";
  
  
  
  public String getEstatus() {
	return estatus;
}

public void setEstatus(String estatus) {
	this.estatus = estatus;
}

public Integer getCodigo()
  {
    return this.codigo;
  }
  
  public void setCodigo(Integer codigo) { this.codigo = codigo; }
  
  public String getNombre()
  {
    return this.nombre;
  }
  
  public void setNombre(String nombre) { this.nombre = nombre; }
  
  public String getDescripcion() {
    return this.descripcion;
  }
  
  public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
  

  public boolean equals(Object other)
  {
    if ((other instanceof Glosario))
    {
      Glosario grupo = (Glosario)other;
      return grupo.getCodigo().equals(getCodigo());
    }
    

    return false;
  }
}
