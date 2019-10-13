package com.tyhgg.core.framework.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tyhgg.core.framework.util.ContextUtil;

@WebServlet(name = "InitialServlet", loadOnStartup = 1, urlPatterns = "/base/InitialServlet")
public class InitialServlet extends HttpServlet {

    private static final long serialVersionUID = -4355328207900171901L;

    @Override
    public void init() throws ServletException {
        ContextUtil.setApplicationContext(WebApplicationContextUtils
                .getWebApplicationContext(this.getServletContext()));
    }
}