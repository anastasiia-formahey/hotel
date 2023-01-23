package com.anastasiia.web.command.client;

import com.anastasiia.dto.BookingDTO;
import com.anastasiia.dto.RoomDTO;
import com.anastasiia.dto.UserDTO;
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
import java.util.ArrayList;
import java.util.List;

public class BookRoomPageCommand implements Command {
    private static final Logger log = Logger.getLogger(BookRoomPageCommand.class);
    BookingService bookingService = new BookingService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        List<BookingDTO> bookingDTOS = new ArrayList<>();
        ArrayList<RoomDTO> listOfRooms = (ArrayList)request.getSession().getAttribute(JspAttributes.ROOMS);
        RoomDTO room = new RoomDTO();
        for (RoomDTO room1:listOfRooms){
            if (room1.getId() == Integer.parseInt(request.getParameter(JspAttributes.NUMBER_OF_ROOM))){
                room = room1;
            }
        }
        BookingDTO bookingDTO = new BookingDTO();
        UserDTO userDTO = (UserDTO)  request.getSession().getAttribute(JspAttributes.USER);
        log.debug(request.getParameter(JspAttributes.CHECK_IN_DATE));
        log.debug(request.getParameter(JspAttributes.CHECK_OUT_DATE));
        log.debug(request.getParameter(JspAttributes.PRICE));
        bookingDTO.setRoom(room);
        bookingDTO.setUser(userDTO);
        bookingDTO.setCheckInDate(Date.valueOf(request.getSession().getAttribute("checkIn").toString()));
        bookingDTO.setCheckOutDate(Date.valueOf(request.getSession().getAttribute("checkOut").toString()));
        bookingDTO.setPrice(bookingService.getTotalCost(room.getPrice(), bookingDTO.getCheckInDate(), bookingDTO.getCheckOutDate()));
        bookingDTO.setDateOfBooking(bookingService.getCurrentDate());
        bookingDTO.setStatusOfBooking(Status.BOOKED);
        bookingDTO.setBookingExpirationDate();
        bookingDTOS.add(bookingDTO);
        log.debug(room.toString());
        request.getSession().setAttribute(JspAttributes.ROOM, room);
        request.getSession().setAttribute("bookingDTOS", bookingDTOS);
        return new CommandResult(Pages.CLIENT_BOOK_ROOM,false);
    }
}
