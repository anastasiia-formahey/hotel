package com.anastasiia.web.command.client;

import com.anastasiia.dto.BookingDTO;
import com.anastasiia.services.BookingService;
import com.anastasiia.utils.Status;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class PayForBookingCommand implements Command {
    BookingService bookingService = new BookingService();
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        List<BookingDTO> bookingDTOList = (ArrayList) request.getSession().getAttribute("bookings");
        int bookingId = Integer.parseInt(request.getParameter("booking"));
        int roomId = Integer.parseInt(request.getParameter("roomId"));
        bookingService.updateStatus(bookingId, roomId, Status.PAID);
        return new CommandResult(request.getHeader("referer"), true);
    }
}
