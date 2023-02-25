package com.anastasiia.web.command.manager;

import com.anastasiia.services.OccupancyOfRoomService;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Pages;
import com.anastasiia.utils.Status;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

public class CheckInRoom implements Command {
    private static final Logger log = Logger.getLogger(CheckInRoom.class);
    OccupancyOfRoomService occupancyOfRoomService = new OccupancyOfRoomService();
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        log.debug("Method starts");
        int roomId = Integer.parseInt(request.getParameter(JspAttributes.ROOM_ID));
        Date checkIn = Date.valueOf(request.getParameter(JspAttributes.CHECK_IN));
        Date checkOut = Date.valueOf(request.getParameter(JspAttributes.CHECK_OUT));

        occupancyOfRoomService.updateStatus(roomId, Status.BUSY, checkIn,checkOut);
        return new CommandResult(Pages.MANAGER_COMMAND_HOME, true);
    }
}
