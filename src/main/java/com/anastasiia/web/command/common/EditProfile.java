package com.anastasiia.web.command.common;

import com.anastasiia.dto.UserDTO;
import com.anastasiia.exceptions.ServiceException;
import com.anastasiia.services.impl.UserService;
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

public class EditProfile implements Command {
    private static final Logger log = Logger.getLogger(EditProfile.class);
    UserService userService;
    public EditProfile(AppContext appContext) {
        userService = appContext.getUserService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String page;
        UserDTO userDTO = (UserDTO) request.getSession().getAttribute(JspAttributes.USER);
        Role roleInSession = userDTO.getRole();
        switch (roleInSession){
            case CLIENT: {
                page = Pages.CLIENT_HOME_COMMAND;
                break;
            }
            case MANAGER: {
                page = Pages.MANAGER_COMMAND_HOME;
                break;
            }
            default: page = Pages.INDEX;
        }
        try{
            String firstName = Validation.validField(request.getParameter(JspAttributes.FIRST_NAME_JSP));
            String lastName = Validation.validField(request.getParameter(JspAttributes.LAST_NAME_JSP));
            String email = Validation.validEmail(request.getParameter(JspAttributes.EMAIL));
            userService.editUserProfile(firstName, lastName, email, userDTO);
            request.getSession().setAttribute(JspAttributes.SUCCESS_MESSAGE, JspAttributes.EDIT_PROFILE_SUCCESS);
            request.getSession().setAttribute("showMessage", true);
        } catch (ServiceException e) {
            request.getSession().setAttribute("showMessage", true);
            log.error("ServiceException was caught. Cause : " + e);
            request.getSession().setAttribute(JspAttributes.EXCEPTION_MESSAGE, e.getMessage());
        }
        return new CommandResult(page, true);
    }
}
