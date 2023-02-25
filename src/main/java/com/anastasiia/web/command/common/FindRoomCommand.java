package com.anastasiia.web.command.common;

import com.anastasiia.dto.UserDTO;
import com.anastasiia.services.RoomService;
import com.anastasiia.utils.ClassOfRoom;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Pages;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FindRoomCommand implements Command {
    RoomService roomService = new RoomService();
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String page = "";
        UserDTO userDTO = (UserDTO) request.getSession().getAttribute(JspAttributes.USER);
        request.setAttribute(JspAttributes.IS_BOOKING, true);
        request.getSession().setAttribute(JspAttributes.NUMBER_OF_PERSON, request.getParameter(JspAttributes.NUMBER_OF_PERSON));
        request.getSession().setAttribute(JspAttributes.CHECK_IN, request.getParameter(JspAttributes.CHECK_IN_DATE));
        request.getSession().setAttribute(JspAttributes.CHECK_OUT, request.getParameter(JspAttributes.CHECK_OUT_DATE));
        request.getSession().setAttribute(JspAttributes.CLASS_OF_ROOM, ClassOfRoom.values());
        request.getSession().setAttribute(JspAttributes.CAPACITY, roomService.getCapacityOfRoom());
        switch (userDTO.getRole()){
            case CLIENT: {
                page = Pages.CLIENT_ROOMS_FOR_BOOKING;
                request.getSession().setAttribute(JspAttributes.ROOMS, roomService.findRoomForBooking(request));
                break;
            }
            case MANAGER:{
                page = Pages.MANAGER_REVIEW_APPLICATIONS;
                request.getSession().setAttribute(JspAttributes.ROOMS, roomService.findRoomForBooking(request));
                break;
            }
        }
        return new CommandResult(page, false);
    }
}
