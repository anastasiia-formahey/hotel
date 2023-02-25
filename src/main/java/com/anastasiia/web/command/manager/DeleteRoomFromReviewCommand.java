package com.anastasiia.web.command.manager;

import com.anastasiia.dto.RequestDTO;
import com.anastasiia.dto.RoomDTO;
import com.anastasiia.services.RoomService;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Pages;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class DeleteRoomFromReviewCommand implements Command {
    private static final Logger log = Logger.getLogger(DeleteRoomFromReviewCommand.class);
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        log.debug("Method starts");
        RequestDTO requestDTO = (RequestDTO) request.getSession().getAttribute(JspAttributes.REQUEST_DTO);
        List<RoomDTO> roomList = (ArrayList) request.getSession().getAttribute(JspAttributes.LIST_OF_ROOM_IN_REVIEW);
        List<Integer> addedRooms = (ArrayList) request.getSession().getAttribute(JspAttributes.ADDED_ROOMS);
        RoomDTO room = new RoomService().findRoomById(Integer.parseInt(request.getParameter(JspAttributes.NUMBER_OF_ROOM)));
        RequestDTO.RequestElement requestElement = requestDTO.getRequestElements()
                .stream().filter(element -> room.getId() == element.getRoom().getId())
                        .findAny().orElse(new RequestDTO.RequestElement());
        roomList.removeIf(room1 -> room.getId()==room1.getId());
        addedRooms.removeIf(id -> room.getId()==id);
            requestDTO.removeRequestElement(requestElement);
        request.getSession().setAttribute(JspAttributes.ADDED_ROOMS,addedRooms);
        request.getSession().setAttribute(JspAttributes.LIST_OF_ROOM_IN_REVIEW, roomList);
        request.getSession().setAttribute(JspAttributes.REQUEST_DTO, requestDTO);
        return new CommandResult(Pages.MANAGER_REVIEW_APPLICATIONS, true);
    }
}
