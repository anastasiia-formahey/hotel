package com.anastasiia.web.command.client;

import com.anastasiia.dto.BookingDTO;
import com.anastasiia.exceptions.ServiceException;
import com.anastasiia.exceptions.ValidationException;
import com.anastasiia.services.impl.BookingService;
import com.anastasiia.services.impl.RequestService;
import com.anastasiia.services.Validation;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Pages;
import com.anastasiia.utils.Status;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;
import com.anastasiia.web.context.AppContext;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class BookRoomCommand implements Command {
    private static final Logger log = Logger.getLogger(BookRoomPageCommand.class);
    BookingService bookingService;
    RequestService requestService;

    List<BookingDTO> bookingDTOS = new ArrayList<>();

    public BookRoomCommand(AppContext appContext) {
        bookingService = appContext.getBookingService();
        requestService = appContext.getRequestService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        log.debug("Method starts");
        boolean isConfirm;
         bookingDTOS = (List<BookingDTO>) request.getSession().getAttribute(JspAttributes.BOOKING_DTOS);
        for(BookingDTO bookingDTO: bookingDTOS){
            bookingDTO.setStatusOfBooking(Status.BOOKED);
        }
        try{
            isConfirm = request.getParameter(JspAttributes.CONFIRM) != null;
            bookingService.insertBooking(bookingDTOS, isConfirm);
                if (request.getParameter(JspAttributes.APPLICATION_ID) != null) {
                    int applicationId = Validation.validIntField(request.getParameter(JspAttributes.APPLICATION_ID));
                        requestService.updateStatus(applicationId, Status.CONFIRMED);
                }
            request.getSession().setAttribute(JspAttributes.SUCCESS_MESSAGE, JspAttributes.BOOKING_ADDED);
        }catch (ValidationException e) {
            log.error("ValidationException was caught. Cause : " + e);
            request.getSession().setAttribute(JspAttributes.EXCEPTION_MESSAGE, e.getMessage());
        }catch (ServiceException e) {
            log.error("ServiceException was caught. Cause : " + e);
            request.getSession().setAttribute(JspAttributes.EXCEPTION_MESSAGE, e.getMessage());
        }
        return new CommandResult(Pages.BOOK_ROOM, true);
    }
}
