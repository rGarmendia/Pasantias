package org.modelo.dto;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@javax.persistence.Entity
public class Permiso
{
  @javax.persistence.Id
  @GeneratedValue(strategy=GenerationType.AUTO, generator="my_entity_seq_gen")
  @SequenceGenerator(name="my_entity_seq_gen", sequenceName="idPermiso_seq")
  private Integer codigo;
  @ManyToOne(fetch=FetchType.EAGER, cascade={javax.persistence.CascadeType.ALL})
  @JoinColumn(name="operacion")
  private Operacion operacion;
  private String ventana;
  @ManyToOne(cascade={javax.persistence.CascadeType.ALL}, fetch=FetchType.EAGER)
  @JoinColumn(name="rol")
  private Rol rol;
  
  public Integer getCodigo()
  {
    return this.codigo;
  }
  
  public void setCodigo(Integer codigo) { this.codigo = codigo; }
  
  public Operacion getOperacion() {
    return this.operacion;
  }
  
  public void setOperacion(Operacion operacion) { this.operacion = operacion; }
  
  public String getVentana() {
    return this.ventana;
  }
  
  public void setVentana(String ventana) { this.ventana = ventana; }
  
  public Rol getRol() {
    return this.rol;
  }
  
  public void setRol(Rol rol) { this.rol = rol; }
}
