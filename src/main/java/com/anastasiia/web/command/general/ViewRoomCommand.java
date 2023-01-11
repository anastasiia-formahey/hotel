package com.anastasiia.web.command.general;

import com.anastasiia.entity.Room;
import com.anastasiia.services.FeatureService;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Pages;
import com.anastasiia.utils.Role;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class ViewRoomCommand implements Command {

    private static final Logger log = Logger.getLogger(ViewRoomCommand.class);
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response){
        String page;
        Role roleInSession = (Role)request.getSession().getAttribute(JspAttributes.ROLE);
        ArrayList <Room> listOfRooms = (ArrayList)request.getSession().getAttribute(JspAttributes.ROOMS);

        Room room = new Room();
        for (Room room1:listOfRooms) {
            if (room1.getId() == Integer.parseInt(request.getParameter(JspAttributes.NUMBER_OF_ROOM))){
                room = room1;
                room.setFeatures(FeatureService.getListOfFeatures(room));
            }
        }
        switch (roleInSession){
            case CLIENT: {
                request.getSession().setAttribute(JspAttributes.ROOM, room);
                page = Pages.CLIENT_VIEW_ROOM;
                break;
            }
            case MANAGER:{
                request.getSession().setAttribute(JspAttributes.ROOM, room);
                page = Pages.MANAGER_VIEW_ROOM;
                break;
            }
            case UNREGISTERED:{
                request.getSession().setAttribute(JspAttributes.ROOM, room);
                page = Pages.VIEW_ROOM;
                break;
            }
            default:{
                page = Pages.INDEX;
            }
        }

        request.getSession().setAttribute(JspAttributes.ROOM, room);
        return new CommandResult(page, false);
    }
}
