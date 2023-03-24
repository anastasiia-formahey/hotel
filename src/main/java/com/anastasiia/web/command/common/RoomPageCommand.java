package com.anastasiia.web.command.common;

import com.anastasiia.dao.Fields;
import com.anastasiia.dto.RoomDTO;
import com.anastasiia.exceptions.ServiceException;
import com.anastasiia.services.BookingService;
import com.anastasiia.services.Pagination;
import com.anastasiia.services.RoomService;
import com.anastasiia.utils.*;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;
import com.anastasiia.web.context.AppContext;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class RoomPageCommand implements Command {
    private static final Logger log = Logger.getLogger(RoomPageCommand.class);
    RoomService roomService;
    BookingService bookingService;

    public RoomPageCommand(AppContext appContext) {
        roomService = appContext.getRoomService();
        bookingService = appContext.getBookingService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String pageRoom = Pages.ROOMS;
        int currentPage;
        String orderBy;
        int rows;
        List<RoomDTO> roomDTOList;

        log.debug("Method starts");
        request.setAttribute(JspAttributes.EXCEPTION_MESSAGE, request.getSession().getAttribute(JspAttributes.EXCEPTION_MESSAGE));
        request.getSession().removeAttribute(JspAttributes.EXCEPTION_MESSAGE);
        request.setAttribute(JspAttributes.INFO_MESSAGE, request.getSession().getAttribute(JspAttributes.INFO_MESSAGE));
        request.getSession().removeAttribute(JspAttributes.INFO_MESSAGE);
        request.setAttribute(JspAttributes.SUCCESS_MESSAGE, request.getSession().getAttribute(JspAttributes.SUCCESS_MESSAGE));
        request.getSession().removeAttribute(JspAttributes.SUCCESS_MESSAGE);

        Object roleParameter = request.getSession().getAttribute(JspAttributes.ROLE);
        if(roleParameter == "" || roleParameter == null){
            roleParameter = Role.UNREGISTERED;
        }
        Role roleInSession =
                Role.valueOf(roleParameter.toString());
        request.getSession().setAttribute(JspAttributes.ROLE, roleInSession);

        currentPage = Pagination.getCurrentPage(request.getParameter(JspAttributes.CURRENT_PAGE));
        orderBy = request.getParameter(JspAttributes.ORDER_BY);
        rows = roomService.countAllRooms();
        request.getSession().setAttribute(JspAttributes.ROWS, rows);
        if (orderBy == null){
            orderBy= Fields.USER_ID;
            request.setAttribute(JspAttributes.ORDER_BY, orderBy);
        }
        Pagination.setPagination(request);
        request.getSession().setAttribute(JspAttributes.CLASS_OF_ROOM, ClassOfRoom.values());
        try {
            roomDTOList = roomService.selectAllRooms(currentPage, Pagination.RECORDS_PER_PAGE, orderBy);
        } catch (ServiceException e) {
            roomDTOList = new ArrayList<>();
        }
        switch (roleInSession){
            case MANAGER: {
                pageRoom = Pages.MANAGER_ROOMS;
                request.getSession().setAttribute(JspAttributes.ROOMS, roomDTOList);
                break;
            }
            case CLIENT:{
                pageRoom = Pages.CLIENT_ROOMS;
                request.getSession().setAttribute(JspAttributes.CURRENT_DATE, bookingService.getCurrentDate());
                request.getSession().setAttribute(JspAttributes.ROOMS, roomDTOList);
                break;
            }
            default:
                request.getSession().setAttribute(JspAttributes.ROOMS, roomDTOList);
        }
        return new CommandResult(pageRoom, false);
    }
}
