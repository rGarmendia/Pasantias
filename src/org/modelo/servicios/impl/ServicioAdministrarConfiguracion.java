package org.modelo.servicios.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.modelo.dto.Area;
import org.modelo.dto.Campo;
import org.modelo.dto.Documento;
import org.modelo.dto.Glosario;
import org.modelo.dto.Grupo;
import org.modelo.dto.Notificacion;
import org.modelo.dto.Opcion;
import org.modelo.dto.Operacion;
import org.modelo.dto.Permiso;
import org.modelo.dto.Rol;
import org.modelo.dto.RutaAprobacionSeguridad;
import org.modelo.dto.Tarea;
import org.modelo.dto.RutaAprobacionAprobador;
import org.modelo.dto.TipoDocumento;
import org.modelo.dto.Usuario;
import org.modelo.dto.Valor;
import org.modelo.dto.ValorOpcion;
import org.modelo.servicios.IServicioAdministrarConfiguracion;
import org.sevenlabs.exception.SevenLabsException;
import org.sevenlabs.modelo.dao.IHibernateDAO;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.utils.ValidacionUtils;
import org.zkforge.ckez.CKeditor;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;




@Transactional
@Service
public class ServicioAdministrarConfiguracion
  implements IServicioAdministrarConfiguracion
{
  private IHibernateDAO hibernateDAO;
  
  public List buscarTodasTareasPorUsuario(Usuario usuario)
  {
    List objetosTareas = new ArrayList();
    List<Documento> documentos=hibernateDAO.findByNamedParam("from Documento doc where doc.usuarioActual=:usuario and (doc.estatus='En Revision' or doc.estatus='Rechazado')","usuario",usuario);
    if (usuario.getRolSistema().equals(buscarRolPorNombre("Administrador")))
    {
    	documentos.addAll(hibernateDAO.find("from Documento doc where doc.estatus='Aprobado'"));
    } 
    	
    for (Documento doc : documentos)
    {
    	Tarea tarea = new Tarea();
    	tarea.setEstado(doc.getEstatus());
    	if (doc.getEstatus().equals("Rechazado"))
    	{
    		tarea.setFecha(doc.ultimoAprobador().getFechaRecepcion());
    		tarea.setEstado("Por Reenviar");
    	}
    	else if (doc.getEstatus().equals("Aprobado"))
    	{
			tarea.setFecha(doc.ultimoAprobador().getFechaAprobacion());
    		tarea.setEstado("Por Actualizar");
    	}
    	else
    	{
    		if (doc.getEstatus().equals("En Revision"))
        	{
        		tarea.setEstado("Por Aprobar");
        	}
    		tarea.setFecha(doc.aprobadorActual(usuario).getFechaRecepcion());
    	}
    	tarea.setIdtarea(doc.getCodigo() == null ? doc.getIdCodigo().toString() : doc.getCodigo());
    	tarea.setObjeto(doc);
    	tarea.setTipoTarea(doc.getTipoDocumento().getNombre());
    	tarea.setUsuarioCreador(doc.getUsuarioActual());
    	objetosTareas.add(tarea);
    }
    
    Collections.sort(objetosTareas);
    return objetosTareas;
  }
  


  public void verificarUsuarioDuplicado(Usuario usuario)
  {
    List usuarios = new ArrayList();
    
    List rest = new ArrayList();
    rest.add(Restrictions.eq("login", usuario.getLogin()));
    usuarios = this.hibernateDAO.findByCriterions(Usuario.class, rest);
    
    if (usuarios.size() > 0)
    {
      throw new SevenLabsException("There is Already a User With That Login");
    }
  }
  


  public Usuario buscarUsuarioPorLogin(String login)
  {
    List usuarios = new ArrayList();
    
    List rest = new ArrayList();
    rest.add(Restrictions.eq("login", login));
    usuarios = this.hibernateDAO.findByCriterions(Usuario.class, rest);
    
    if (usuarios.size() > 0)
    {
      return (Usuario)usuarios.get(0);
    }
    

    return null;
  }
  
  public IHibernateDAO getHibernateDAO()
  {
    return this.hibernateDAO;
  }
  
  public void setHibernateDAO(IHibernateDAO hibernateDAO) {
    this.hibernateDAO = hibernateDAO;
  }
  
  public Usuario validarUsuario(String login, String password)
  {
    try
    {
      List rest = new ArrayList();
      rest.add(Restrictions.eq("login", login));
      rest.add(Restrictions.eq("password", password));
      rest.add(Restrictions.eq("estatus", "Activo"));
      List lista = this.hibernateDAO.findByCriterions(Usuario.class, rest);
      if (lista.size() > 0)
      {
        return (Usuario)lista.get(0);
      }
      

      throw new SevenLabsException(
        "Incorrect User Credentials");
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new SevenLabsException("Incorrect User Credentials");
    }
  }
  

  public void guardarUsuario(Usuario usuario)
  {
    this.hibernateDAO.saveOrUpdate(usuario);
  }
  

  public List buscarTodosUsuarios()
  {
    List listaUsuario = new ArrayList();
    listaUsuario.addAll(this.hibernateDAO.findAll(Usuario.class));
    return listaUsuario;
  }
  
 
  public void borrarUsuario(Usuario usuario)
  {
    this.hibernateDAO.delete(usuario);
  }
  

  public void cambiarContrasena(Object user, String antContrasena, String nuevaContrasena, String confContrasena)
  {
    if ((user instanceof Usuario))
    {

      Usuario usuario = (Usuario)user;
      if (usuario.getPassword().equals(antContrasena)) {
        if ((nuevaContrasena != null) && (nuevaContrasena.trim().length() > 0)) {
          if (nuevaContrasena.equals(confContrasena)) {
            usuario.setPassword(nuevaContrasena);
            this.hibernateDAO.saveOrUpdate(usuario);
          } else {
            throw new SevenLabsException("The New Password Mismatch");
          }
        } else {
          throw new SevenLabsException("New Password Can Not Be Empty");
        }
      } else {
        throw new SevenLabsException("Previous Password NOT Match With That User");
      }
    }
  }
  







  public List buscarTodasNotificacionesPorUsuario(Usuario usuario)
  {
    List orden = new ArrayList();
    orden.add(Order.desc("fecha"));
    
    List restrictions = new ArrayList();
    restrictions.add(Restrictions.or(Restrictions.like("usuarios", usuario.getLogin(), MatchMode.ANYWHERE), Restrictions.eq("usuarios", "todos")));
    
    return this.hibernateDAO.findByCriterions(Notificacion.class, restrictions, orden);
  }
  
  public void validarUsuario(Usuario usuario)
  {
	  ValidacionUtils.validarObjeto(usuario.getRolSistema(), "Debe Seleccionar un Rol");
	  ValidacionUtils.validarTexto(usuario.getLogin(), "Debe Ingresar un Nombre de Usuario");
	  ValidacionUtils.validarTexto(usuario.getPassword(), "Debe Ingresar un Password");
	  //ValidacionUtils.validarTexto(usuario.getNombreCompleto(), "Debe Ingresar el Nombre y Apellido del Usuario");
	  ValidacionUtils.validarTexto(usuario.getCorreo(),"Debe Ingresar un Correo");
	  ValidacionUtils.validarEmail(usuario.getCorreo());
	  ValidacionUtils.validarTexto(usuario.getEstatus(), "Debe Seleccionar un Estado");
  }

  public void verificarDatosCompletosPorUsuario(Usuario usuario)
  {
    boolean falta = false;
    String nota = "";
    if ((usuario.getCelular1() == null) && (usuario.getCelular2() == null) && (usuario.getCelular3() == null) && (usuario.getCelular4() == null))
    {
      nota = "At Least One Cell -";
      falta = true;
    }
    if (usuario.getCorreo() == null)
    {
      nota = nota + " Email -";
      falta = true;
    }
    if (usuario.getNombreCompleto() == null)
    {
      nota = nota + " Full Name -";
      falta = true;
    }
    















    if (falta)
    {
      if (!existeNotificacionPorTipoUsuario(Integer.valueOf(1), usuario.getLogin()))
      {
        Notificacion notificacion = new Notificacion();
        notificacion.setFecha(Calendar.getInstance().getTime());
        notificacion.setTexto("Complete Missing for the following personal data: " + nota);
        notificacion.setTipo(Integer.valueOf(1));
        notificacion.setUsuarios(usuario.getLogin());
        this.hibernateDAO.saveOrUpdate(notificacion);
      }
    }
  }
  
  public boolean existeNotificacionPorTipoUsuario(Integer tipo, String login)
  {
    List restrictions = new ArrayList();
    restrictions.add(Restrictions.like("usuarios", login, MatchMode.ANYWHERE));
    restrictions.add(Restrictions.eq("tipo", tipo));
    if (this.hibernateDAO.findByCriterions(Notificacion.class, restrictions).size() > 0)
    {
      return true;
    }
    

    return false;
  }
  


  public void eliminarNotificacion(Notificacion notificacion)
  {
    this.hibernateDAO.delete(notificacion);
  }
  
  public Usuario buscarUsuarioActivoPorRol(String rol)
  {
    List restrictions = new ArrayList();
    restrictions.add(Restrictions.eq("rolSistema.nombre", rol));
    restrictions.add(Restrictions.eq("estatus", "Activo"));
    List lista = this.hibernateDAO.findByCriterions(Usuario.class, restrictions);
    if (lista.size() > 0)
    {
      return (Usuario)lista.get(0);
    }
    

    return null;
  }
  




  public List buscarTareasPorUsuarioPorRol(Usuario usuario, String cual)
  {
    List lista = new ArrayList();
    
    return lista;
  }
  


  public List buscarOperacionesTodas()
  {
    return this.hibernateDAO.loadAll(Operacion.class);
  }
  



  public List buscarRolesTodos()
  {
    return this.hibernateDAO.findAll(Rol.class);
  }
  
  public void guardarPermiso(Permiso permiso)
  {
    this.hibernateDAO.saveOrUpdate(permiso);
  }
  
  public Operacion buscarOperacionPorNombre(String nombre)
  {
    List restricciones = new ArrayList();
    restricciones.add(Restrictions.eq("nombre", nombre));
    List lista = this.hibernateDAO.findByCriterions(Operacion.class, restricciones);
    if (lista.size() > 0)
    {
      return (Operacion)lista.get(0);
    }
    

    return null;
  }
  

  public void guardarPermisos(List<Checkbox> listaChecks, Rol rol)
  {
    if (listaChecks.size() > 0)
    {
      Object[] valores = new Object[1];
      valores[0] = rol;
      this.hibernateDAO.executeUpdateBySQL("delete from Permiso p where p.rol = ?", valores);
      this.hibernateDAO.flushSession();
      
      for (Checkbox chk : listaChecks)
      {
        if (chk.isChecked())
        {
          Permiso permiso = new Permiso();
          permiso.setOperacion(buscarOperacionPorNombre((String)chk.getValue()));
          permiso.setRol(rol);
          permiso.setVentana((String)((Row)chk.getParent()).getValue());
          guardarPermiso(permiso);
        }
      }
    }
  }
  

  public List buscarPermisosPorRol(Rol rol)
  {
    List restricciones = new ArrayList();
    restricciones.add(Restrictions.eq("rol", rol));
    return this.hibernateDAO.findByCriterions(Permiso.class, restricciones);
  }
  


  public List buscarPermisoMenusPorRol(String rol)
  {
    return this.hibernateDAO.findByNamedParam("select distinct(p.ventana) from Permiso p where p.rol.nombre=:rol", "rol", rol);
  }
  






  public List buscarPermisosPorVentanaRol(String ventana, String rol)
  {
    Object[] values = new Object[2];
    values[0] = rol;
    values[1] = ventana;
    
    String[] names = new String[2];
    names[0] = "nomRol";
    names[1] = "nomVentana";
    return this.hibernateDAO.findByNamedParam("from Permiso p where p.rol.nombre=:nomRol and p.ventana=:nomVentana", names, values);
  }
  

  public void guardarRol(Rol rol)
  {
    ValidacionUtils.validarTexto(rol.getNombre(), "You Must Insert The Name");
    this.hibernateDAO.saveOrUpdate(rol);
  }
  
  public Rol buscarRolPorNombre(String nombre)
  {
    List restricciones = new ArrayList();
    restricciones.add(Restrictions.eq("nombre", nombre).ignoreCase());
    List lista = this.hibernateDAO.findByCriterions(Rol.class, restricciones);
    if (lista.size() > 0)
    {
      return (Rol)lista.get(0);
    }
    

    return null;
  }
  


  

  public void guardarNotificacion(Notificacion notificacion)
  {
    this.hibernateDAO.saveOrUpdate(notificacion);
  }
  

  public List buscarUsuariosPorRol(String rol)
  {
    Rol rolS = buscarRolPorNombre(rol);
    List restricciones = new ArrayList();
    restricciones.add(Restrictions.eq("rolSistema", rolS));
    return this.hibernateDAO.findByCriterions(Usuario.class, restricciones);
  }



@Override
public List buscarAreasTodas() {
	
	return hibernateDAO.findByProperty(Area.class, Restrictions.isNull("areaPadre"));
}

public Area buscarAreaPorCodigo(String codigo)
{
	return (Area) hibernateDAO.findByKey(Area.class,codigo);
}


@Override
public void verificarArea(Area area) {
	ValidacionUtils.validarTexto(area.getCodigo(), "Debe Insertar un Codigo");
	ValidacionUtils.validarTexto(area.getNombre(), "Debe Insertar un Nombre");
	ValidacionUtils.validarTexto(area.getDescripcion(), "Debe Insertar una Descripcion");
	if (!hibernateDAO.getCurrentSession().contains(area))
	{
		if (buscarAreaPorCodigo(area.getCodigo()) != null)
		{
			throw new SevenLabsException("El Codigo ya Existe");
		}
	}
}



@Override
public void guardarArea(Area area) {
	hibernateDAO.saveOrUpdate(area);
}



@Override
public List buscarTiposDocumentosTodos() {
	// TODO Auto-generated method stub
	return hibernateDAO.loadAll(TipoDocumento.class);
}



@Override
public void verificarTipoDocumento(TipoDocumento tipoDocumento) {
	ValidacionUtils.validarTexto(tipoDocumento.getNombre(), "Debe Insertar un Nombre");
	//ValidacionUtils.validarTexto(tipoDocumento.getPatronCodigo(), "Debe Insertar un Patron de Codigo");
	if (tipoDocumento.getCampos().size() == 0)
	{
		throw new SevenLabsException("Debe Insertar al Menos un Campo");
	}
}



@Override
public void guardarTipoDocumento(TipoDocumento tipoDocumento) {
	hibernateDAO.saveOrUpdate(tipoDocumento);
}



@Override
public void verificarCampo(Campo campo) {
	ValidacionUtils.validarTexto(campo.getNombre(), "Debe Insertar un Nombre");
	ValidacionUtils.validarTexto(campo.getTipo(), "Debe Seleccionar el Tipo");
	if (campo.getTipo().contains("Caja"))
	{
		ValidacionUtils.validarTexto(campo.getTipoDato(), "Debe Seleccionar el Tipo de Dato");
		//ValidacionUtils.validarNumero(campo.getLongAncho(), "Debe Ingresar el Ancho");
		ValidacionUtils.validarNumero(campo.getCantMax(), "Debe Ingresar la Cantidad Maxima de Caracteres");
	}
	else if (campo.getTipo().contains("Area"))
	{
		//ValidacionUtils.validarNumero(campo.getLongAncho(), "Debe Ingresar el Ancho");
		ValidacionUtils.validarNumero(campo.getLongAlto(), "Debe Ingresar el Alto");
	}
	else if (campo.getTipo().contains("Opciones"))
	{
		if (campo.getOpciones().size() == 0)
		{
			throw new SevenLabsException("Debe Ingresar al Menos una Opcion");
		}
	}
}



@Override
public void verificarOpcion(Opcion opcion) {
	ValidacionUtils.validarTexto(opcion.getNombre(), "Debe Insertar un Nombre");
}



@Override
public List buscarGruposTodos() {
	return hibernateDAO.loadAll(Grupo.class);
}



@Override
public List buscarUsuariosPorApellidos(String texto) {
	return this.hibernateDAO.findByProperty(Usuario.class, Restrictions.ilike("apellidos", texto, MatchMode.ANYWHERE));
}



@Override
public List buscarUsuariosPorNombres(String texto) {
	return this.hibernateDAO.findByProperty(Usuario.class, Restrictions.ilike("nombres", texto, MatchMode.ANYWHERE));
}



@Override
public void verificarGrupo(Grupo grupo) {
	ValidacionUtils.validarTexto(grupo.getNombre(), "Debe Insertar un Nombre");
	ValidacionUtils.validarTexto(grupo.getDescripcion(), "Debe Insertar una Descripcion");
}



@Override
public void guardarGrupo(Grupo grupo) {
	hibernateDAO.saveOrUpdate(grupo);
}



@Override
public List buscarRutasAprobacionPorAreaTipo(Area area, Area subArea, TipoDocumento tipoDocumento) {
	if (area.getCodigo() != null && subArea.getCodigo() != null && tipoDocumento.getCodigo() != null)
	{
		String[] nombres = new String[3];
		nombres[0] = "area";
		nombres[1] = "subArea";
		nombres[2] = "tipo";
		
		Object[] valores = new Object[3];
		valores[0] = area;
		valores[1] = subArea;
		valores[2] = tipoDocumento;
		
		return this.hibernateDAO.findByNamedParam("from RutaAprobacion ra where ra.pk.area=:area and ra.pk.subArea=:subarea and ra.pk.tipoDocumento=:tipo", nombres, valores);
	}
	else if (area.getCodigo() != null && subArea.getCodigo() == null && tipoDocumento.getCodigo() != null)
	{
		String[] nombres = new String[2];
		nombres[0] = "area";
		nombres[1] = "tipo";
		
		Object[] valores = new Object[2];
		valores[0] = area;
		valores[1] = tipoDocumento;
		
		return this.hibernateDAO.findByNamedParam("from RutaAprobacion ra where ra.pk.area=:area and ra.pk.tipoDocumento=:tipo", nombres, valores);
	}
	else if (area.getCodigo() != null && subArea.getCodigo() != null && tipoDocumento.getCodigo() == null)
	{
		String[] nombres = new String[2];
		nombres[0] = "area";
		nombres[1] = "subArea";
		
		Object[] valores = new Object[2];
		valores[0] = area;
		valores[1] = subArea;
		
		return this.hibernateDAO.findByNamedParam("from RutaAprobacion ra where ra.pk.area=:area and ra.pk.subArea=:subarea", nombres, valores);
	}
		
	else if (area.getCodigo() != null)
	{
		String[] nombres = new String[1];
		nombres[0] = "area";
		
		Object[] valores = new Object[1];
		valores[0] = area;
		
		return this.hibernateDAO.findByNamedParam("from RutaAprobacion ra where ra.pk.area=:area", nombres, valores);
	}
	else if (tipoDocumento.getCodigo() != null)
	{
		String[] nombres = new String[1];
		nombres[0] = "tipo";
		
		Object[] valores = new Object[1];
		valores[0] = tipoDocumento;
		
		return this.hibernateDAO.findByNamedParam("from RutaAprobacion ra where ra.pk.tipoDocumento=:tipo", nombres, valores);
	}
	else
	{
		return buscarRutasAprobacionTodos();
	}
}



@Override
public List buscarRutasAprobacionTodos() {
	// TODO Auto-generated method stub
	return hibernateDAO.loadAll(RutaAprobacionSeguridad.class);
}



@Override
public void verificarRutaAprobacion(RutaAprobacionSeguridad rutaAprobacion) {
	ValidacionUtils.validarObjeto(rutaAprobacion.getPk().getTipoDocumento(), "Debe Seleccionar el Tipo de Documento");
	ValidacionUtils.validarObjeto(rutaAprobacion.getPk().getArea(), "Debe Seleccionar el Area");
	if (rutaAprobacion.getPk().getArea().getSubAreas().size() > 0)
	{
		ValidacionUtils.validarObjeto(rutaAprobacion.getPk().getSubArea(), "Debe Seleccionar la Sub Area");
	}
	ValidacionUtils.validarLista(rutaAprobacion.getAprobadores(), "Debe Seleccionar al Menos Un Aprobador");
}



@Override
public void guardarRutaAprobacion(RutaAprobacionSeguridad rutaAprobacion) {
	for (RutaAprobacionAprobador raa : rutaAprobacion.getAprobadores())
	{
		raa.setAreaid(rutaAprobacion.getPk().getArea().getCodigo());
		raa.setTipodocumentoid(rutaAprobacion.getPk().getTipoDocumento().getCodigo());
	}
	hibernateDAO.saveOrUpdate(rutaAprobacion);
}



@Override
public Grupo buscarGrupoGeneral() {
	List lista = hibernateDAO.findByProperty(Grupo.class, Restrictions.eq("nombre", "General"));
	if (lista.size() > 0)
	{
		return (Grupo) lista.get(0);
	}
	else
	{
		return null;
	}
}



@Override
public void verificarUsuario(Usuario usuario) {
	ValidacionUtils.validarTexto(usuario.getLogin(), "Debe Insertar un Login");
	ValidacionUtils.validarObjeto(usuario.getRolSistema(), "Debe Seleccionar un Rol");
	ValidacionUtils.validarTexto(usuario.getPassword(), "Debe Insertar un Password");
	ValidacionUtils.validarTexto(usuario.getNombres(), "Debe Insertar un Nombre");
	ValidacionUtils.validarTexto(usuario.getApellidos(), "Debe Insertar un Apellido");
	ValidacionUtils.validarTexto(usuario.getCorreo(), "Debe Insertar un Email");
	ValidacionUtils.validarEmail(usuario.getCorreo());
	ValidacionUtils.validarTexto(usuario.getEstatus(), "Debe Seleccionar un Estado");
}



@Override
public List buscarTiposDocumentosPrincipales() {
	return hibernateDAO.find("from TipoDocumento td where td.principal = true");
}



@Override
public TipoDocumento buscarTipoDocumentoPorCodigo(Integer codigo) {
	return (TipoDocumento) hibernateDAO.findByKey(TipoDocumento.class, codigo);
}



@Override
public void asignarValoresDocumento(Documento documento, Window ventana) {
	documento.getValores().clear();
	documento.getTipoDocumento().ordenarCamposDesc();
    
	for (org.modelo.dto.Campo cmp : documento.getTipoDocumento().getCampos())
	{
		org.zkoss.zk.ui.Component comp = ventana.getFellow(cmp.getCodigo().toString(), true);
		if (comp != null)
		{
			Valor valor = new Valor();
			valor.setCampo(cmp);
			valor.setDocumento(documento);
			
			if (cmp.getTipo().equals("Caja de Texto"))
			{
				if(cmp.getTipoDato().equals("Texto"))
				{	
					valor.setValor(((Textbox) comp).getValue());
				}
				else if(cmp.getTipoDato().equals("Numerico Enteros"))
				{
					valor.setValor(((Intbox) comp).getValue().toString());
				}
				else if(cmp.getTipoDato().equals("Numerico Decimales"))
				{
					valor.setValor(((Doublebox) comp).getValue().toString());
				}
			}
			else if (cmp.getTipo().contains("Area de Texto Simple"))
			{	
				valor.setValor(((Textbox) comp).getValue());
			}
			else if (cmp.getTipo().contains("Area de Texto Ampliada"))
			{	
				valor.setValor(((CKeditor) comp).getValue());
			}
			else if (cmp.getTipo().contains("Opciones Simples"))
			{	
				valor.setValor(((Radiogroup) comp).getSelectedItem() == null ? null :(String) ((Radiogroup) comp).getSelectedItem().getValue());				
			}
			else if (cmp.getTipo().contains("Opciones Multiples"))
			{	
				for(org.zkoss.zk.ui.Component compOps : comp.getChildren())
				{	
					Checkbox chk = (Checkbox) compOps;
					if (chk.isChecked())
					{
						ValorOpcion valorOpcion = new ValorOpcion();
						valorOpcion.setValor(valor);
						valorOpcion.setValorResul((String)chk.getValue());
						valor.getValoresOpciones().add(valorOpcion);
					}
				}
			}
			documento.getValores().add(valor);
		}
	}
}

@Override
public void verificarDocumento(Documento documento) {
	if (documento.getTipoDocumento().getPatronCodigo() == null)
	{
		ValidacionUtils.validarTexto(documento.getCodigo(), "Debe Insertar el Codigo");
	}
	else if (documento.getTipoDocumento().getPatronCodigo().equals(""))
	{
		ValidacionUtils.validarTexto(documento.getCodigo(), "Debe Insertar el Codigo");
	}
	ValidacionUtils.validarTexto(documento.getTitulo(), "Debe Insertar el Titulo");
	ValidacionUtils.validarObjeto(documento.getArea(), "Debe Seleccionar el Area");
	if (documento.getArea().getSubAreas().size() > 0)
	{
		ValidacionUtils.validarObjeto(documento.getSubArea(), "Debe Seleccionar el Sub Area");
	}
	
//	if (documento.getAprobadores().size() == 0)
//	{
//		throw new SevenLabsException("Debe Seleccionar Por lo Menos un Aprobador");
//	}
}



@Override
public void guardarDocumento(Documento documento) {
	hibernateDAO.saveOrUpdate(documento);
}



@Override
public List buscarEstadosNoVigenteDocumentosPorTipo(TipoDocumento tipoDocumento, Usuario usuario) {
	if (usuario == null)
	{
		return this.hibernateDAO.findByNamedParam("Select distinct(doc.estatus) from Documento doc where doc.tipoDocumento=:tipo and doc.estatus<>'Vigente'", "tipo", tipoDocumento);
	}
	else
	{
		String[] names = new String[2];
		names[0] = "tipo";
		names[1] = "usuario";
		
		Object[] values = new Object[2];
		values[0] = tipoDocumento;
		values[1] = usuario;
		
		return this.hibernateDAO.findByNamedParam("Select distinct(doc.estatus) from Documento doc where doc.tipoDocumento=:tipo and doc.estatus<>'Vigente' and doc.usuarioElaborador=:usuario", names, values);
	}
}



@Override
public List buscarAreasNoVigenteDocumentosPorTipo(TipoDocumento tipoDocumento, Usuario usuario) {
	if (usuario == null)
	{
		return this.hibernateDAO.findByNamedParam("Select distinct(doc.area.nombre) from Documento doc where doc.tipoDocumento=:tipo and doc.estatus<>'Vigente' ", "tipo", tipoDocumento);
	}
	else
	{
		String[] names = new String[2];
		names[0] = "tipo";
		names[1] = "usuario";
		
		Object[] values = new Object[2];
		values[0] = tipoDocumento;
		values[1] = usuario;
		
		return this.hibernateDAO.findByNamedParam("Select distinct(doc.area.nombre) from Documento doc where doc.tipoDocumento=:tipo and doc.estatus<>'Vigente' and doc.usuarioElaborador=:usuario", names, values);
	}
}

@Override
public List buscarDocumentosPorEstadoAreaTipo(String estado, String area, TipoDocumento tipoDocumento, Usuario usuario)
{
	if (usuario == null)
	{
		if (estado == null)
		{
			return this.hibernateDAO.findByNamedParam("from Documento doc where doc.tipoDocumento=:tipo and doc.area.nombre='"+ area +"'", "tipo", tipoDocumento);
		}
		else if (area == null)
		{
			return this.hibernateDAO.findByNamedParam("from Documento doc where doc.tipoDocumento=:tipo and doc.estatus='" + estado + "'", "tipo", tipoDocumento);
		}
		else
		{	
			return this.hibernateDAO.findByNamedParam("from Documento doc where doc.tipoDocumento=:tipo and doc.area.nombre='"+ area +"' and doc.estatus='" + estado + "'", "tipo", tipoDocumento);
		}
	}
	else
	{
		String[] names = new String[2];
		names[0] = "tipo";
		names[1] = "usuario";
		
		Object[] values = new Object[2];
		values[0] = tipoDocumento;
		values[1] = usuario;
		
		if (estado == null)
		{
			return this.hibernateDAO.findByNamedParam("from Documento doc where doc.tipoDocumento=:tipo and doc.area.nombre='"+ area +"'  and doc.usuarioElaborador=:usuario", names, values);
		}
		else if (area == null)
		{
			return this.hibernateDAO.findByNamedParam("from Documento doc where doc.tipoDocumento=:tipo and doc.estatus='" + estado + "'  and doc.usuarioElaborador=:usuario", names, values);
		}
		else
		{	
			return this.hibernateDAO.findByNamedParam("from Documento doc where doc.tipoDocumento=:tipo and doc.area.nombre='"+ area +"' and doc.estatus='" + estado + "'  and doc.usuarioElaborador=:usuario", names, values);
		}
	}
}



@Override
public RutaAprobacionSeguridad buscarAprobadoresPorTipoArea(TipoDocumento tipoDocumento, Area area, Area subArea) {
	String[] names = new String[3];
	names[0] = "tipo";
	names[1] = "area";
	names[2] = "sub";
	
	Object[] valores = new Object[3];
	valores[0] = tipoDocumento;
	valores[1] = area;
	valores[2] = subArea;
	
	List lista = this.hibernateDAO.findByNamedParam("from RutaAprobacionSeguridad ras where ras.pk.tipoDocumento=:tipo and ras.pk.area=:area and ras.pk.subArea=:sub", names, valores);
	if(lista.size() > 0)
	{
		return (RutaAprobacionSeguridad) lista.get(0);
	}
	else
	{
		return null;
	}
}



@Override
public List buscarProximoAprobadoresDocumentosEnRevisionPorTipo(TipoDocumento tipoDocumento) {
	return this.hibernateDAO.findByNamedParam("Select distinct(doc.usuarioActual) from Documento doc where doc.tipoDocumento=:tipo and doc.estatus = 'En Revision' ", "tipo", tipoDocumento);
}



@Override
public List buscarDocumentosPorProximoAprobadorTipo(Usuario usuario, TipoDocumento tipoDocumento) {
	String[] names = new String[2];
	names[0] = "tipo";
	names[1] = "usuario";
	
	Object[] values = new Object[2];
	values[0] = tipoDocumento;
	values[1] = usuario;
	return this.hibernateDAO.findByNamedParam("from Documento doc where doc.tipoDocumento=:tipo and doc.estatus = 'En Revision' and doc.usuarioActual=:usuario", names, values);
}



@Override
public void verificarDocumentoCompleto(Documento documento) {
	for (Campo campo : documento.getTipoDocumento().getCampos())
	{
		if (campo.getEstatus().equals("Activo") && campo.isObligatorio())
		{
			boolean encontro = false;
			Valor valorEnc = null;
			for(Valor valor : documento.getValores())
			{
				if (campo.equals(valor.getCampo()))
				{
					encontro = true;
					valorEnc = valor;
					break;
				}
			}
			
			if (!encontro)
			{
				throw new SevenLabsException("Debe Llenar o Seleccionar Todos los Campos Obligatorios");
			}
			else
			{
				if (valorEnc.getValor() == null && !valorEnc.getCampo().getTipo().equals("Opciones Multiples"))
				{
					throw new SevenLabsException("Debe Llenar o Seleccionar Todos los Campos Obligatorios");
				}
				else
				{
					if (valorEnc.getCampo().getTipo().equals("Opciones Multiples") && valorEnc.getValoresOpciones().size() == 0)
					{
						throw new SevenLabsException("Debe Llenar o Seleccionar Todos los Campos Obligatorios");
					}
					else if(valorEnc.getValor().trim().equals(""))
					{
						throw new SevenLabsException("Debe Llenar o Seleccionar Todos los Campos Obligatorios");
					}
				}
			}
		}
	}
}



@Override
public void enviarNotificacionDocumento(String mensaje, Documento documento) {
	RutaAprobacionSeguridad ras = buscarAprobadoresPorTipoArea(documento.getTipoDocumento(), documento.getArea(), documento.getSubArea());
	if (ras != null)
	{
		for (Grupo grp : ras.getGruposPermiso())
		{
			for (Usuario usu : grp.getUsuarios())
			{
				Notificacion not = new Notificacion();
				not.setFecha(Calendar.getInstance().getTime());
				not.setTexto(mensaje);
				not.setTipo(new Integer(1));
				not.setUsuarios(usu.getLogin());
				guardarNotificacion(not);
			}
		}
		
		for (Usuario usu : ras.getUsuariosSeguridad())
		{
			Notificacion not = new Notificacion();
			not.setFecha(Calendar.getInstance().getTime());
			not.setTexto(mensaje);
			not.setTipo(new Integer(1));
			not.setUsuarios(usu.getLogin());
			guardarNotificacion(not);
		}
	}
}


@Override
public void eliminarGrupo(Grupo grupo) {
	if (!grupo.getNombre().equals("General"))
	{
		if (grupo.getUsuarios().size() > 0)
		{
			throw new SevenLabsException("No Se Puede Eliminar el Grupo: " + grupo.getNombre() +" Ya Que Existen Usuarios en Dicho Grupo");
		}
		else
		{
			hibernateDAO.delete(grupo);
		}
	}
	else
	{
		throw new SevenLabsException("No Se Puede Eliminar el Grupo: General, del Sistema");
	}
}

@Override
public void eliminarRol(Rol rol) {
	if (!rol.getNombre().equals("Administrador"))
	{
		List lista = hibernateDAO.findByProperty(Usuario.class, Restrictions.eq("rolSistema", rol));
		if (lista.size() > 0)
		{
			throw new SevenLabsException("No Se Puede Eliminar el Rol: " + rol.getNombre() +" Ya Que Existen Usuarios con Dicho Rol");
		}
		else
		{
			Object[] vals = new Object[1];
			vals[0] = rol;
			hibernateDAO.executeUpdateBySQL("Delete from Permiso per where rol = ?", vals);
			hibernateDAO.delete(rol);
		}
	}
	else
	{
		throw new SevenLabsException("No Se Puede Eliminar el Rol: Administrador, del Sistema");
	}
}



@Override
public void eliminarTipoDocumento(TipoDocumento tipoDocumento) {
	if (!tipoDocumento.getCodigo().equals(new Integer(1)) && tipoDocumento.getCodigo().equals(new Integer(2)))
	{
		List lista = hibernateDAO.findByProperty(Documento.class, Restrictions.eq("tipoDocumento", tipoDocumento));
		if (lista.size() > 0)
		{
			throw new SevenLabsException("No Se Puede Eliminar el Tipo: " + tipoDocumento.getNombre() +" Ya Que Existen Documentos con Dicho Tipo");
		}
		else
		{
			Object[] vals = new Object[1];
			vals[0] = tipoDocumento;
			hibernateDAO.executeUpdateBySQL("Delete from RutaAprobacionAprobador raa where raa.rutaAprobacion.pk.tipoDocumento = ?", vals);
			hibernateDAO.executeUpdateBySQL("Delete from RutaAprobacionSeguridad ras where ras.pk.tipoDocumento = ?", vals);
			hibernateDAO.delete(tipoDocumento);
		}
	}
	else
	{
		throw new SevenLabsException("No Se Puede Eliminar el Tipo: " + tipoDocumento.getNombre());
	}
}



@Override
public void eliminarArea(Area area) {
	try
	{
		hibernateDAO.delete(area);
	}
	catch(Exception e)
	{
		throw new SevenLabsException("No Se Puede Eliminar el Area: " + area.getNombre() + " Ya que Esta Siendo Usado en Otros Registros");
	}
}



@Override
public void eliminarUsuario(Usuario usuario) {
	try
	{
		hibernateDAO.delete(usuario);
	}
	catch(Exception e)
	{
		throw new SevenLabsException("No Se Puede Eliminar el Usuario: " + usuario.getLogin() + " Ya que Esta Siendo Usado en Otros Registros");
	}
	
}



@Override
public void eliminarRutaAprobacionSeguridad(RutaAprobacionSeguridad rutaAprobacionSeguridad) {
	try
	{
		hibernateDAO.delete(rutaAprobacionSeguridad);
	}
	catch(Exception e)
	{
		throw new SevenLabsException("No Se Puede Eliminar el Documento con Area: "
				+ rutaAprobacionSeguridad.getPk().getArea().getNombre() + " y Tipo de Documento "
						+ rutaAprobacionSeguridad.getPk().getTipoDocumento().getNombre() + "; ya que Esta Siendo Usado en Otros Registros");
	}
}



@Override
public List buscarGlosariosTodos() {
	return hibernateDAO.loadAll(Glosario.class);
}



@Override
public void guardarGlosario(Glosario glosario) {
	hibernateDAO.saveOrUpdate(glosario);
}



@Override
public void eliminarGlosario(Glosario glosario) {
	try
	{
		hibernateDAO.delete(glosario);
	}
	catch(Exception e)
	{
		throw new SevenLabsException("No Se Puede Eliminar el Glosario con Nombre: "
				+ glosario.getNombre());
	}
}

public Glosario buscarGlosarioPorNombre(String texto)
{
	List listado = hibernateDAO.findByProperty(Glosario.class, Restrictions.eq("nombre", texto));
	return (Glosario) listado.get(0); 
}

@Override
public void verificarGlosario(Glosario glosario) {
	ValidacionUtils.validarTexto(glosario.getNombre(), "Debe Insertar el Nombre del Termino");
	ValidacionUtils.validarTexto(glosario.getDescripcion(), "Debe Insertar la Descripcion del Termino");
	if (buscarGlosarioPorNombre(glosario.getNombre()) != null)
	{
		throw new SevenLabsException("Ya Existe un Termino con el Mismo Nombre");
	}
}



@Override
public List buscarDocumentosPorTipoEstado(List<TipoDocumento> tipos, String estado) {
	// TODO Auto-generated method stub
	List documentos = new ArrayList<Documento>();
	List propies = new ArrayList();
	
	if (tipos.size() > 0)
	{
		for (TipoDocumento tdoc : tipos)
		{
			propies.clear();
			propies.add(Restrictions.eq("estatus", estado));
			propies.add(Restrictions.eq("tipoDocumento", tdoc));
			documentos.addAll(hibernateDAO.findByCriterions(Documento.class, propies));
		}
	}
	else
	{
		propies.add(Restrictions.eq("estatus", estado));
		documentos.addAll(hibernateDAO.findByCriterions(Documento.class, propies));
	}
	return documentos;
}



@Override
public void verificarTermino(Glosario glosario) {
	ValidacionUtils.validarTexto(glosario.getNombre(), "Debe Ingresar un Nombre del Termino");
	ValidacionUtils.validarTexto(glosario.getDescripcion(), "Debe Ingresar la Descripcion del Termino");
	ValidacionUtils.validarTexto(glosario.getEstatus(), "Debe Seleccionar el Estatus");
}



@Override
public void guardarTermino(Glosario glosario) {
	hibernateDAO.saveOrUpdate(glosario);
}



@Override
public List buscarTerminosPorNombre(String texto) {
	return hibernateDAO.findByProperty(Glosario.class, Restrictions.like("nombre", texto, MatchMode.ANYWHERE).ignoreCase(), Order.asc("nombre"));
}



@Override
public List buscarTodosTerminos() {
	List orders = new ArrayList();
	orders.add(Order.asc("nombre"));
	return hibernateDAO.findAll(Glosario.class, orders);
}

@Override
public List buscarTerminosPorNombreActivos(String texto) {
	List params = new ArrayList();
	params.add(Restrictions.like("nombre", texto, MatchMode.ANYWHERE).ignoreCase());
	params.add(Restrictions.eq("estatus", "Activo"));
	
	List orders = new ArrayList();
	orders.add(Order.asc("nombre"));
	
	return hibernateDAO.findByCriterions(Glosario.class, params, orders);
}



@Override
public List buscarTodosTerminosActivos() {
	List orders = new ArrayList();
	orders.add(Order.asc("nombre"));
	
	List params = new ArrayList();
	params.add(Restrictions.eq("estatus", "Activo"));
	
	return hibernateDAO.findByCriterions(Glosario.class, params, orders);
}



@Override
public void eliminarDocumento(Documento documento) {
	documento.getOcurrencias().clear();
	hibernateDAO.delete(documento);
}



@Override
public List buscarAreasVigenteDocumentosPorTipo(TipoDocumento tipoDocumento) {	
	return this.hibernateDAO.findByNamedParam("Select distinct(doc.area.nombre) from Documento doc where doc.tipoDocumento=:tipo and doc.estatus = 'Vigente'", "tipo", tipoDocumento);
}



@Override
public List buscarDocumentosVigentesPorAreaTipo(String area, TipoDocumento tipoDocumento) {
	return this.hibernateDAO.findByNamedParam("from Documento doc where doc.tipoDocumento=:tipo and doc.area.nombre='"+ area +"' and doc.estatus='Vigente'", "tipo", tipoDocumento);
}



@Override
public List buscarDocumentosVigentesPorAreaTipo(String area, TipoDocumento tipoDocumento, List exs) {
	if (area == null && tipoDocumento != null)
	{
		return hibernateDAO.findByNamedParam("from Documento doc where doc.tipoDocumento=:tipo and doc.estatus='Vigente'", "tipo", tipoDocumento);
	}
	else if (area != null && tipoDocumento == null)
	{
		List lista = new ArrayList();
		List<TipoDocumento> tipos = buscarTiposDocumentosTodos();
		
		for (TipoDocumento tip : tipos)
		{
			if (!exs.contains(tip.getCodigo()))
			{
				lista.addAll(this.hibernateDAO.findByNamedParam("from Documento doc where doc.tipoDocumento=:tipo and doc.area.nombre='"+ area +"' and doc.estatus='Vigente'", "tipo", tip));
			}
		}
		return lista;
	}
	else if (area != null && tipoDocumento != null)
	{
		return this.hibernateDAO.findByNamedParam("from Documento doc where doc.tipoDocumento=:tipo and doc.area.nombre='"+ area +"' and doc.estatus='Vigente'", "tipo", tipoDocumento);
	}
	else
	{
		return new ArrayList();
	}
	
}


}
