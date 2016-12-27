package org.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.criterion.Restrictions;
import org.sevenlabs.exception.SevenLabsException;
import org.sevenlabs.modelo.dao.IHibernateDAO;


public class ValidacionUtils
{
	private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
 
    
    public static void validarEmail(String email) {
 
        Pattern pattern = Pattern.compile(PATTERN_EMAIL);
 
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches())
        {
        	throw new SevenLabsException("Debe Insertar un Email Valido");
        }
 
    }
  public static void validarObjetoDuplicado(IHibernateDAO hibernateDAO, Class clase, Serializable valor, String mensaje)
  {
    Object objetoEncontrado = hibernateDAO.findByKey(clase, valor);
    if (objetoEncontrado != null)
    {
      throw new SevenLabsException(mensaje);
    }
  }
  
  public static void validarObjetoDuplicado(IHibernateDAO hibernateDAO, Class clase, Serializable valor, String atributo, String mensaje)
  {
    List rest = new ArrayList();
    rest.add(Restrictions.eq(atributo, valor));
    List objetos = hibernateDAO.findByCriterions(clase, rest);
    if (objetos.size() > 0)
    {
      throw new SevenLabsException(mensaje);
    }
  }
  
  public static void validarTexto(String texto, String mensaje)
  {
    if (texto == null)
    {
      throw new SevenLabsException(mensaje);
    }
    if (texto.equals(""))
    {
      throw new SevenLabsException(mensaje);
    }
  }
  
  public static void validarBoolean(boolean valor, String mensaje)
  {
    if (new Boolean(valor) == null)
    {
      throw new SevenLabsException(mensaje);
    }
  }
  
  public static void validarNumero(Double numero, String mensaje)
  {
    if (numero == null)
    {
      throw new SevenLabsException(mensaje);
    }
    if (numero.equals(new Double(0.0D)))
    {
      throw new SevenLabsException(mensaje);
    }
  }
  
  public static void validarNumero(Integer numero, String mensaje)
  {
    if (numero == null)
    {
      throw new SevenLabsException(mensaje);
    }
    if (numero.equals(new Integer(0)))
    {
      throw new SevenLabsException(mensaje);
    }
  }
  
  public static void validarLista(Set set, String mensaje)
  {
    if (set == null)
    {
      throw new SevenLabsException(mensaje);
    }
    if (set.size() == 0)
    {
      throw new SevenLabsException(mensaje);
    }
  }
  
  public static void validarLista(Set set, Integer numero, String mensaje)
  {
    if (set == null)
    {
      throw new SevenLabsException(mensaje);
    }
    if (set.size() != numero.intValue())
    {
      throw new SevenLabsException(mensaje);
    }
  }
  
  public static void validarObjeto(Object objeto, String mensaje)
  {
    if (objeto == null)
    {
      throw new SevenLabsException(mensaje);
    }
  }
  
  public static void validarFecha(Date fecha, String mensaje)
  {
    if (fecha == null)
    {
      throw new SevenLabsException(mensaje);
    }
  }
  
  public static void validarFechasMayorQue(Date fecha1, Date fecha2, String mensaje)
  {
    if (fecha1.after(fecha2))
    {
      throw new SevenLabsException(mensaje);
    }
  }
}
