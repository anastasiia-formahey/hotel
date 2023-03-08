package com.anastasiia.web.command.common;

import com.anastasiia.entity.User;
import com.anastasiia.exceptions.ServiceException;
import com.anastasiia.exceptions.ValidationException;
import com.anastasiia.services.PasswordEncoder;
import com.anastasiia.services.UserService;
import com.anastasiia.dao.Fields;
import com.anastasiia.services.Validation;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Pages;
import com.anastasiia.utils.Role;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;
import com.anastasiia.web.context.AppContext;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignUpCommand implements Command {

    private static final Logger log = Logger.getLogger(SignUpCommand.class);
    UserService userService;

    public SignUpCommand(AppContext appContext) {
        userService = appContext.getUserService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        log.debug("Method starts");
        try {
            String email = Validation.validEmail(request.getParameter(Fields.USER_EMAIL));
            String password = Validation.validPassword(request.getParameter(JspAttributes.PASSWORD));
            User user = new User(
                    Validation.validField(request.getParameter(JspAttributes.FIRST_NAME_JSP)),
                    Validation.validField(request.getParameter(JspAttributes.LAST_NAME_JSP)),
                    email,
                    PasswordEncoder.encode(password),
                    Role.CLIENT
            );
                userService.validateUserByEmail(email);
                boolean flag = userService.insertUser(user);
                request.getSession().setAttribute(JspAttributes.IS_SUCCESS, flag);
                return new CommandResult(Pages.LOGIN_COMMAND, true);
        }catch (ValidationException e){
            log.error("ValidationException was caught. Cause : " + e);
            request.getSession().setAttribute(JspAttributes.EXCEPTION_MESSAGE, e.getMessage());
            return new CommandResult(Pages.SIGN_UP_COMMAND, true);
        }catch (ServiceException e){
            log.error("ServiceException was caught. Cause : " + e);
            request.getSession().setAttribute(JspAttributes.EXCEPTION_MESSAGE, e.getMessage());
            return new CommandResult(Pages.SIGN_UP_COMMAND, true);
        }catch (NullPointerException e){
            log.error("NullPointerException was caught. Cause : " + e);
            request.getSession().setAttribute(JspAttributes.EXCEPTION_MESSAGE, JspAttributes.FIELD_EXCEPTION);
            return new CommandResult(Pages.SIGN_UP_COMMAND, true);
        }
    }
}
