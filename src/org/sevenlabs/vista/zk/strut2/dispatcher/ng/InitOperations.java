package org.sevenlabs.vista.zk.strut2.dispatcher.ng;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.util.ClassLoaderUtil;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.struts2.dispatcher.StaticContentLoader;
import org.apache.struts2.dispatcher.ng.HostConfig;










public class InitOperations
{
  public void initLogging(HostConfig filterConfig)
  {
    String factoryName = filterConfig.getInitParameter("loggerFactory");
    if (factoryName != null) {
      try {
        Class cls = ClassLoaderUtil.loadClass(factoryName, getClass());
        LoggerFactory fac = (LoggerFactory)cls.newInstance();
        LoggerFactory.setLoggerFactory(fac);
      } catch (InstantiationException e) {
        System.err.println("Unable to instantiate logger factory: " + factoryName + ", using default");
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        System.err.println("Unable to access logger factory: " + factoryName + ", using default");
        e.printStackTrace();
      } catch (ClassNotFoundException e) {
        System.err.println("Unable to locate logger factory class: " + factoryName + ", using default");
        e.printStackTrace();
      }
    }
  }
  


  public org.apache.struts2.dispatcher.Dispatcher initDispatcher(HostConfig filterConfig)
  {
    org.apache.struts2.dispatcher.Dispatcher dispatcher = createDispatcher(filterConfig);
    dispatcher.init();
    return dispatcher;
  }
  


  public StaticContentLoader initStaticContentLoader(HostConfig filterConfig, org.apache.struts2.dispatcher.Dispatcher dispatcher)
  {
    StaticContentLoader loader = (StaticContentLoader)dispatcher.getContainer().getInstance(StaticContentLoader.class);
    loader.setHostConfig(filterConfig);
    return loader;
  }
  



  public org.apache.struts2.dispatcher.Dispatcher findDispatcherOnThread()
  {
    org.apache.struts2.dispatcher.Dispatcher dispatcher = org.apache.struts2.dispatcher.Dispatcher.getInstance();
    if (dispatcher == null) {
      throw new IllegalStateException("Must have the StrutsPrepareFilter execute before this one");
    }
    return dispatcher;
  }
  


  private org.apache.struts2.dispatcher.Dispatcher createDispatcher(HostConfig filterConfig)
  {
    Map<String, String> params = new HashMap();
    for (Iterator e = filterConfig.getInitParameterNames(); e.hasNext();) {
      String name = (String)e.next();
      String value = filterConfig.getInitParameter(name);
      params.put(name, value);
    }
    return new org.sevenlabs.vista.zk.strut2.dispatcher.Dispatcher(filterConfig.getServletContext(), params);
  }
  
  public void cleanup() {
    ActionContext.setContext(null);
  }
}
