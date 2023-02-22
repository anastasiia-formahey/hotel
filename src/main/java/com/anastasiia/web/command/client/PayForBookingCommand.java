package com.anastasiia.web.command.client;

import com.anastasiia.services.BookingService;
import com.anastasiia.utils.Status;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

public class PayForBookingCommand implements Command {
    BookingService bookingService = new BookingService();
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        int bookingId = Integer.parseInt(request.getParameter("booking"));
        Date checkIn = Date.valueOf(request.getParameter("checkIn"));
        Date checkOut = Date.valueOf(request.getParameter("checkOut"));
        int roomId = Integer.parseInt(request.getParameter("roomId"));
        bookingService.updateStatus(bookingId, roomId, checkIn, checkOut,Status.PAID);
        return new CommandResult(request.getHeader("referer"), true);
    }
}
