package com.anastasiia.web.command.manager;

import com.anastasiia.dto.RequestDTO;
import com.anastasiia.dto.RoomDTO;
import com.anastasiia.services.RoomService;
import com.anastasiia.utils.Pages;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class DeleteRoomFromReviewCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        RequestDTO requestDTO = (RequestDTO) request.getSession().getAttribute("requestDTO");
        List<RoomDTO> roomList = (ArrayList) request.getSession().getAttribute("listOfRoomInReview");
        List<Integer> addedRooms = (ArrayList) request.getSession().getAttribute("addedRooms");
        RoomDTO room = new RoomService().findRoomById(Integer.parseInt(request.getParameter("numberOfRoom")));
        RequestDTO.RequestElement requestElement = requestDTO.getRequestElements()
                .stream().filter(element -> room.getId() == element.getRoom().getId())
                        .findAny().orElse(new RequestDTO.RequestElement());
        roomList.removeIf(room1 -> room.getId()==room1.getId());
        addedRooms.removeIf(id -> room.getId()==id);
            requestDTO.removeRequestElement(requestElement);
        request.getSession().setAttribute("addedRooms",addedRooms);
        request.getSession().setAttribute("listOfRoomInReview", roomList);
        request.getSession().setAttribute("requestDTO", requestDTO);
        return new CommandResult(Pages.MANAGER_REVIEW_APPLICATIONS, true);
    }
}
