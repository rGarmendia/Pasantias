package org.sevenlabs.comunes;

import java.util.HashMap;
import org.modelo.dto.Usuario;

public class ContextoImpl
  implements Contexto
{
  private Usuario usuarioActual;
  private HashMap datosAplicacion = new HashMap();
  
  public Usuario getUsuarioActual() {
    return this.usuarioActual;
  }
  
  public void setUsuarioActual(Usuario usuario) {
    this.usuarioActual = usuario;
  }
  
  public HashMap getDatosAplicacion() { return this.datosAplicacion; }
}
