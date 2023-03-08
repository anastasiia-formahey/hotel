package com.anastasiia.web.command.manager;

import com.anastasiia.dto.RoomDTO;
import com.anastasiia.exceptions.ValidationException;
import com.anastasiia.services.FeatureService;
import com.anastasiia.services.RoomService;
import com.anastasiia.services.Validation;
import com.anastasiia.utils.ClassOfRoom;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Pages;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;
import com.anastasiia.web.context.AppContext;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class EditRoomPageCommand implements Command {
    private static final Logger log = Logger.getLogger(EditRoomPageCommand.class);
    RoomService roomService;
    FeatureService featureService;

    public EditRoomPageCommand(AppContext appContext) {
        roomService = appContext.getRoomService();
        featureService = appContext.getFeatureService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        log.debug("Method starts");
        ArrayList<RoomDTO> listOfRooms = (ArrayList)request.getSession().getAttribute(JspAttributes.ROOMS);
        RoomDTO room = new RoomDTO();

        for (RoomDTO room1:listOfRooms) {
            try {
                if (room1.getId() == Validation.validIntField(request.getParameter(JspAttributes.NUMBER_OF_ROOM))){
                    room = room1;
                    room.setFeatures(featureService.getFeaturesForRoomEdit(roomService.dtoToEntity(room)));
                }
                request.getSession().setAttribute(JspAttributes.ROOM_EDIT, room);
                request.getSession().setAttribute(JspAttributes.CLASS_OF_ROOM, ClassOfRoom.values());
            } catch (ValidationException e) {
                log.error("ValidationException was caught. Cause : "+ e);
                request.setAttribute(JspAttributes.EXCEPTION_MESSAGE, e.getMessage());
            }
        }
        return new CommandResult(Pages.MANAGER_EDIT_ROOM, false);
    }
}
