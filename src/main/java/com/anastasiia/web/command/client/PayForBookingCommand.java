package com.anastasiia.web.command.client;

import com.anastasiia.services.BookingService;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Status;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

public class PayForBookingCommand implements Command {
    private static final Logger log = Logger.getLogger(PayForBookingCommand.class);
    BookingService bookingService = new BookingService();
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        log.debug("Method starts");
        int bookingId = Integer.parseInt(request.getParameter(JspAttributes.BOOKING));
        Date checkIn = Date.valueOf(request.getParameter(JspAttributes.CHECK_IN));
        Date checkOut = Date.valueOf(request.getParameter(JspAttributes.CHECK_OUT));
        int roomId = Integer.parseInt(request.getParameter(JspAttributes.ROOM_ID));
        bookingService.updateStatus(bookingId, roomId, checkIn, checkOut,Status.PAID);
        return new CommandResult(request.getHeader("referer"), true);
    }
}
