package com.anastasiia.web.filter;

import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Pages;
import com.anastasiia.utils.Role;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
@WebFilter("/*")
public class SecurityURIFilter implements Filter {

    private static final Logger log = Logger.getLogger(SecurityURIFilter.class);

    public void destroy() {
        log.debug("Filter destruction starts");
        // do nothing
        log.debug("Filter destruction finished");
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        log.debug("Filter starts");

        HttpServletRequest httpRequest = (HttpServletRequest)request;
        log.trace("Request uri --> " + httpRequest.getRequestURI());
        Role role = (Role)httpRequest.getSession().getAttribute(JspAttributes.ROLE);
        if(role == null){
            httpRequest.getSession().setAttribute(JspAttributes.ROLE, Role.UNREGISTERED);
        }
        if(httpRequest.getRequestURI().contains("/user")){
            log.trace("Request uri --> contains /user + role=> " + role);
            if(role != Role.UNREGISTERED){
                String errorMessages = "You do not have permission to access the user resource";
                request.setAttribute("error", errorMessages);

                log.trace("Set the request attribute: errorMessage --> " + errorMessages);
                httpRequest.getSession().invalidate();

                request.getRequestDispatcher(Pages.ERROR).forward(request, response);
            }
        }
        if(httpRequest.getRequestURI().contains("/manager")){
            log.trace("Request uri --> contains /manager + role=> " + role);
            if(role != Role.MANAGER){
                String errorMessages = "You do not have permission to access the manager resource";
                request.setAttribute("error", errorMessages);

                log.trace("Set the request attribute: errorMessage --> " + errorMessages);
                httpRequest.getSession().invalidate();

                request.getRequestDispatcher(Pages.ERROR).forward(request, response);
            }
        }
        if(httpRequest.getRequestURI().contains("/client")){
            log.trace("Request uri --> contains /client + role=> " + role);
            if(role != Role.CLIENT){
                String errorMessages = "You do not have permission to access the client resource";
                request.setAttribute("error", errorMessages);

                log.trace("Set the request attribute: errorMessage --> " + errorMessages);
                httpRequest.getSession().invalidate();

                request.getRequestDispatcher(Pages.ERROR).forward(request, response);
            }
        }

        log.debug("Filter finished");
        chain.doFilter(request, response);
    }

    public void init(FilterConfig fConfig) throws ServletException {
        log.debug("Filter initialization starts");

        log.debug("Filter initialization finished");
    }

}
