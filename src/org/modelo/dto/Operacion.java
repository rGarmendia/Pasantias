package org.modelo.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.SequenceGenerator;

@javax.persistence.Entity
public class Operacion
{
  @javax.persistence.Id
  @GeneratedValue(strategy=javax.persistence.GenerationType.AUTO, generator="my_entity_seq_gen")
  @SequenceGenerator(name="my_entity_seq_gen", sequenceName="idOperacion_seq")
  private Integer codigo;
  @Column(length=15)
  private String nombre;
  
  public Integer getCodigo()
  {
    return this.codigo;
  }
  
  public void setCodigo(Integer codigo) { this.codigo = codigo; }
  
  public String getNombre() {
    return this.nombre;
  }
  
  public void setNombre(String nombre) { this.nombre = nombre; }
}
