package com.anastasiia.web.command.common;

import com.anastasiia.dto.BookingDTO;
import com.anastasiia.dto.UserDTO;
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
    BookingService bookingService = new BookingService();
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        List<BookingDTO> bookingDTOList;
        UserDTO userDTO = (UserDTO) request.getSession().getAttribute(JspAttributes.USER);
        String page = Pages.INDEX;
        int currentPage = Pagination.getCurrentPage(request.getParameter(JspAttributes.CURRENT_PAGE));
        String orderBy = request.getParameter(JspAttributes.ORDER_BY);
        int rows = request.getSession().getAttribute(JspAttributes.ROLE).equals(Role.MANAGER)
                ? bookingService.selectAllBooking().size()
                : bookingService.selectAllBooking(userDTO.getId()).size();
        request.getSession().setAttribute("rows", rows);
        if (orderBy == null){
            orderBy= Fields.ID;
        }
        Pagination.setPagination(request);
        switch ((Role)request.getSession().getAttribute(JspAttributes.ROLE)){
            case CLIENT : { bookingDTOList = bookingService.selectAllBooking(
                    currentPage, Pagination.RECORDS_PER_PAGE, orderBy, userDTO.getId());
                page = Pages.CLIENT_VIEW_BOOKINGS;
                break;
            }
            case MANAGER:{
                bookingDTOList = bookingService.selectAllBooking(currentPage, Pagination.RECORDS_PER_PAGE, orderBy);
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
