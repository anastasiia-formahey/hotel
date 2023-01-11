package com.anastasiia.web.command.client;

import com.anastasiia.dto.BookingDTO;
import com.anastasiia.entity.Booking;
import com.anastasiia.services.BookingService;
import com.anastasiia.utils.Status;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class PayForBookingCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        List<BookingDTO> bookingDTOList = (ArrayList) request.getSession().getAttribute("bookings");
        int id = Integer.parseInt(request.getParameter("booking"));
        BookingDTO bookingDTO =
                bookingDTOList.stream().filter(bookingDTO1 -> id == (bookingDTO1.getId()))
                        .findAny().orElse(new BookingDTO());
        Booking booking = bookingDTO.dtoToEntity();
        booking.setStatusOfBooking(Status.PAID);
        BookingService.updateStatus(booking);
        return new CommandResult(request.getHeader("referer"), true);
    }
}
