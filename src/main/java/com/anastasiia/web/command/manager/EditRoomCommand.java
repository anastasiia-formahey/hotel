package com.anastasiia.web.command.manager;

import com.anastasiia.dto.RoomDTO;
import com.anastasiia.exceptions.ServiceException;
import com.anastasiia.services.FeatureService;
import com.anastasiia.services.RoomService;
import com.anastasiia.utils.ClassOfRoom;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Pages;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;
import com.anastasiia.web.context.AppContext;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditRoomCommand implements Command {
    private static final Logger log = Logger.getLogger(EditRoomCommand.class);
    RoomService roomService;

    public EditRoomCommand(AppContext appContext) {
        roomService = appContext.getRoomService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        log.debug("Method starts");
        try {
            RoomDTO room = new RoomDTO();
            RoomDTO roomEdit = (RoomDTO) request.getSession().getAttribute("roomEdit");
            room.setNumberOfPerson(Integer.parseInt(request.getParameter("numberOfPerson")));
            room.setPrice(Double.parseDouble(request.getParameter("price")));
            room.setClassOfRoom(ClassOfRoom.valueOf(request.getParameter("classOfRoom")));
            String image = request.getParameter("image");
            if(image.isEmpty() || image.equals(" ")){
                image = roomEdit.getImage();
            }
            room.setImage(image);
            room.setId(roomEdit.getId());
            room.setFeatures(new FeatureService().getFeaturesForRoom(request));
            roomService.editRoom(room);
            request.getSession().setAttribute(JspAttributes.SUCCESS_MESSAGE, JspAttributes.ROOM_EDIT_SUCCESS);
        } catch (ServiceException e) {
            log.error("ServiceException was caught. Cause : "+ e);
            request.getSession().setAttribute(JspAttributes.EXCEPTION_MESSAGE, e.getMessage());
            return new CommandResult(Pages.MANAGER_EDIT_ROOM_COMMAND, true);
        }catch (NullPointerException e){
            request.getSession().setAttribute(JspAttributes.EXCEPTION_MESSAGE, JspAttributes.FIELD_EXCEPTION);
            return new CommandResult(Pages.MANAGER_EDIT_ROOM_COMMAND, true);
        }
        return new CommandResult(Pages.MANAGER_ROOMS_COMMAND, true);
    }
}
