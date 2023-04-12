package com.anastasiia.web.command.common;

import com.anastasiia.dto.UserDTO;
import com.anastasiia.exceptions.ServiceException;
import com.anastasiia.exceptions.ValidationException;
import com.anastasiia.services.*;
import com.anastasiia.services.impl.ApplicationService;
import com.anastasiia.services.impl.BookingService;
import com.anastasiia.services.impl.RequestService;
import com.anastasiia.services.impl.RoomService;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Pages;
import com.anastasiia.utils.Role;
import com.anastasiia.utils.Status;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;
import com.anastasiia.web.context.AppContext;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Date;

public class HomeCommand implements Command {
    private static final Logger log = Logger.getLogger(HomeCommand.class);
    ApplicationService applicationService;
    RoomService roomService;
    BookingService bookingService;
    RequestService requestService;

    public HomeCommand(AppContext appContext) {
        applicationService = appContext.getApplicationService();
        roomService = appContext.getRoomService();
        bookingService = appContext.getBookingService();
        requestService = appContext.getRequestService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {

        String homePage;
        Role roleInSession;
        HttpSession session = request.getSession();
        UserDTO userDTO = (UserDTO) session.getAttribute(JspAttributes.USER);

        if(userDTO == null)  roleInSession = Role.UNREGISTERED;
        else roleInSession = userDTO.getRole();

        switch (roleInSession){
            case CLIENT: {
                bookingService.checkBooking();
                homePage = Pages.CLIENT_HOME;
                break;
            }
            case MANAGER: {
                bookingService.checkBooking();
                requestService.checkRequests();
                homePage = Pages.MANAGER_HOME;
                Date dateOfOccupancy = bookingService.getCurrentDate();
                try{
                    if(request.getParameter(JspAttributes.DATE_OF_OCCUPANCY) == null)session.setAttribute(JspAttributes.DATE_OF_OCCUPANCY, dateOfOccupancy);
                    else dateOfOccupancy = Validation.validDate(session.getAttribute(JspAttributes.DATE_OF_OCCUPANCY).toString());

                    session.setAttribute(JspAttributes.APPLICATION_COUNT, applicationService.applicationCountByStatus(Status.NEW));
                    session.setAttribute(JspAttributes.ROOM_MAP, roomService.getRoomMap(dateOfOccupancy));
                    request.getSession().setAttribute(JspAttributes.ROOMS,
                            roomService.selectAllRoomsDTO(roomService.selectAllRooms()));

                }catch (ValidationException e) {
                    log.error("ValidationException was caught. Cause : "+ e.getMessage());
                    session.setAttribute(JspAttributes.EXCEPTION_MESSAGE, e.getMessage());
                    new CommandResult(request.getHeader("referer"), false);
                }catch (ServiceException e) {
                    log.error("ServiceException was caught. Cause : "+ e);
                }
                break;
            }
            default:{
                homePage = Pages.INDEX;
            }
        }
        try{
            if(request.getSession().getAttribute("showMessage").equals(true)){
                request.getSession().removeAttribute("showMessage");
                request.setAttribute(JspAttributes.EXCEPTION_MESSAGE, request.getSession().getAttribute(JspAttributes.EXCEPTION_MESSAGE));
                request.getSession().removeAttribute(JspAttributes.EXCEPTION_MESSAGE);
                request.setAttribute(JspAttributes.INFO_MESSAGE, request.getSession().getAttribute(JspAttributes.INFO_MESSAGE));
                request.getSession().removeAttribute(JspAttributes.INFO_MESSAGE);
                request.setAttribute(JspAttributes.SUCCESS_MESSAGE, request.getSession().getAttribute(JspAttributes.SUCCESS_MESSAGE));
                request.getSession().removeAttribute(JspAttributes.SUCCESS_MESSAGE);
                return new CommandResult(homePage, false);
            }else {
                return new CommandResult(homePage, true);
            }
        }catch (NullPointerException e){
            return new CommandResult(homePage, true);
        }
    }
}
