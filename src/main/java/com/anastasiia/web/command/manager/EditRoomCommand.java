package com.anastasiia.web.command.manager;

import com.anastasiia.services.RoomService;
import com.anastasiia.utils.Pages;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditRoomCommand implements Command {
    private static final Logger log = Logger.getLogger(EditRoomCommand.class);
    RoomService roomService = new RoomService();
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        log.debug("Method starts");
        roomService.editRoom(request);
        log.debug("Method finished");
        return new CommandResult(Pages.EDIT_ROOM, true);
    }
}
