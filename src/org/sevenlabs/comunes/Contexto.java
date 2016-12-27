package org.sevenlabs.comunes;

import java.util.HashMap;
import org.modelo.dto.Usuario;

public abstract interface Contexto
{
  public static final String ID_CONTEXTO = "CONTEXTO";
  
  public abstract Usuario getUsuarioActual();
  
  public abstract void setUsuarioActual(Usuario paramUsuario);
  
  public abstract HashMap getDatosAplicacion();
}
