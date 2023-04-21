package com.anastasiia.web.command.client;

import com.anastasiia.dto.ApplicationDTO;
import com.anastasiia.dto.UserDTO;
import com.anastasiia.exceptions.ServiceException;
import com.anastasiia.exceptions.ValidationException;
import com.anastasiia.services.impl.ApplicationService;
import com.anastasiia.services.Validation;
import com.anastasiia.utils.ClassOfRoom;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Pages;
import com.anastasiia.utils.Status;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;
import com.anastasiia.web.context.AppContext;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddApplicationCommand implements Command {
    private static final Logger log = Logger.getLogger(AddApplicationCommand.class);
    ApplicationService applicationService;

    public AddApplicationCommand(AppContext appContext) {
        applicationService = appContext.getApplicationService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response){
        log.debug("Method starts");
        try {
            ApplicationDTO applicationDTO = new ApplicationDTO();
            applicationDTO.setUserDTO((UserDTO)(request.getSession().getAttribute(JspAttributes.USER)));
            applicationDTO.setNumberOfGuests(
                    Validation.validIntField(request.getParameter(JspAttributes.NUMBER_OF_PERSON)));
            applicationDTO.setClassOfRoom(ClassOfRoom.valueOf(request.getParameter(JspAttributes.CLASS_OF_ROOM)));
            applicationDTO.setLengthOfStay(
                    Validation.validIntField(request.getParameter(JspAttributes.LENGTH_OF_STAY)));
            applicationDTO.setStatus(Status.NEW);
            applicationDTO.setComment(Validation.validField(request.getParameter(JspAttributes.COMMENT)));

            applicationService.insertApplication(applicationDTO);
            request.getSession().setAttribute(JspAttributes.SUCCESS_MESSAGE, JspAttributes.APPLICATION_ADDED);
        } catch (ValidationException e) {
            log.error("ValidationException was caught. Cause : " + e);
            request.getSession().setAttribute(JspAttributes.EXCEPTION_MESSAGE, e.getMessage());
        }catch (ServiceException e) {
            log.error("ServiceException was caught. Cause : " + e);
            request.getSession().setAttribute(JspAttributes.EXCEPTION_MESSAGE, e.getMessage());
        }
        return new CommandResult(Pages.CLIENT_VIEW_APPLICATIONS_COMMAND,true );
    }
}
