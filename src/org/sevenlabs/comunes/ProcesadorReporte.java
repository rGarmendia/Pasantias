package org.sevenlabs.comunes;

import javax.sql.DataSource;

















public class ProcesadorReporte
  implements IProcesadorReporte
{
  private DataSource dataSource;
  private String servletReporte;
  
  public String getURIReporte()
  {
    return getServletReporte();
  }
  
  public String getServletReporte()
  {
    return this.servletReporte;
  }
  
  public void setServletReporte(String servletReporte)
  {
    this.servletReporte = servletReporte;
  }
  
  public DataSource getDataSource()
  {
    return this.dataSource;
  }
  
  public void setDataSource(DataSource dataSource)
  {
    this.dataSource = dataSource;
  }
}
