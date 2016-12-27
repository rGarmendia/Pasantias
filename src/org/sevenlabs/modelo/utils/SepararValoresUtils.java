package org.sevenlabs.modelo.utils;

public class SepararValoresUtils
{
  public static String extraerCodigo(String telefono) {
    if (telefono.length() < 5)
    {
      return "";
    }
    

    return telefono.substring(3, 6);
  }
  

  public static String extraerNumeroTelefono(String telefono)
  {
    if (telefono.length() < 6)
    {
      return "";
    }
    

    return telefono.substring(7, telefono.length());
  }
  

  public static String extraerNacionalidad(String cedula)
  {
    return cedula.substring(0, 2);
  }
  
  public static String extraerNumeroCedula(String cedula)
  {
    return cedula.substring(2, cedula.length());
  }
}
