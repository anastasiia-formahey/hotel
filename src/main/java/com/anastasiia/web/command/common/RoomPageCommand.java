package com.anastasiia.web.command.common;

import com.anastasiia.dao.Fields;
import com.anastasiia.services.BookingService;
import com.anastasiia.services.Pagination;
import com.anastasiia.services.RoomService;
import com.anastasiia.utils.*;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RoomPageCommand implements Command {
    private static final Logger log = Logger.getLogger(RoomPageCommand.class);
    RoomService roomService = new RoomService();
    BookingService bookingService = new BookingService();
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Object roleParameter = request.getSession().getAttribute(JspAttributes.ROLE);
        if(roleParameter == "" || roleParameter == null){
            roleParameter = Role.UNREGISTERED;
        }
        Role roleInSession =
                Role.valueOf(roleParameter.toString());
        request.getSession().setAttribute(JspAttributes.ROLE, roleInSession);
        log.debug("Role ==> " + roleInSession);
        String pageRoom = Pages.ROOMS;
        int currentPage = Pagination.getCurrentPage(request.getParameter(JspAttributes.CURRENT_PAGE));
        String orderBy = request.getParameter(JspAttributes.ORDER_BY);
        int rows = roomService.selectAllRooms().size();
        request.getSession().setAttribute("rows", rows);
        if (orderBy == null){
            orderBy= Fields.USER_ID;
        }
        Pagination.setPagination(request);
        request.getSession().setAttribute(JspAttributes.CLASS_OF_ROOM, ClassOfRoom.values());
        request.getSession().setAttribute(JspAttributes.CAPACITY, roomService.getCapacityOfRoom());

        switch (roleInSession){
            case MANAGER: {
                pageRoom = Pages.MANAGER_ROOMS;

                request.getSession().setAttribute(JspAttributes.ROOMS,
                        roomService.selectAllRooms(currentPage, Pagination.RECORDS_PER_PAGE, orderBy));
                break;
            }
            case CLIENT:{
                pageRoom = Pages.CLIENT_ROOMS;
                request.getSession().setAttribute("currentDate", bookingService.getCurrentDate());
                request.getSession().setAttribute(JspAttributes.ROOMS,
                        roomService.selectAllRooms(currentPage, Pagination.RECORDS_PER_PAGE, orderBy));
                break;
            }
            default:
                request.getSession().setAttribute(JspAttributes.ROOMS,
                    roomService.selectAllRooms(currentPage, Pagination.RECORDS_PER_PAGE, orderBy));
        }
        log.debug("Go to ==> " + pageRoom);

        return new CommandResult(pageRoom, false);
    }
}
