package com.anastasiia.web.command.common;

import com.anastasiia.dto.RoomDTO;
import com.anastasiia.exceptions.ValidationException;
import com.anastasiia.services.impl.FeatureService;
import com.anastasiia.services.Validation;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Pages;
import com.anastasiia.utils.Role;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;
import com.anastasiia.web.context.AppContext;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class ViewRoomCommand implements Command {

    private static final Logger log = Logger.getLogger(ViewRoomCommand.class);
    FeatureService featureService;
    AppContext appContext;

    public ViewRoomCommand(AppContext appContext) {
        featureService = appContext.getFeatureService();
        this.appContext = AppContext.getAppContext();
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response){
        log.debug("Method starts");
        String page="";
        Role roleInSession = (Role)request.getSession().getAttribute(JspAttributes.ROLE);
        ArrayList <RoomDTO> listOfRooms = (ArrayList)request.getSession().getAttribute(JspAttributes.ROOMS);

        RoomDTO room = new RoomDTO();
        try {
            for (RoomDTO room1 : listOfRooms) {
                if (room1.getId() == Validation.validIntField(request.getParameter(JspAttributes.NUMBER_OF_ROOM))) {
                    room = room1;
                    room.setFeatures(featureService.getListOfFeatures(room.getId()));
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
        }catch (ValidationException e) {
            log.error("ValidationException was caught. Cause : " + e);
            request.getSession().setAttribute(JspAttributes.EXCEPTION_MESSAGE, e.getMessage());
            return new RoomPageCommand(appContext).execute(request, response);
        }
        return new CommandResult(page, false);
    }
}
