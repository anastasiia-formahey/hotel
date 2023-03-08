package com.anastasiia.web.command.common;

import com.anastasiia.dto.ApplicationDTO;
import com.anastasiia.dto.BookingDTO;
import com.anastasiia.dto.RequestDTO;
import com.anastasiia.dto.UserDTO;
import com.anastasiia.exceptions.ServiceException;
import com.anastasiia.services.ApplicationService;
import com.anastasiia.services.BookingService;
import com.anastasiia.services.RequestService;
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
import java.util.ArrayList;
import java.util.List;

public class GetRequestCommand implements Command {
    private static final Logger log = Logger.getLogger(GetRequestCommand.class);
    RequestService requestService;
    ApplicationService applicationService;
    BookingService bookingService;
    UserDTO userDTO;

    public GetRequestCommand(AppContext appContext) {
        requestService = appContext.getRequestService();
        applicationService = appContext.getApplicationService();
        bookingService = appContext.getBookingService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        log.debug("Method starts");
        int applicationId;
        Role role = (Role) request.getSession().getAttribute(JspAttributes.ROLE);
        RequestDTO requestDTO;
        String page = "";
        try {
            applicationId = Validation.validIntField(request.getParameter(JspAttributes.APPLICATION_ID));
            requestDTO = requestService.getRequestByApplicationId(applicationId);
            ApplicationDTO applicationDTO = null;
            switch (role){
                case CLIENT:{
                    page = Pages.CLIENT_VIEW_REQUEST;
                    userDTO = (UserDTO) request.getSession().getAttribute(JspAttributes.USER);
                    applicationDTO = applicationService.get(
                            (ArrayList)request.getSession().getAttribute(JspAttributes.APPLICATIONS),
                            applicationId
                    );
                    break;
                }
                case MANAGER:{
                    page = Pages.MANAGER_VIEW_REQUEST;
                    applicationDTO = applicationService.get(
                            (ArrayList)request.getSession().getAttribute(JspAttributes.APPLICATIONS),
                            applicationId
                    );
                    userDTO = applicationDTO.getUserDTO();
                    break;
                }
            }

            List<BookingDTO> bookingDTOS = bookingService.getBookingFromRequest(requestDTO,userDTO);
            request.getSession().setAttribute(JspAttributes.BOOKING_DTOS, bookingDTOS);
            request.setAttribute(JspAttributes.APPLICATION, applicationDTO);
        } catch (ServiceException e) {
            log.error("ServiceException was caught. Cause : "+ e);
        }
        return new CommandResult(page, false);
    }
}
