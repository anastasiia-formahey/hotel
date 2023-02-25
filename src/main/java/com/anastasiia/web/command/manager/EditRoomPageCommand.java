package com.anastasiia.web.command.manager;

import com.anastasiia.dto.RoomDTO;
import com.anastasiia.services.FeatureService;
import com.anastasiia.services.RoomService;
import com.anastasiia.utils.ClassOfRoom;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Pages;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class EditRoomPageCommand implements Command {
    private static final Logger log = Logger.getLogger(EditRoomPageCommand.class);
    RoomService roomService = new RoomService();
    FeatureService featureService = new FeatureService();
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        log.debug("Method starts");
        ArrayList<RoomDTO> listOfRooms = (ArrayList)request.getSession().getAttribute(JspAttributes.ROOMS);
        RoomDTO room = new RoomDTO();
        for (RoomDTO room1:listOfRooms) {
            if (room1.getId() == Integer.parseInt(request.getParameter(JspAttributes.NUMBER_OF_ROOM))){
                room = room1;
                room.setFeatures(featureService.getFeaturesForRoomEdit(roomService.dtoToEntity(room)));
                log.debug(room.toString());
            }
        }

        request.getSession().setAttribute(JspAttributes.ROOM_EDIT, room);
        request.getSession().setAttribute(JspAttributes.CLASS_OF_ROOM, ClassOfRoom.values());
        request.getSession().setAttribute(JspAttributes.CAPACITY, roomService.getCapacityOfRoom());

        return new CommandResult(Pages.MANAGER_EDIT_ROOM, false);
    }
}
