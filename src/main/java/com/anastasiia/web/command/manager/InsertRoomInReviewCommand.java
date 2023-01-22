package com.anastasiia.web.command.manager;

import com.anastasiia.dto.ApplicationDTO;
import com.anastasiia.dto.BookingDTO;
import com.anastasiia.dto.RequestDTO;
import com.anastasiia.entity.Room;
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
import java.util.Objects;

public class InsertRoomInReviewCommand implements Command {

    private static final Logger log = Logger.getLogger(InsertRoomInReviewCommand.class);
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        RequestDTO requestDTO = (RequestDTO) request.getSession().getAttribute("requestDTO");
        List<Room> roomList = (ArrayList) request.getSession().getAttribute("listOfRoomInReview");
        List<Integer> addedRooms = (ArrayList) request.getSession().getAttribute("addedRooms");
        ApplicationDTO applicationDTO = (ApplicationDTO) request.getSession().getAttribute("app");
        log.debug(applicationDTO.toString());
        Room room = new RoomService().findRoomById(Integer.parseInt(request.getParameter("numberOfRoom")));
        requestDTO.setRequestElements(
                        room,
                        Date.valueOf(request.getSession().getAttribute("checkIn").toString()),
                        Date.valueOf(request.getSession().getAttribute("checkOut").toString()));
        requestDTO.setApplicationId(applicationDTO.getId());
        roomList.add(room);
        addedRooms.add(room.getId());
        log.debug(requestDTO.toString());
        log.debug(addedRooms.toString());
        request.getSession().setAttribute("addedRooms",addedRooms);
        request.getSession().setAttribute("listOfRoomInReview", roomList);
        return new CommandResult(Pages.MANAGER_REVIEW_APPLICATIONS,true);
    }
}
