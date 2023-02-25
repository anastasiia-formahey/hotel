package com.anastasiia.web.command.manager;

import com.anastasiia.dto.ApplicationDTO;
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
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class InsertRoomInReviewCommand implements Command {

    private static final Logger log = Logger.getLogger(InsertRoomInReviewCommand.class);
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        log.debug("Method starts");
        RequestDTO requestDTO = (RequestDTO) request.getSession().getAttribute(JspAttributes.REQUEST_DTO);
        List<RoomDTO> roomList = (ArrayList) request.getSession().getAttribute(JspAttributes.LIST_OF_ROOM_IN_REVIEW);
        List<Integer> addedRooms = (ArrayList) request.getSession().getAttribute(Pages.ADDED_ROOMS);
        ApplicationDTO applicationDTO = (ApplicationDTO) request.getSession().getAttribute(JspAttributes.APP);
        RoomDTO room = new RoomService().findRoomById(Integer.parseInt(request.getParameter(JspAttributes.NUMBER_OF_ROOM)));
        requestDTO.setRequestElements(
                        room,
                        Date.valueOf(request.getSession().getAttribute(JspAttributes.CHECK_IN).toString()),
                        Date.valueOf(request.getSession().getAttribute(JspAttributes.CHECK_OUT).toString()));
        requestDTO.setApplicationId(applicationDTO.getId());
        roomList.add(room);
        addedRooms.add(room.getId());
        request.getSession().setAttribute(Pages.ADDED_ROOMS,addedRooms);
        request.getSession().setAttribute(JspAttributes.LIST_OF_ROOM_IN_REVIEW, roomList);
        return new CommandResult(Pages.MANAGER_REVIEW_APPLICATIONS,true);
    }
}
