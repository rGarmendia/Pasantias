package org.sevenlabs.vista.struts2.result;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Result;
import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import org.sevenlabs.modelo.Archivo;



public class ArchivoResult
  implements Result
{
  public void execute(ActionInvocation invocation)
    throws Exception
  {
    ModelDriven action = (ModelDriven)invocation.getAction();
    
    Object modelo = action.getModel();
    
    if ((modelo != null) && 
      ((modelo instanceof Archivo))) {
      Archivo archivo = (Archivo)modelo;
      verArchivo(archivo);
    }
  }
  



  private void verArchivo(Archivo archivo)
    throws IOException
  {
    HttpServletResponse response = ServletActionContext.getResponse();
    response.setContentType(archivo.getTipo());
    response.setContentLength(archivo.getContenido().length);
    response.setHeader("Content-Disposition", "attachment;filename=\"" + 
      archivo.getNombre() + "\"");
    response.getOutputStream().write(archivo.getContenido());
    response.getOutputStream().flush();
  }
}
