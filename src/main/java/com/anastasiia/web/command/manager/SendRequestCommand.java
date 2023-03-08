package com.anastasiia.web.command.manager;

import com.anastasiia.dto.ApplicationDTO;
import com.anastasiia.dto.RequestDTO;
import com.anastasiia.exceptions.ServiceException;
import com.anastasiia.services.ApplicationService;
import com.anastasiia.services.RequestService;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Pages;
import com.anastasiia.utils.Status;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;
import com.anastasiia.web.context.AppContext;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SendRequestCommand implements Command {
    private static final Logger log = Logger.getLogger(SendRequestCommand.class);
    RequestService requestService;
    ApplicationService applicationService;

    public SendRequestCommand(AppContext appContext) {
        requestService = appContext.getRequestService();
        applicationService = appContext.getApplicationService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        log.debug("Method starts");
        RequestDTO requestDTO = (RequestDTO) request.getSession().getAttribute(JspAttributes.REQUEST_DTO);
        ApplicationDTO applicationDTO = (ApplicationDTO) request.getSession().getAttribute(JspAttributes.APP);
        requestDTO.setStatusOfRequest(Status.NOT_CONFIRMED);
        try {
            requestService.insertRequest(requestDTO, applicationDTO);
            request.getSession().setAttribute(JspAttributes.SUCCESS_MESSAGE, JspAttributes.REQUEST_SENT);
            request.getSession().setAttribute(JspAttributes.APPLICATION_COUNT, applicationService.applicationCountByStatus(Status.NEW));

        } catch (ServiceException e) {
            request.getSession().setAttribute(JspAttributes.EXCEPTION_MESSAGE, e.getMessage());
        }
         return new CommandResult(Pages.MANAGER_VIEW_APPLICATIONS_COMMAND, true);
    }
}
