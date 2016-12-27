package org.sevenlabs.vista.zk;

import org.modelo.dto.Usuario;




















public class Application
{
  private SecurityConfig securityConfig;
  
  public SecurityConfig getSecurityConfig()
  {
    return this.securityConfig;
  }
  
  public void setSecurityConfig(SecurityConfig securityConfig) {
    this.securityConfig = securityConfig;
  }
  
  public Usuario getGuestUser() {
    return this.securityConfig.getGuestUser();
  }
}
