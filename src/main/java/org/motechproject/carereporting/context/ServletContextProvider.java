package org.motechproject.carereporting.context;

import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

public class ServletContextProvider implements ServletContextAware {

    private ServletContext servletContext;

    public ServletContext getServletContext() {
        return servletContext;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
