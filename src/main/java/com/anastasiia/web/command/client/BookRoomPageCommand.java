package com.anastasiia.web.command.client;

import com.anastasiia.dto.BookingDTO;
import com.anastasiia.dto.RoomDTO;
import com.anastasiia.dto.UserDTO;
import com.anastasiia.exceptions.ServiceException;
import com.anastasiia.exceptions.ValidationException;
import com.anastasiia.services.BookingService;
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
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class BookRoomPageCommand implements Command {
    private static final Logger log = Logger.getLogger(BookRoomPageCommand.class);
    BookingService bookingService;

    public BookRoomPageCommand(AppContext appContext) {
        bookingService = appContext.getBookingService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        log.debug("Method starts");
        List<BookingDTO> bookingDTOS = new ArrayList<>();
        ArrayList<RoomDTO> listOfRooms = (ArrayList)request.getSession().getAttribute(JspAttributes.ROOMS);
        RoomDTO room = new RoomDTO();
        BookingDTO bookingDTO = new BookingDTO();
        try{
            for (RoomDTO room1:listOfRooms){
                if (room1.getId() == Validation.validIntField(request.getParameter(JspAttributes.NUMBER_OF_ROOM))){
                    room = room1;}
            }

            UserDTO userDTO = (UserDTO)  request.getSession().getAttribute(JspAttributes.USER);
            bookingDTO.setRoom(room);
            bookingDTO.setUser(userDTO);
            bookingDTO.setCheckInDate(
                    Validation.validDate(request.getSession().getAttribute(JspAttributes.CHECK_IN).toString()));
            bookingDTO.setCheckOutDate(
                    Validation.validDate(request.getSession().getAttribute(JspAttributes.CHECK_OUT).toString()));
            Validation.validField(bookingDTO.getCheckInDate(), bookingDTO.getCheckOutDate());
            bookingDTO.setPrice(bookingService.getTotalCost(room.getPrice(), bookingDTO.getCheckInDate(), bookingDTO.getCheckOutDate()));
            bookingDTO.setDateOfBooking(bookingService.getCurrentDate());
            bookingDTO.setStatusOfBooking(Status.BOOKED);
            bookingDTO.setBookingExpirationDate();
            bookingDTOS.add(bookingDTO);

            request.getSession().setAttribute(JspAttributes.ROOM, room);
            request.getSession().setAttribute(JspAttributes.BOOKING_DTOS, bookingDTOS);
        }catch (ValidationException e) {
            log.error("ValidationException was caught. Cause : " + e);
            request.getSession().setAttribute(JspAttributes.EXCEPTION_MESSAGE, e.getMessage());
            return new CommandResult(Pages.CLIENT_ROOMS_COMMAND,true);
        }
        return new CommandResult(Pages.CLIENT_BOOK_ROOM,false);
    }
}
