package com.anastasiia.web.command.manager;

import com.anastasiia.dto.RoomDTO;
import com.anastasiia.exceptions.ServiceException;
import com.anastasiia.services.impl.FeatureService;
import com.anastasiia.services.impl.RoomService;
import com.anastasiia.utils.ClassOfRoom;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Pages;
import com.anastasiia.utils.Status;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;
import com.anastasiia.web.context.AppContext;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditRoomCommand implements Command {
    private static final Logger log = Logger.getLogger(EditRoomCommand.class);
    RoomService roomService;
    FeatureService featureService;

    public EditRoomCommand(AppContext appContext) {
        roomService = appContext.getRoomService();
        featureService = appContext.getFeatureService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        log.debug("Method starts");
        try {
            RoomDTO room = new RoomDTO();
            RoomDTO roomEdit = (RoomDTO) request.getSession().getAttribute(JspAttributes.ROOM_EDIT);
            room.setNumberOfPerson(Integer.parseInt(request.getParameter(JspAttributes.NUMBER_OF_PERSON)));
            room.setPrice(Double.parseDouble(request.getParameter(JspAttributes.PRICE)));
            room.setClassOfRoom(ClassOfRoom.valueOf(request.getParameter(JspAttributes.CLASS_OF_ROOM)));
            String image = request.getParameter(JspAttributes.IMAGE);
            if(image.isEmpty() || image.equals(" ")){
                image = roomEdit.getImage();
            }
            room.setImage(image);
            room.setId(roomEdit.getId());
            room.setFeatures(featureService.getFeaturesForRoom(request.getParameterValues(JspAttributes.FEATURES)));
            room.setStatus(Status.valueOf(request.getParameter(JspAttributes.STATUS)));
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
