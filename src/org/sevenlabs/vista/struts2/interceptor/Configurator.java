package org.sevenlabs.vista.struts2.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import org.modelo.dto.Usuario;
import org.sevenlabs.comunes.Contexto;
import org.sevenlabs.comunes.ContextoImpl;
import org.sevenlabs.vista.zk.Application;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;












@Configuration
@EnableAsync
@EnableScheduling
public class Configurator
  extends AbstractInterceptor
{
  private Application app;
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    HttpSession session = ServletActionContext.getRequest().getSession();
    
    Contexto contexto = (Contexto)session
      .getAttribute("CONTEXTO");
    
    if (contexto == null) {
      contexto = new ContextoImpl();
      
      Usuario usuario = this.app.getGuestUser();
      contexto.setUsuarioActual(usuario);
      
      session.setAttribute("CONTEXTO", contexto);
    }
    
    return invocation.invoke();
  }
  
  public Application getApp() {
    return this.app;
  }
  
  public void setApp(Application app) {
    this.app = app;
  }
}
