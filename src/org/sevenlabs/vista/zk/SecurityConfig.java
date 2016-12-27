package org.sevenlabs.vista.zk;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;
import org.modelo.dto.Usuario;
import org.springframework.core.io.Resource;


public class SecurityConfig
{
  private Resource configurationFile;
  private Document document;
  private String guestLogin;
  private String guestPass;
  private Usuario guestUser;
  
  public Usuario getGuestUser()
  {
    return this.guestUser;
  }
  
  public Resource getConfigurationFile()
  {
    return this.configurationFile;
  }
  
  public void setConfigurationFile(Resource configurarioFile) {
    this.configurationFile = configurarioFile;
    try
    {
      SAXBuilder builder = new SAXBuilder();
      


      InputStream inputStream = this.configurationFile.getInputStream();
      
      this.document = builder.build(inputStream);
      
      XPath servletPath = XPath.newInstance("//guest-user");
      List components = servletPath.selectNodes(this.document);
      
      if ((components.size() > 0) && (components.get(0) != null) && 
        ((components.get(0) instanceof Element)))
      {
        Element element = (Element)components.get(0);
        
        this.guestLogin = element.getAttributeValue("login");
        this.guestPass = element.getAttributeValue("password");
        
        System.out.println("Login = " + this.guestLogin);
        System.out.println("Password = " + this.guestPass);
      }
      

    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    catch (JDOMException e) {
      e.printStackTrace();
    }
  }
}
