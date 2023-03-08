package com.anastasiia.web.command.common;

import com.anastasiia.dto.UserDTO;
import com.anastasiia.exceptions.InvalidUserException;
import com.anastasiia.exceptions.ValidationException;
import com.anastasiia.services.UserService;
import com.anastasiia.services.Validation;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Pages;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;
import com.anastasiia.web.context.AppContext;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginCommand implements Command {
    private static final Logger log = Logger.getLogger(LoginCommand.class);
    private final AppContext appContext;
    UserService userService;

    public LoginCommand(AppContext appContext) {
        this.appContext = appContext;
        userService = appContext.getUserService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        log.debug("Method starts");
        String email;
        String password;
        request.getSession().removeAttribute(JspAttributes.ERROR);
        request.getSession().removeAttribute(JspAttributes.EXCEPTION_MESSAGE);
        try {
            email = Validation.validEmail(request.getParameter(JspAttributes.EMAIL));
            password = Validation.validPassword(request.getParameter(JspAttributes.PASSWORD));
            UserDTO userDTO = userService.validateUserByEmailAndPassword(email, password);
            request.getSession().setAttribute(JspAttributes.USER, userDTO);
            request.getSession().setAttribute(JspAttributes.ROLE, userDTO.getRole());
            return new HomeCommand(appContext).execute(request, response);
        }catch (ValidationException e){
            log.error("ValidationException was caught. Cause : " + e.getMessage());
            request.getSession().setAttribute(JspAttributes.EXCEPTION_MESSAGE, e.getMessage());
            return new CommandResult(Pages.LOGIN_COMMAND, true);
        }catch (InvalidUserException e){
            log.error("InvalidUserException was caught. Cause : " + e.getMessage());
            request.getSession().setAttribute(JspAttributes.ERROR, true);
            return new CommandResult(Pages.LOGIN_COMMAND, true);
        }
    }
}
