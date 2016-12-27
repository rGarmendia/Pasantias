package org.modelo.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name="usuario")
@Inheritance(strategy=InheritanceType.JOINED)
public class Usuario
  implements Cloneable, Serializable
{
  @Id
  private String login;
  @Column(name="password", length=15)
  private String password;
  @Column(name="nombrecompleto", length=30)
  private String nombreCompleto;
  private String nombres;
  private String apellidos;
  @ManyToOne(fetch=FetchType.EAGER)
  @JoinColumn(name="rol")
  private Rol rolSistema;
  @Column(name="correo", length=30)
  private String correo;
  @Column(name="estatus", length=10)
  private String estatus;
  @Column(name="celular1", length=18)
  private String celular1;
  @Column(name="celular2", length=18)
  private String celular2;
  @Column(name="celular3", length=18)
  private String celular3;
  @Column(name="celular4", length=18)
  private String celular4;
  @ManyToOne(cascade={javax.persistence.CascadeType.ALL},fetch=FetchType.EAGER)
  @JoinColumn(name="cargo")
  private Documento cargo;
  @ManyToMany(mappedBy="usuarios", fetch=FetchType.EAGER)
  private Set<Grupo> grupos = new HashSet();


  public Documento getCargo() {
	return cargo;
}


public void setCargo(Documento cargo) {
	this.cargo = cargo;
}


public String getNombres() {
	return nombres;
}


public void setNombres(String nombres) {
	this.nombres = nombres;
}


public String getApellidos() {
	return apellidos;
}


public void setApellidos(String apellidos) {
	this.apellidos = apellidos;
}


public Set<Grupo> getGrupos() {
	return grupos;
}


public void setGrupos(Set<Grupo> grupos) {
	this.grupos = grupos;
}


public String getCorreo()
  {
    return this.correo;
  }
  

  public String getCelular4()
  {
    return this.celular4;
  }
  

  public void setCelular4(String celular4)
  {
    this.celular4 = celular4;
  }
  

  public String getCelular2()
  {
    return this.celular2;
  }
  

  public void setCelular2(String celular2)
  {
    this.celular2 = celular2;
  }
  

  public String getCelular3()
  {
    return this.celular3;
  }
  

  public void setCelular3(String celular3)
  {
    this.celular3 = celular3;
  }
  

  public String getCelular1()
  {
    return this.celular1;
  }
  
  public void setCelular1(String celular1) {
    this.celular1 = celular1;
  }
  
  public String getEstatus() {
    return this.estatus;
  }
  
  public void setEstatus(String estatus) {
    this.estatus = estatus;
  }
  
  public void setCorreo(String correo) {
    this.correo = correo;
  }
  

  public Usuario() {}
  
  public String getLogin()
  {
    return this.login;
  }
  
  public void setLogin(String login) {
    this.login = login;
  }
  
  public String getPassword() {
    return this.password;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
  
  public String getNombreCompleto() {
    return this.nombreCompleto;
  }
  
  public void setNombreCompleto(String nombreCompleto) {
    this.nombreCompleto = nombreCompleto;
  }
  
  public Rol getRolSistema() {
    return this.rolSistema;
  }
  
  public void setRolSistema(Rol rolSistema) {
    this.rolSistema = rolSistema;
  }
  

  public Usuario(String login, String password, String nombreCompleto, Rol rolSistema)
  {
    this.login = login;
    this.password = password;
    this.nombreCompleto = nombreCompleto;
    this.rolSistema = rolSistema;
  }
  
  public Object clone() throws CloneNotSupportedException
  {
    return super.clone();
  }
  

  public boolean equals(Object objeto)
  {
    Usuario usu = (Usuario)objeto;
    return getLogin().equals(usu.getLogin());
  }
}
