package com.anastasiia.web.command.common;

import com.anastasiia.dto.ApplicationDTO;
import com.anastasiia.dto.BookingDTO;
import com.anastasiia.dto.RequestDTO;
import com.anastasiia.dto.UserDTO;
import com.anastasiia.services.ApplicationService;
import com.anastasiia.services.BookingService;
import com.anastasiia.services.RequestService;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Pages;
import com.anastasiia.utils.Role;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class GetRequestCommand implements Command {
    private static final Logger log = Logger.getLogger(GetRequestCommand.class);
    RequestService requestService = new RequestService();
    ApplicationService applicationService = new ApplicationService();
    BookingService bookingService = new BookingService();
    UserDTO userDTO;
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        int applicationId = Integer.parseInt(request.getParameter("applicationId"));
        Role role = (Role) request.getSession().getAttribute(JspAttributes.ROLE);
        RequestDTO requestDTO = requestService.getRequestByApplicationId(applicationId);
        ApplicationDTO applicationDTO = null;
        String page = "";
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
        request.getSession().setAttribute("bookingDTOS", bookingDTOS);
        request.setAttribute("application", applicationDTO);
        log.debug(bookingDTOS);
        return new CommandResult(page, false);
    }
}
