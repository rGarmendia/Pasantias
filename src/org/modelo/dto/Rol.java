package org.modelo.dto;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;



















@Entity
public class Rol
  implements Serializable
{
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO, generator="my_entity_seq_gen")
  @SequenceGenerator(name="my_entity_seq_gen", sequenceName="idAreaTematica_seq")
  private Integer codigo;
  @Column(length=50)
  private String nombre;
  @Column(length=200)
  private String funciones;
  
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
  
  public String getFunciones() {
    return this.funciones;
  }
  
  public void setFunciones(String funciones) { this.funciones = funciones; }
  


  public boolean equals(Object other)
  {
    if ((other instanceof Rol))
    {
      Rol rol = (Rol)other;
      return rol.getCodigo().equals(getCodigo());
    }
    

    return false;
  }
}
