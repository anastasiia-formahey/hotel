package com.anastasiia.web.command.common;

import com.anastasiia.dto.UserDTO;
import com.anastasiia.services.ApplicationService;
import com.anastasiia.services.BookingService;
import com.anastasiia.services.RoomService;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Pages;
import com.anastasiia.utils.Role;
import com.anastasiia.utils.Status;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Date;

public class HomeCommand implements Command {
    ApplicationService applicationService = new ApplicationService();
    RoomService roomService = new RoomService();
    BookingService bookingService = new BookingService();
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Role roleInSession;
        HttpSession session = request.getSession();
        UserDTO userDTO = (UserDTO) session.getAttribute(JspAttributes.USER);
        if(userDTO == null){
            roleInSession = Role.UNREGISTERED;
        }else {
            roleInSession = userDTO.getRole();
        }
        session.removeAttribute(JspAttributes.ERROR);
        session.removeAttribute(JspAttributes.EMAIL_EXISTS);
        String homePage;
        switch (roleInSession){
            case CLIENT: {
                homePage = Pages.CLIENT_HOME;
                //todo send attribute count
                // reviewed by manager
                // and not confirmed by client application
                break;
            }
            case MANAGER: {
                homePage = Pages.MANAGER_HOME;
                Date dateOfOccupancy = bookingService.getCurrentDate();
                if(request.getParameter("dateOfOccupancy") ==null ){
                    session.setAttribute("dateOfOccupancy", dateOfOccupancy);
                }else {
                    dateOfOccupancy = Date.valueOf(session.getAttribute("dateOfOccupancy").toString());
                }
                session.setAttribute("applicationCount", applicationService.applicationCountByStatus(Status.NEW));
                session.setAttribute("roomMap", roomService.getRoomMap(dateOfOccupancy));
                request.getSession().setAttribute(JspAttributes.ROOMS,
                        roomService.selectAllRoomsDTO(roomService.selectAllRooms()));
                break;
            }
            case ADMIN: {
                homePage = Pages.ADMIN_HOME;
                break;
            }
            default:{
                homePage = Pages.INDEX;
            }

        }
        return new CommandResult(homePage, true);
    }
}
