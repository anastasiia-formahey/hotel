package com.anastasiia.web.command.common;

import com.anastasiia.dto.BookingDTO;
import com.anastasiia.dto.UserDTO;
import com.anastasiia.exceptions.ServiceException;
import com.anastasiia.services.BookingService;
import com.anastasiia.services.Pagination;
import com.anastasiia.dao.Fields;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Pages;
import com.anastasiia.utils.Role;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;
import com.anastasiia.web.context.AppContext;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class ViewBookingCommand implements Command {
    private static final Logger log = Logger.getLogger(ViewBookingCommand.class);
    BookingService bookingService;

    public ViewBookingCommand(AppContext appContext) {
        bookingService = appContext.getBookingService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(JspAttributes.EXCEPTION_MESSAGE, request.getSession().getAttribute(JspAttributes.EXCEPTION_MESSAGE));
        request.getSession().removeAttribute(JspAttributes.EXCEPTION_MESSAGE);
        request.setAttribute(JspAttributes.INFO_MESSAGE, request.getSession().getAttribute(JspAttributes.INFO_MESSAGE));
        request.getSession().removeAttribute(JspAttributes.INFO_MESSAGE);
        request.setAttribute(JspAttributes.SUCCESS_MESSAGE, request.getSession().getAttribute(JspAttributes.SUCCESS_MESSAGE));
        request.getSession().removeAttribute(JspAttributes.SUCCESS_MESSAGE);

        log.debug("Method starts");
        List<BookingDTO> bookingDTOList = new ArrayList<>();
        UserDTO userDTO = (UserDTO) request.getSession().getAttribute(JspAttributes.USER);
        String page;
        int currentPage = Pagination.getCurrentPage(request.getParameter(JspAttributes.CURRENT_PAGE));
        String orderBy = request.getParameter(JspAttributes.ORDER_BY);
        int rows = request.getSession().getAttribute(JspAttributes.ROLE).equals(Role.MANAGER)
                ? bookingService.countAllBooking()
                : bookingService.countAllBookingByUserId(userDTO.getId());
        request.getSession().setAttribute(JspAttributes.ROWS, rows);
        if (orderBy == null){
            orderBy= Fields.USER_ID;
        }
        Pagination.setPagination(request);
        switch ((Role)request.getSession().getAttribute(JspAttributes.ROLE)){
            case CLIENT : {
                try {
                    bookingDTOList = bookingService.selectAllBooking(
                            currentPage, Pagination.RECORDS_PER_PAGE, orderBy, userDTO.getId());

                } catch (ServiceException e) {
                    log.error("ServiceException was caught. Cause : "+ e);
                }
                page = Pages.CLIENT_VIEW_BOOKINGS;
                break;
            }
            case MANAGER : {
                try {
                    bookingDTOList = bookingService.selectAllBooking(currentPage, Pagination.RECORDS_PER_PAGE, orderBy);
                } catch (ServiceException e) {
                    log.error("ServiceException was caught. Cause : "+ e);
                }
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
