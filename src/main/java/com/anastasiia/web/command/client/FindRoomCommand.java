package com.anastasiia.web.command.client;

import com.anastasiia.services.RoomService;
import com.anastasiia.utils.Pages;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FindRoomCommand implements Command {
    RoomService roomService = new RoomService();
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute("rooms", roomService.findRoomForBooking(request));
        request.setAttribute("isBooking", true);
        request.setAttribute("numberOfPersonValue", request.getParameter("numberOfPerson"));
        request.getSession().setAttribute("checkIn", request.getParameter("checkInDate"));
        request.getSession().setAttribute("checkOut", request.getParameter("checkOutDate"));
        return new CommandResult(Pages.CLIENT_ROOMS_FOR_BOOKING, false);
    }
}
