package com.anastasiia.web.command.client;

import com.anastasiia.dto.BookingDTO;
import com.anastasiia.dto.RequestDTO;
import com.anastasiia.dto.UserDTO;
import com.anastasiia.services.BookingService;
import com.anastasiia.services.RequestService;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Pages;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GetRequestCommand implements Command {
    private static final Logger log = Logger.getLogger(GetRequestCommand.class);
    RequestService requestService = new RequestService();
    BookingService bookingService = new BookingService();
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        int applicationId = Integer.parseInt(request.getParameter("applicationId"));
        UserDTO userDTO = (UserDTO) request.getSession().getAttribute(JspAttributes.USER);
        RequestDTO requestDTO = requestService.getRequestByApplicationId(applicationId);
        List<BookingDTO> bookingDTOS = bookingService.getBookingFromRequest(requestDTO,userDTO);
        request.getSession().setAttribute("bookingDTOS", bookingDTOS);
        request.getSession().setAttribute("applicationId", applicationId);
        log.debug(bookingDTOS);
        return new CommandResult(Pages.CLIENT_VIEW_REQUEST, true);
    }
}
