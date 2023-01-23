package com.anastasiia.web.command.client;

import com.anastasiia.dto.BookingDTO;
import com.anastasiia.services.ApplicationService;
import com.anastasiia.services.BookingService;
import com.anastasiia.services.RequestService;
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
    ApplicationService applicationService = new ApplicationService();

    List<BookingDTO> bookingDTOS = new ArrayList<>();
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
         bookingDTOS = (List<BookingDTO>) request.getSession().getAttribute("bookingDTOS");
        for(BookingDTO bookingDTO: bookingDTOS){
            bookingDTO.setStatusOfBooking(Status.BOOKED);
        }
        bookingService.insertBooking(bookingDTOS);
        if(request.getParameter("applicationId") != null){
            int applicationId = Integer.parseInt(request.getParameter("applicationId"));
            requestService.updateStatus(applicationId);
        }
        return new CommandResult(Pages.BOOK_ROOM, true);
    }
}
