package com.anastasiia.web.command.manager;

import com.anastasiia.services.FeatureService;
import com.anastasiia.services.RoomService;
import com.anastasiia.utils.*;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddRoomPageCommand implements Command {
    RoomService roomService = new RoomService();
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute(JspAttributes.CLASS_OF_ROOM, ClassOfRoom.values());
        request.getSession().setAttribute(JspAttributes.CAPACITY, roomService.getCapacityOfRoom());
        request.getSession().setAttribute(JspAttributes.FEATURES, FeatureService.getListOfFeatures());
        return new CommandResult(Pages.MANAGER_ADD_ROOM, false);
    }
}
