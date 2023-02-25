package com.anastasiia.web.command.manager;

import com.anastasiia.dto.RoomDTO;
import com.anastasiia.services.FeatureService;
import com.anastasiia.services.OccupancyOfRoomService;
import com.anastasiia.services.RoomService;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Pages;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

public class ViewOccupancyOfRoom implements Command {
    private static final Logger log = Logger.getLogger(ViewOccupancyOfRoom.class);
    RoomService roomService = new RoomService();
    FeatureService featureService = new FeatureService();
    OccupancyOfRoomService occupancyOfRoomService = new OccupancyOfRoomService();
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        log.debug("Method starts");
        int numberOfRoom = Integer.parseInt(request.getParameter(JspAttributes.NUMBER_OF_ROOM));
        Date date = Date.valueOf(request.getSession().getAttribute(JspAttributes.DATE_OF_OCCUPANCY).toString());
        RoomDTO room = roomService.findRoomById(numberOfRoom);
        room.setFeatures(featureService.getListOfFeatures(numberOfRoom));
        request.setAttribute(JspAttributes.ROOM, room);
        request.setAttribute(JspAttributes.OCCUPANCY_OF_ROOM, occupancyOfRoomService.getOccupancyOfRoomByRoomId(numberOfRoom,date));
        return new CommandResult(Pages.MANAGER_VIEW_OCCUPANCY_OF_ROOM_JSP, false);
    }
}
