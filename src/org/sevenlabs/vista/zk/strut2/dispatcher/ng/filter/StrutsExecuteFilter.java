package org.sevenlabs.vista.zk.strut2.dispatcher.ng.filter;

import org.apache.struts2.RequestUtils;
import org.apache.struts2.StrutsStatics;
import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.dispatcher.ng.PrepareOperations;
import org.apache.struts2.dispatcher.ng.ExecuteOperations;
import org.apache.struts2.dispatcher.ng.filter.FilterHostConfig;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.sevenlabs.vista.zk.strut2.dispatcher.ng.InitOperations;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Executes the discovered request information.  This filter requires the {@link StrutsPrepareFilter} to have already
 * been executed in the current chain.
 */
public class StrutsExecuteFilter implements StrutsStatics, Filter {
    private PrepareOperations prepare;
    private ExecuteOperations execute;
	
    private String zkuri;    

    private FilterConfig filterConfig;

    public void init(FilterConfig filterConfig) throws ServletException {
		zkuri = filterConfig.getInitParameter("zk-uri");    	
        this.filterConfig = filterConfig;
    }

    protected synchronized void lazyInit() {
        if (execute == null) {
            InitOperations init = new InitOperations();
            Dispatcher dispatcher = init.findDispatcherOnThread();
            init.initStaticContentLoader(new FilterHostConfig(filterConfig), dispatcher);

            prepare = new PrepareOperations(filterConfig.getServletContext(), dispatcher);
            execute = new ExecuteOperations(filterConfig.getServletContext(), dispatcher);
        }

    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        // This is necessary since we need the dispatcher instance, which was created by the prepare filter
        lazyInit();

		String resourcePath = RequestUtils.getServletPath(request);
		if (resourcePath.indexOf(zkuri) != -1) {
			chain.doFilter(request, response);
		} else {
			ActionMapping mapping = prepare.findActionMapping(request,
					response);
			if (mapping == null) {
				boolean handled = execute.executeStaticResourceRequest(
						request, response);
				if (!handled) {
					chain.doFilter(request, response);
				}
			} else {
				execute.executeAction(request, response, mapping);
			}
		}
        
    }

    public void destroy() {
        prepare = null;
        execute = null;
        filterConfig = null;
    }
}