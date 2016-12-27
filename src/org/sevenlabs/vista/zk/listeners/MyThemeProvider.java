package org.sevenlabs.vista.zk.listeners;

import java.util.Properties;
import org.zkoss.zk.ui.Execution;

public class MyThemeProvider implements org.zkoss.zk.ui.util.ThemeProvider
{
  private String themeName;
  private String fileList;
  
  public String getThemeName()
  {
    return this.themeName;
  }
  
  public void setThemeName(String themeName) {
    this.themeName = themeName;
  }
  
  public MyThemeProvider() {
    try {
      java.io.InputStream is = getClass().getResourceAsStream(
        "/zkthemer.properties");
      
      if (is == null) {
        throw new RuntimeException("Cannot find zkthemer.properties");
      }
      Properties prop = new Properties();
      prop.load(is);
      this.themeName = ((String)prop.get("theme"));
      this.fileList = ((String)prop.get("fileList"));
      if (this.themeName == null) {
        throw new RuntimeException(
          "zkthemer.properties found, but missing 'theme' entry");
      }
      is.close();
    } catch (java.io.IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  public java.util.Collection getThemeURIs(Execution exec, java.util.List uris)
  {
    java.util.List newUris = new java.util.ArrayList(uris);
    for (Object object : newUris) {
      String uri = (String)object;
      if (uri.startsWith("~./")) {
        uri = "~./" + this.themeName + "/" + uri.substring(3);
      }
      uris.add(uri);
    }
    return uris;
  }
  
  public String beforeWCS(Execution exec, String uri) {
    return uri;
  }
  
  public String beforeWidgetCSS(Execution exec, String uri) {
    String fileName = uri.substring(uri.lastIndexOf("/") + 1);
    if (this.fileList.indexOf(fileName) >= 0)
      uri = 
        "~./" + this.themeName + "/" + uri.substring(3);
    return uri;
  }
  
  public int getWCSCacheControl(Execution exec, String uri) {
    return -1;
  }
}
