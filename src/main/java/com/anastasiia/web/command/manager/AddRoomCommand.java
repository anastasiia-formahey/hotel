package com.anastasiia.web.command.manager;

import com.anastasiia.dto.RoomDTO;
import com.anastasiia.entity.Feature;
import com.anastasiia.exceptions.ServiceException;
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
import java.util.List;

public class AddRoomCommand implements Command {
    private static final Logger log = Logger.getLogger(AddRoomCommand.class);
    RoomService roomService;
    FeatureService featureService;

    public AddRoomCommand(AppContext appContext) {
        roomService = appContext.getRoomService();
        featureService = appContext.getFeatureService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        log.debug("Method starts");
        RoomDTO room = new RoomDTO();
        try {
               room.setNumberOfPerson(Validation.validIntField(request.getParameter(JspAttributes.NUMBER_OF_PERSON)));
               room.setPrice(Validation.validIntField(request.getParameter(JspAttributes.PRICE)));
               room.setClassOfRoom(ClassOfRoom.valueOf(request.getParameter(JspAttributes.CLASS_OF_ROOM)));
               room.setImage(request.getParameter(JspAttributes.IMAGE));

               List<Feature> features = featureService.getFeaturesForRoom(request.getParameterValues(JspAttributes.FEATURES));
               room.setId(roomService.insertRoom(room));
               featureService.insertRoomFeatures(room, features);
               request.getSession().setAttribute(JspAttributes.SUCCESS_MESSAGE, JspAttributes.ROOM_ADDED);
        } catch (ValidationException e) {
            log.error("ValidationException was caught. Cause : "+ e);
            request.getSession().setAttribute(JspAttributes.EXCEPTION_MESSAGE, e.getMessage());
        }catch (ServiceException e) {
            log.error("ServiceException was caught. Cause : "+ e);
            request.getSession().setAttribute(JspAttributes.EXCEPTION_MESSAGE, e.getMessage());
        }
        return new CommandResult(Pages.MANAGER_ROOMS_COMMAND, true);
    }
}
