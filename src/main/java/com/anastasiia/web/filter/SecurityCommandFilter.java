package com.anastasiia.web.filter;

import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Pages;
import com.anastasiia.utils.Role;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * SecurityCommandFilter checks by Role of user permission to access the command
 */
public class SecurityCommandFilter implements Filter {
    private static final Logger log = Logger.getLogger(SecurityCommandFilter.class);

    private static final Map<Role, List<String>> accessMap = new HashMap<>();
    private static List<String> common = new ArrayList<>();
    private static List<String> outOfControl = new ArrayList<>();
    @Override
    public void init(FilterConfig filterConfig){
        log.debug("Filter initialization starts");

        accessMap.put(Role.MANAGER, asList(filterConfig.getInitParameter("manager")));
        accessMap.put(Role.CLIENT, asList(filterConfig.getInitParameter("client")));
        accessMap.put(Role.UNREGISTERED, asList(filterConfig.getInitParameter("unregistered")));
        log.debug("Access map " + accessMap);

        common = asList(filterConfig.getInitParameter("common"));
        log.debug("Common " + common);

        outOfControl = asList(filterConfig.getInitParameter("outOfControl"));
        log.debug("outOfControl " + outOfControl);
        log.debug("Filter initialization finished");
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        log.debug("Filter starts");
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpSession session = httpRequest.getSession(false);
        if(accessCommandAllowed(servletRequest)){
            log.debug("Filter finished");
            filterChain.doFilter(servletRequest, servletResponse);
        }else{
            String errorMessages = "You do not have permission to access the requested resource";
            servletRequest.setAttribute("error", errorMessages);

            log.trace("Set the request attribute: errorMessage --> " + errorMessages);
            session.invalidate();

            servletRequest.getRequestDispatcher(Pages.ERROR).forward(servletRequest, servletResponse);
        }

    }

    private boolean accessCommandAllowed(ServletRequest request){
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String commandName = request.getParameter("command");

        if(outOfControl.contains(commandName)){
            return true;
        }
        HttpSession session = httpRequest.getSession(false);
        if (session == null){
            return false;
        }
        Role userRole = (Role) session.getAttribute(JspAttributes.ROLE);
        if(userRole == null){
            return false;
        }

        return accessMap.get(userRole).contains(commandName) || common.contains(commandName);
    }

    @Override
    public void destroy() {
        log.debug("Filter destruction starts");
        // do nothing
        log.debug("Filter destruction finished");
    }
    private List<String> asList(String param) {
        List<String> list = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(param);
        while (st.hasMoreTokens()) {
            list.add(st.nextToken());
        }
        return list;
    }
}
