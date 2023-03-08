package com.anastasiia.web.command.common;

import com.anastasiia.dto.RoomDTO;
import com.anastasiia.dto.UserDTO;
import com.anastasiia.exceptions.NoResultException;
import com.anastasiia.exceptions.ServiceException;
import com.anastasiia.exceptions.ValidationException;
import com.anastasiia.services.RoomService;
import com.anastasiia.services.Validation;
import com.anastasiia.utils.ClassOfRoom;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Pages;
import com.anastasiia.utils.Role;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;
import com.anastasiia.web.context.AppContext;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.List;

public class FindRoomCommand implements Command {
    private static final Logger log = Logger.getLogger(FindRoomCommand.class);
    RoomService roomService;
    public FindRoomCommand(AppContext appContext) {
        roomService = appContext.getRoomService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String page = "";
        List<RoomDTO> roomDTOList;
        UserDTO userDTO = (UserDTO) request.getSession().getAttribute(JspAttributes.USER);
        request.setAttribute(JspAttributes.IS_BOOKING, true);
        try{
            request.getSession().setAttribute(JspAttributes.NUMBER_OF_PERSON,
                    Validation.validIntField(request.getParameter(JspAttributes.NUMBER_OF_PERSON)));
            Date checkIn = Validation.validDate(request.getParameter(JspAttributes.CHECK_IN_DATE));
            Date checkOut = Validation.validDate(request.getParameter(JspAttributes.CHECK_OUT_DATE));
            Validation.validField(checkIn,checkOut);
            request.getSession().setAttribute(JspAttributes.CHECK_IN, checkIn.toString());
            request.getSession().setAttribute(JspAttributes.CHECK_OUT, checkOut.toString());
            request.getSession().setAttribute(JspAttributes.CLASS_OF_ROOM, ClassOfRoom.values());
        }catch (ValidationException e){
            log.error("ValidationException was caught. Cause : " + e.getMessage());
            request.getSession().setAttribute(JspAttributes.EXCEPTION_MESSAGE, e.getMessage());
            if(userDTO.getRole().equals(Role.CLIENT)) page = Pages.CLIENT_ROOMS_COMMAND;
            if(userDTO.getRole().equals(Role.MANAGER)) page = Pages.MANAGER_REVIEW_APPLICATIONS_COMMAND;
            return new CommandResult(page, true);
        }
        switch (userDTO.getRole()){
            case CLIENT: {
                page = Pages.CLIENT_ROOMS_FOR_BOOKING;
                try {
                    roomDTOList = roomService.findRoomForBooking(request);
                    request.getSession().setAttribute(JspAttributes.ROOMS, roomDTOList);
                }catch (NoResultException e) {
                    log.error("NoResultException was caught. Cause : "+ e);
                    request.getSession().setAttribute(JspAttributes.INFO_MESSAGE, e.getMessage());
                    request.getSession().removeAttribute(JspAttributes.ROOMS);
                    return new CommandResult(Pages.CLIENT_ROOMS_COMMAND, true);
                }catch (ServiceException e) {
                    log.error("ServiceException was caught. Cause : "+ e);
                }
                break;
            }
            case MANAGER:{
                page = Pages.MANAGER_REVIEW_APPLICATIONS;
                try {
                    roomDTOList = roomService.findRoomForBooking(request);
                    request.getSession().setAttribute(JspAttributes.ROOMS, roomDTOList);
                }catch (NoResultException e) {
                    log.error("NoResultException was caught. Cause : "+ e);
                    request.getSession().setAttribute(JspAttributes.INFO_MESSAGE, e.getMessage());
                    request.getSession().removeAttribute(JspAttributes.ROOMS);
                    return new CommandResult(Pages.MANAGER_REVIEW_APPLICATIONS_COMMAND, true);
                }catch (ServiceException e) {
                    log.error("ServiceException was caught. Cause : "+ e);
                }
                break;
            }
        }
        return new CommandResult(page, false);
    }
}
