package com.anastasiia.web.command.general;

import com.anastasiia.dto.UserDTO;
import com.anastasiia.services.UserService;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Pages;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginCommand implements Command {
    private static final Logger log = Logger.getLogger(LoginCommand.class);
    UserService userService = new UserService();
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter(JspAttributes.EMAIL);
        String password = request.getParameter(JspAttributes.PASSWORD);
        if (userService.validateUserByEmailAndPassword(email, password)) {
            UserDTO userDTO = userService.getUser(email);
            request.getSession().setAttribute(JspAttributes.USER, userDTO);
            request.getSession().setAttribute(JspAttributes.ROLE, userDTO.getRole());
        return new HomeCommand().execute(request, response);
    }else {
            request.getSession().setAttribute(JspAttributes.ERROR, true);
            return new CommandResult(Pages.LOGIN, true);
        }
    }
}
