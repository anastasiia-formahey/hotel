package com.anastasiia.web.command.common;

import com.anastasiia.dto.BookingDTO;
import com.anastasiia.dto.UserDTO;
import com.anastasiia.services.BookingService;
import com.anastasiia.services.Pagination;
import com.anastasiia.dao.Fields;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Pages;
import com.anastasiia.utils.Role;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ViewBookingCommand implements Command {
    private static final Logger log = Logger.getLogger(ViewBookingCommand.class);
    BookingService bookingService = new BookingService();
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        log.debug("Method starts");
        List<BookingDTO> bookingDTOList;
        UserDTO userDTO = (UserDTO) request.getSession().getAttribute(JspAttributes.USER);
        String page;
        int currentPage = Pagination.getCurrentPage(request.getParameter(JspAttributes.CURRENT_PAGE));
        String orderBy = request.getParameter(JspAttributes.ORDER_BY);
        int rows = request.getSession().getAttribute(JspAttributes.ROLE).equals(Role.MANAGER)
                ? bookingService.selectAllBooking().size()
                : bookingService.selectAllBooking(userDTO.getId()).size();
        request.getSession().setAttribute(JspAttributes.ROWS, rows);
        if (orderBy == null){
            orderBy= Fields.USER_ID;
        }
        Pagination.setPagination(request);
        switch ((Role)request.getSession().getAttribute(JspAttributes.ROLE)){
            case CLIENT : { bookingDTOList = bookingService.selectAllBooking(
                    currentPage, Pagination.RECORDS_PER_PAGE, orderBy, userDTO.getId());
                page = Pages.CLIENT_VIEW_BOOKINGS;
                request.setAttribute(JspAttributes.IS_SUCCESS, request.getSession().getAttribute(JspAttributes.IS_SUCCESS));
                request.getSession().removeAttribute(JspAttributes.IS_SUCCESS);
                break;
            }
            case MANAGER:{
                bookingDTOList = bookingService.selectAllBooking(currentPage, Pagination.RECORDS_PER_PAGE, orderBy);
                page = Pages.MANAGER_VIEW_BOOKINGS;
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + request.getSession().getAttribute(JspAttributes.ROLE));
        }
        request.getSession().setAttribute(JspAttributes.BOOKINGS, bookingDTOList);
        return new CommandResult(page, false);
    }
}
