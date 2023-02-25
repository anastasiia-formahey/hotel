package com.anastasiia.web.command.client;

import com.anastasiia.dto.BookingDTO;
import com.anastasiia.services.BookingService;
import com.anastasiia.services.RequestService;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Pages;
import com.anastasiia.utils.Status;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class BookRoomCommand implements Command {
    private static final Logger log = Logger.getLogger(BookRoomPageCommand.class);
    BookingService bookingService = new BookingService();
    RequestService requestService = new RequestService();

    List<BookingDTO> bookingDTOS = new ArrayList<>();
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        log.debug("Method starts");
        boolean isSuccess;
        boolean isConfirm;
         bookingDTOS = (List<BookingDTO>) request.getSession().getAttribute(JspAttributes.BOOKING_DTOS);
        for(BookingDTO bookingDTO: bookingDTOS){
            bookingDTO.setStatusOfBooking(Status.BOOKED);
        }
        isConfirm = request.getParameter(JspAttributes.CONFIRM) != null;
        isSuccess = bookingService.insertBooking(bookingDTOS, isConfirm);
        if(isSuccess) {
            if (request.getParameter(JspAttributes.APPLICATION_ID) != null) {
                int applicationId = Integer.parseInt(request.getParameter(JspAttributes.APPLICATION_ID));
                requestService.updateStatus(applicationId);
            }
            request.getSession().setAttribute(JspAttributes.IS_SUCCESS, true);
        }else {
            request.getSession().setAttribute(JspAttributes.IS_SUCCESS, false);
        }
        return new CommandResult(Pages.BOOK_ROOM, true);
    }
}
