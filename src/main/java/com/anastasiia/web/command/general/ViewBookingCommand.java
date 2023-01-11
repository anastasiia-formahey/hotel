package com.anastasiia.web.command.general;

import com.anastasiia.dao.BookingDAO;
import com.anastasiia.dao.RoomDAO;
import com.anastasiia.dto.BookingDTO;
import com.anastasiia.dto.UserDTO;
import com.anastasiia.entity.User;
import com.anastasiia.services.BookingService;
import com.anastasiia.services.Pagination;
import com.anastasiia.utils.Fields;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Pages;
import com.anastasiia.utils.Role;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ViewBookingCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        List<BookingDTO> bookingDTOList;
        String page = Pages.INDEX;
        int currentPage = Pagination.getCurrentPage(request.getParameter(JspAttributes.CURRENT_PAGE));
        String orderBy = request.getParameter(JspAttributes.ORDER_BY);
        int rows = request.getSession().getAttribute(JspAttributes.ROLE).equals(Role.MANAGER)
                ? BookingService.selectAllBooking().size()
                : BookingService.selectAllBooking((Integer) request.getSession().getAttribute(JspAttributes.USER)).size();
        request.getSession().setAttribute("rows", rows);
        if (orderBy == null){
            orderBy= Fields.ID;
        }
        Pagination.setPagination(request);
        switch ((Role)request.getSession().getAttribute(JspAttributes.ROLE)){
            case CLIENT : { bookingDTOList = BookingService.selectAllBooking(
                    currentPage, Pagination.RECORDS_PER_PAGE, orderBy,
                    (Integer) request.getSession().getAttribute(JspAttributes.USER));
                page = Pages.CLIENT_VIEW_BOOKINGS;
                break;
            }
            case MANAGER:{
                bookingDTOList = BookingService.selectAllBooking(currentPage, Pagination.RECORDS_PER_PAGE, orderBy);
                page = Pages.MANAGER_VIEW_BOOKINGS;
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + (Role) request.getSession().getAttribute(JspAttributes.ROLE));
        }
        request.getSession().setAttribute(JspAttributes.BOOKINGS, bookingDTOList);
        return new CommandResult(page, false);
    }
}
