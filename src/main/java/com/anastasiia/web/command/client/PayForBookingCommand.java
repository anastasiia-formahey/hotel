package com.anastasiia.web.command.client;

import com.anastasiia.exceptions.ServiceException;
import com.anastasiia.exceptions.ValidationException;
import com.anastasiia.services.impl.BookingService;
import com.anastasiia.services.Validation;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Status;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;
import com.anastasiia.web.context.AppContext;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

public class PayForBookingCommand implements Command {
    private static final Logger log = Logger.getLogger(PayForBookingCommand.class);
    BookingService bookingService;

    public PayForBookingCommand(AppContext appContext) {
        bookingService = appContext.getBookingService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        log.debug("Method starts");
        try {
            int bookingId = Validation.validIntField(request.getParameter(JspAttributes.BOOKING));
            Date checkIn = Validation.validDate(request.getParameter(JspAttributes.CHECK_IN));
            Date checkOut =  Validation.validDate(request.getParameter(JspAttributes.CHECK_OUT));
            int roomId = Validation.validIntField(request.getParameter(JspAttributes.ROOM_ID));

            bookingService.updateStatus(bookingId, roomId, checkIn, checkOut,Status.PAID);
        } catch (ValidationException e) {
            log.error("ValidationException was caught. Cause : "+ e);
        }catch (ServiceException e) {
            log.error("ServiceException was caught. Cause : "+ e);
        }
        return new CommandResult(request.getHeader("referer"), true);
    }
}
