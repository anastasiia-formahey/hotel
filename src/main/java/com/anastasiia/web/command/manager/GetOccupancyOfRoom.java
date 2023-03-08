package com.anastasiia.web.command.manager;

import com.anastasiia.exceptions.ServiceException;
import com.anastasiia.services.RoomService;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Pages;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;
import com.anastasiia.web.context.AppContext;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

public class GetOccupancyOfRoom implements Command {
    private static final Logger log = Logger.getLogger(GetOccupancyOfRoom.class);
    RoomService roomService;

    public GetOccupancyOfRoom(AppContext appContext) {
        roomService = appContext.getRoomService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            Date dateOfOccupancy = Date.valueOf(request.getParameter(JspAttributes.DATE_OF_OCCUPANCY));
            request.getSession().setAttribute(JspAttributes.DATE_OF_OCCUPANCY, dateOfOccupancy);
            request.getSession().setAttribute(
                    JspAttributes.ROOM_MAP,
                    roomService.getRoomMap(dateOfOccupancy));
        } catch (ServiceException e) {
            log.error("ServiceException was caught. Cause : "+ e);
        }
        return new CommandResult(Pages.MANAGER_HOME, true);
    }
}
