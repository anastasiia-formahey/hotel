package com.anastasiia.web.command.manager;

import com.anastasiia.dto.OccupancyOfRoomDTO;
import com.anastasiia.dto.RoomDTO;
import com.anastasiia.exceptions.ServiceException;
import com.anastasiia.services.*;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Pages;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;
import com.anastasiia.web.context.AppContext;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

public class ViewOccupancyOfRoom implements Command {
    private static final Logger log = Logger.getLogger(ViewOccupancyOfRoom.class);
    RoomService roomService;
    FeatureService featureService;
    BookingService bookingService;
    OccupancyOfRoomService occupancyOfRoomService;
    public ViewOccupancyOfRoom(AppContext appContext) {
        roomService = appContext.getRoomService();
        featureService = appContext.getFeatureService();
        bookingService = appContext.getBookingService();
        occupancyOfRoomService = appContext.getOccupancyOfRoomService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        log.debug("Method starts");
        RoomDTO room;
        OccupancyOfRoomDTO occupancyOfRoomDTO;
        try {
            int numberOfRoom = Validation.validIntField(request.getParameter(JspAttributes.NUMBER_OF_ROOM));
            Date date = Validation.validDate(request.getSession().getAttribute(JspAttributes.DATE_OF_OCCUPANCY).toString());

            room = roomService.findRoomById(numberOfRoom);
            room.setFeatures(featureService.getListOfFeatures(numberOfRoom));
            occupancyOfRoomDTO = occupancyOfRoomService.getOccupancyOfRoomByRoomId(numberOfRoom,date);
            request.setAttribute(JspAttributes.ROOM, room);
            request.setAttribute(JspAttributes.OCCUPANCY_OF_ROOM, occupancyOfRoomDTO);
            request.setAttribute(JspAttributes.CURRENT_DATE, bookingService.getCurrentDate().compareTo(occupancyOfRoomDTO.getCheckInDate()));
        } catch (ServiceException e) {
            log.error("ServiceException was caught. Cause : "+ e);
        }

        return new CommandResult(Pages.MANAGER_VIEW_OCCUPANCY_OF_ROOM_JSP, false);
    }
}
