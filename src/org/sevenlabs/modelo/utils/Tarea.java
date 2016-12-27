package org.sevenlabs.modelo.utils;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import org.modelo.dto.Usuario;


















public class Tarea
  implements Serializable, Comparable
{
  private String idtarea;
  private String tipoTarea;
  private Date fecha;
  private String estado;
  private String tiempoEspera;
  private Usuario usuario;
  private Object objeto;
  
  public Tarea() {}
  
  public Tarea(String idtarea, String tipoTarea, Date fecha, String estado, String tiempoEspera, Usuario usuario)
  {
    this.idtarea = idtarea;
    this.tipoTarea = tipoTarea;
    this.fecha = fecha;
    this.estado = estado;
    this.tiempoEspera = tiempoEspera;
    this.usuario = usuario;
  }
  







  public Object getObjeto()
  {
    return this.objeto;
  }
  
  public void setObjeto(Object objeto) {
    this.objeto = objeto;
  }
  
  public String getIdtarea() {
    return this.idtarea;
  }
  
  public void setIdtarea(String idtarea) {
    this.idtarea = idtarea;
  }
  
  public String getTipoTarea() {
    return this.tipoTarea;
  }
  
  public void setTipoTarea(String tipoTarea) {
    this.tipoTarea = tipoTarea;
  }
  
  public Date getFecha() {
    return this.fecha;
  }
  
  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }
  
  public String getEstado() {
    return this.estado;
  }
  
  public void setEstado(String estado) {
    this.estado = estado;
  }
  
  public String getTiempoEspera() {
    String devTiempo = "";
    Long milisegundos_dia = new Long(3600000L);
    Calendar hoy = Calendar.getInstance();
    hoy.setTime(getFecha());
    Integer tiempo = Integer.valueOf(new Long((new Long(Calendar.getInstance().getTimeInMillis()).longValue() - new Long(hoy.getTimeInMillis()).longValue()) / milisegundos_dia.longValue()).intValue());
    



















    return "";
  }
  
  public void setTiempoEspera(String tiempoEspera) {
    this.tiempoEspera = tiempoEspera;
  }
  
  public Usuario getUsuario() {
    return this.usuario;
  }
  
  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }
  
  public int compareTo(Object o) {
    Tarea tarea = (Tarea)o;
    
    return getTiempoEspera().compareTo(tarea.getTiempoEspera());
  }
}
