package com.anastasiia.web.command.manager;

import com.anastasiia.services.OccupancyOfRoomService;
import com.anastasiia.utils.Pages;
import com.anastasiia.utils.Status;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

public class CheckInRoom implements Command {
    OccupancyOfRoomService occupancyOfRoomService = new OccupancyOfRoomService();
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        int roomId = Integer.parseInt(request.getParameter("roomId"));
        Date checkIn = Date.valueOf(request.getParameter("checkIn"));
        Date checkOut = Date.valueOf(request.getParameter("checkOut"));

        occupancyOfRoomService.updateStatus(roomId, Status.BUSY, checkIn,checkOut);
        return new CommandResult("/manager/?command=home", true);
    }
}
