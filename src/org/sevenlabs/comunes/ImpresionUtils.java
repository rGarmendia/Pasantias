package org.sevenlabs.comunes;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.sevenlabs.modelo.Archivo;
import org.zkoss.zk.ui.WebApp;
import org.zkoss.zk.ui.http.SimpleSession;






public final class ImpresionUtils
{
  private static Integer generarID(SimpleSession session)
  {
    boolean existe = true;
    Integer id = Integer.valueOf(0);
    
    while (existe) {
      Random random = new Random();
      id = Integer.valueOf(random.nextInt());
      Integer verificador = (Integer)session.getAttribute(id.toString());
      
      if (verificador == null) {
        existe = false;
      }
    }
    return id;
  }
  
  private static Archivo construirArchivoPdf(String rutaArchivoReporte, List listaDeDatos, Map parametros, SimpleSession session) {
    Archivo archivo = new Archivo();
    try
    {
      InputStream ist = session.getWebApp().getResourceAsStream(rutaArchivoReporte);
      JRBeanCollectionDataSource fuenteDeDatos = new JRBeanCollectionDataSource(listaDeDatos);
      byte[] bytes = JasperRunManager.runReportToPdf(ist, parametros, fuenteDeDatos);
      archivo.setContenido(bytes);
      archivo.setTipo("application/pdf");
    }
    catch (Exception localException) {}
    

    return archivo;
  }
  
  public static Integer imprimir(List lista, Map parametros, IProcesadorReporte procesadorReporte, String rutaArchivoReporte, SimpleSession session)
  {
    try {
      Archivo archivo = null;
      

      archivo = construirArchivoPdf(rutaArchivoReporte, lista, parametros, session);
      Integer id = generarID(session);
      session.setAttribute(id.toString(), archivo);
      return id;


    }
    catch (Exception e)
    {

      System.out.println("aca" + e.getMessage());
      e.printStackTrace(); }
    return null;
  }
}
