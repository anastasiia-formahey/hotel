package com.anastasiia.web;

import com.anastasiia.exceptions.ServiceException;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandContainer;
import com.anastasiia.web.command.CommandResult;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Main servlet controller
 * @author Anastasiia Formahei
 * */
@WebServlet(name = "Controller",
        urlPatterns = {"/locale/*", "/login/*", "/signUp/*", "/auth/*","/logout/", "/manager/", "/client/", "/user/"})
public class Controller extends HttpServlet{
    private static final Logger log = Logger.getLogger(Controller.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        process(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("Controller starts");

        String commandName = request.getParameter("command");
        log.trace("Request parameter: command => " + commandName);

        Command command = CommandContainer.get(commandName);
        log.trace("Obtained command => " + command);

        CommandResult commandResult = command.execute(request, response);

        log.trace("Forward address => " + commandResult.getPage());

        if(commandResult.getPage() != null){
            if(commandResult.isRedirect()){
                log.debug("Controller finished. Go to sendRedirect address " + request.getContextPath() + commandResult.getPage());
                response.sendRedirect(request.getContextPath() + commandResult.getPage());
            }else{
                log.debug("Controller finished. Go to forward address " + commandResult.getPage());
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(commandResult.getPage());
                requestDispatcher.forward(request, response);
                }
        }

    }
}
