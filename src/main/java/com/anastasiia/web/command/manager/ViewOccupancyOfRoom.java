package com.anastasiia.web.command.manager;

import com.anastasiia.dto.RoomDTO;
import com.anastasiia.services.FeatureService;
import com.anastasiia.services.OccupancyOfRoomService;
import com.anastasiia.services.RoomService;
import com.anastasiia.utils.Status;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

public class ViewOccupancyOfRoom implements Command {
    RoomService roomService = new RoomService();
    FeatureService featureService = new FeatureService();
    OccupancyOfRoomService occupancyOfRoomService = new OccupancyOfRoomService();
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        int numberOfRoom = Integer.parseInt(request.getParameter("numberOfRoom"));
        Date date = Date.valueOf(request.getSession().getAttribute("dateOfOccupancy").toString());
        Status status = Status.valueOf(request.getParameter("status"));
        RoomDTO room = roomService.findRoomById(numberOfRoom);
        room.setFeatures(featureService.getListOfFeatures(numberOfRoom));
        request.setAttribute("room", room);
        request.setAttribute("occupancyOfRoom", occupancyOfRoomService.getOccupancyOfRoomByRoomId(numberOfRoom,date));
        return new CommandResult("/manager/viewOccupancyOfRoom.jsp", false);
    }
}
