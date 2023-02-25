package com.anastasiia.web.command.manager;

import com.anastasiia.services.RoomService;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Pages;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

public class GetOccupancyOfRoom implements Command {
    RoomService roomService = new RoomService();
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Date dateOfOccupancy = Date.valueOf(request.getParameter(JspAttributes.DATE_OF_OCCUPANCY));
        request.getSession().setAttribute(JspAttributes.DATE_OF_OCCUPANCY, dateOfOccupancy);
        request.getSession().setAttribute(
                JspAttributes.ROOM_MAP,
                roomService.getRoomMap(dateOfOccupancy));
        return new CommandResult(Pages.MANAGER_HOME, true);
    }
}
