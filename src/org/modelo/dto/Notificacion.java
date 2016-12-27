package org.modelo.dto;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="Notificion")
public class Notificacion
{
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO, generator="my_entity_seq_gen")
  @SequenceGenerator(name="my_entity_seq_gen", sequenceName="Notificacion_seq")
  private Integer codigo;
  @Column(name="fecha")
  private Date fecha;
  @Column(name="texto", length=100)
  private String texto;
  @Column(name="usuarios", length=20)
  private String usuarios;
  @Column(name="tipo")
  private Integer tipo;
  
  public Integer getCodigo()
  {
    return this.codigo;
  }
  
  public void setCodigo(Integer codigo) { this.codigo = codigo; }
  
  public Integer getTipo() {
    return this.tipo;
  }
  
  public void setTipo(Integer tipo) { this.tipo = tipo; }
  


  public Date getFecha()
  {
    return this.fecha;
  }
  
  public void setFecha(Date fecha) { this.fecha = fecha; }
  
  public String getTexto() {
    return this.texto;
  }
  
  public void setTexto(String texto) { this.texto = texto; }
  
  public String getUsuarios() {
    return this.usuarios;
  }
  
  public void setUsuarios(String usuarios) { this.usuarios = usuarios; }
}
