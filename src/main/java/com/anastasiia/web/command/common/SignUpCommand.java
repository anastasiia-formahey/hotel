package com.anastasiia.web.command.common;

import com.anastasiia.entity.User;
import com.anastasiia.services.PasswordEncoder;
import com.anastasiia.services.UserService;
import com.anastasiia.utils.Fields;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Pages;
import com.anastasiia.utils.Role;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignUpCommand implements Command {

    private static final Logger log = Logger.getLogger(SignUpCommand.class);
    UserService userService = new UserService();
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter(Fields.EMAIL);
        User user = new User(
                request.getParameter(JspAttributes.FIRST_NAME_JSP),
                request.getParameter(JspAttributes.LAST_NAME_JSP),
                request.getParameter(JspAttributes.EMAIL),
                PasswordEncoder.encode(request.getParameter(JspAttributes.PASSWORD)),
                Role.CLIENT
        );
        log.debug(user.toString());
        if(!userService.validateUserByEmail(email)){
            userService.insertUser(user);
            return new CommandResult(Pages.LOGIN, true);
        }else{
            request.getSession().setAttribute(JspAttributes.EMAIL_EXISTS, true);
            return new CommandResult(request.getHeader("referer"), true);
        }


    }
}
