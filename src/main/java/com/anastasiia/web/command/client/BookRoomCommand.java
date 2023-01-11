package com.anastasiia.web.command.client;

import com.anastasiia.dto.UserDTO;
import com.anastasiia.entity.Booking;
import com.anastasiia.entity.User;
import com.anastasiia.services.BookingService;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Pages;
import com.anastasiia.utils.Status;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

public class BookRoomCommand implements Command {
    private static final Logger log = Logger.getLogger(BookRoomPageCommand.class);
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Booking booking = new Booking();
        UserDTO userDTO = (UserDTO)  request.getSession().getAttribute(JspAttributes.USER);
        log.debug(request.getParameter(JspAttributes.CHECK_IN_DATE));
        log.debug(request.getParameter(JspAttributes.CHECK_OUT_DATE));
        log.debug(request.getParameter(JspAttributes.PRICE));
        booking.setRoomId(Integer.parseInt(request.getParameter(JspAttributes.ROOM_ID)));
        booking.setClientId(userDTO.getId());
        booking.setCheckInDate(Date.valueOf(request.getParameter(JspAttributes.CHECK_IN_DATE)));
        booking.setCheckOutDate(Date.valueOf(request.getParameter(JspAttributes.CHECK_OUT_DATE)));
        booking.setPrice(Double.parseDouble(request.getParameter(JspAttributes.PRICE)));
        booking.setDateOfBooking(BookingService.getCurrentDate());
        booking.setStatusOfBooking(Status.BOOKED);
        booking.setBookingExpirationDate();
        BookingService.insertBooking(booking);
        BookingService.withDrawnBooking(booking);
        return new CommandResult(Pages.BOOK_ROOM, true);
    }
}
