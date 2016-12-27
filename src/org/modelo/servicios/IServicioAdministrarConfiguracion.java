package org.modelo.servicios;

import java.util.List;
import java.util.Map;

import org.modelo.dto.Area;
import org.modelo.dto.Campo;
import org.modelo.dto.Documento;
import org.modelo.dto.Glosario;
import org.modelo.dto.Grupo;
import org.modelo.dto.Notificacion;
import org.modelo.dto.Opcion;
import org.modelo.dto.Rol;
import org.modelo.dto.RutaAprobacionSeguridad;
import org.modelo.dto.TipoDocumento;
import org.modelo.dto.Usuario;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Window;

public abstract interface IServicioAdministrarConfiguracion
{
  public abstract Usuario buscarUsuarioPorLogin(String paramString);
  
  public abstract void verificarUsuarioDuplicado(Usuario paramUsuario);
  
  public abstract void verificarUsuario(Usuario usuario);
  
  public abstract void guardarUsuario(Usuario paramUsuario);
  
  public abstract void borrarUsuario(Usuario paramUsuario);
  
  public abstract Usuario validarUsuario(String paramString1, String paramString2);
  
  public void validarUsuario(Usuario usuario);
  
  public abstract List buscarTodosUsuarios();
  
  public abstract void cambiarContrasena(Object paramObject, String paramString1, String paramString2, String paramString3);
  
  public abstract List buscarTodasTareasPorUsuario(Usuario paramUsuario);
  
  public abstract List buscarTodasNotificacionesPorUsuario(Usuario paramUsuario);
  
  public abstract void verificarDatosCompletosPorUsuario(Usuario paramUsuario);
  
  public abstract void eliminarNotificacion(Notificacion paramNotificacion);
  
  public abstract List buscarTareasPorUsuarioPorRol(Usuario paramUsuario, String paramString);
  
  public abstract List buscarOperacionesTodas();
  
  public abstract List buscarRolesTodos();
  
  public abstract void guardarPermisos(List<Checkbox> listaChecks, Rol rol);
  
  public abstract List buscarPermisosPorRol(Rol paramRol);
  
  public abstract List buscarPermisoMenusPorRol(String paramString);
  
  public abstract List buscarPermisosPorVentanaRol(String paramString1, String paramString2);
  
  public abstract void guardarRol(Rol paramRol);
  
  public abstract Rol buscarRolPorNombre(String paramString);
  
  public abstract void guardarNotificacion(Notificacion paramNotificacion);
  
  public abstract List buscarUsuariosPorRol(String paramString);

  public abstract List buscarAreasTodas();
  
  public void verificarArea(Area area);
  public void guardarArea(Area area);
  public List buscarTiposDocumentosTodos();
  public void verificarTipoDocumento(TipoDocumento tipoDocumento);
  public void guardarTipoDocumento(TipoDocumento tipoDocumento);
  public void verificarCampo(Campo campo);
  public void verificarOpcion(Opcion opcion);
  public List buscarGruposTodos();
  public List buscarUsuariosPorApellidos(String texto);
  public List buscarUsuariosPorNombres(String texto);
  public void verificarGrupo(Grupo grupo);
  public void guardarGrupo(Grupo grupo);
  public List buscarRutasAprobacionPorAreaTipo(Area area, Area subArea, TipoDocumento tipoDocumento);
  public List buscarRutasAprobacionTodos();
  public void verificarRutaAprobacion(RutaAprobacionSeguridad rutaAprobacion);
  public void guardarRutaAprobacion(RutaAprobacionSeguridad rutaAprobacion);
  public Grupo buscarGrupoGeneral();
  public List buscarTiposDocumentosPrincipales();
  public TipoDocumento buscarTipoDocumentoPorCodigo(Integer codigo);
  public void asignarValoresDocumento(Documento documento, Window ventana);
  public void verificarDocumento(Documento documento);
  public void guardarDocumento(Documento documento);
  public List buscarEstadosNoVigenteDocumentosPorTipo(TipoDocumento tipoDocumento, Usuario usuario);
  public List buscarAreasNoVigenteDocumentosPorTipo(TipoDocumento tipoDocumento, Usuario usuario);
  public List buscarAreasVigenteDocumentosPorTipo(TipoDocumento tipoDocumento);
  public List buscarDocumentosPorEstadoAreaTipo(String estado, String area, TipoDocumento tipoDocumento, Usuario usuario);
  public List buscarDocumentosVigentesPorAreaTipo(String area, TipoDocumento tipoDocumento);
  public RutaAprobacionSeguridad buscarAprobadoresPorTipoArea(TipoDocumento tipoDocumento, Area area, Area subArea);
  public List buscarProximoAprobadoresDocumentosEnRevisionPorTipo(TipoDocumento tipoDocumento);
  public List buscarDocumentosPorProximoAprobadorTipo(Usuario usuario, TipoDocumento tipoDocumento);
  public void verificarDocumentoCompleto(Documento documento);
  public void enviarNotificacionDocumento(String mensaje, Documento documento);
  public void eliminarRol(Rol rol);
  public void eliminarGrupo(Grupo grupo);
  public void eliminarTipoDocumento(TipoDocumento tipoDocumento);
  public void eliminarArea(Area area);
  public void eliminarUsuario(Usuario usuario);
  public void eliminarRutaAprobacionSeguridad(RutaAprobacionSeguridad rutaAprobacionSeguridad);
  public List buscarGlosariosTodos();
  public void guardarGlosario(Glosario glosario);
  public void eliminarGlosario(Glosario glosario);
  public void verificarGlosario(Glosario glosario);
  public List buscarDocumentosPorTipoEstado(List<TipoDocumento> tipos, String estado);
  public void verificarTermino(Glosario glosario);
  public void guardarTermino(Glosario glosario);
  public List buscarTerminosPorNombre(String texto);
  public List buscarTodosTerminos();
  public List buscarTerminosPorNombreActivos(String texto);
  public List buscarTodosTerminosActivos();
  public void eliminarDocumento(Documento documento);
  public List buscarDocumentosVigentesPorAreaTipo(String area, TipoDocumento tipoDocumento, List exs);
}
