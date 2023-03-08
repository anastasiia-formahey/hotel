package com.anastasiia.web.command.manager;

import com.anastasiia.services.FeatureService;
import com.anastasiia.services.RoomService;
import com.anastasiia.utils.*;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;
import com.anastasiia.web.context.AppContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddRoomPageCommand implements Command {
    RoomService roomService;
    FeatureService featureService;

    public AddRoomPageCommand(AppContext appContext) {
        roomService = appContext.getRoomService();
        featureService = appContext.getFeatureService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute(JspAttributes.CLASS_OF_ROOM, ClassOfRoom.values());
        request.getSession().setAttribute(JspAttributes.FEATURES, featureService.getListOfFeatures());
        return new CommandResult(Pages.MANAGER_ADD_ROOM, false);
    }
}
