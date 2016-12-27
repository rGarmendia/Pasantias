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


@Entity
public class Grupo
  implements Serializable
{
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO, generator="my_entity_seq_gen")
  @SequenceGenerator(name="my_entity_seq_gen", sequenceName="idGrupo_seq")
  private Integer codigo;
  @Column(length=50)
  private String nombre;
  @Column(length=200)
  private String descripcion;
  @ManyToMany(cascade={javax.persistence.CascadeType.ALL}, fetch=FetchType.EAGER)
  @JoinTable(name="grupo_usuario", joinColumns={@JoinColumn(name="grupo", referencedColumnName="codigo")}, inverseJoinColumns={@JoinColumn(name="usuario", referencedColumnName="login")})
  private Set<Usuario> usuarios = new HashSet();
  
  public Set<Usuario> getUsuarios() {
	return usuarios;
}

public void setUsuarios(Set<Usuario> usuarios) {
	this.usuarios = usuarios;
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
    if ((other instanceof Grupo))
    {
      Grupo grupo = (Grupo)other;
      return grupo.getCodigo().equals(getCodigo());
    }
    

    return false;
  }
}
