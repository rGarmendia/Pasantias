package org.sevenlabs.comunes;


public class ContenedorContexto
{
  private static ThreadLocal contextHolder = new ThreadLocal();
  
  public static void setContexto(Contexto context) {
    contextHolder.set(context);
  }
  
  public static Contexto getContexto() {
    if (contextHolder.get() == null) {
      contextHolder.set(new ContextoImpl());
    }
    
    return (Contexto)contextHolder.get();
  }
  
  public static void borrarContexto() {
    contextHolder.set(null);
  }
}
