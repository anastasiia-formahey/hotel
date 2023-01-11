package com.anastasiia.web.command.client;

import com.anastasiia.entity.Room;
import com.anastasiia.services.FeatureService;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Pages;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class BookRoomPageCommand implements Command {
    private static final Logger log = Logger.getLogger(BookRoomPageCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        ArrayList<Room> listOfRooms = (ArrayList)request.getSession().getAttribute(JspAttributes.ROOMS);
        Room room = new Room();
        for (Room room1:listOfRooms) {
            if (room1.getId() == Integer.parseInt(request.getParameter(JspAttributes.NUMBER_OF_ROOM))){
                room = room1;
            }
        }
        log.debug(room.toString());
        request.getSession().setAttribute(JspAttributes.ROOM, room);
        return new CommandResult(Pages.CLIENT_BOOK_ROOM,false);
    }
}
