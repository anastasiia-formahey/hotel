package com.anastasiia.web.command.manager;

import com.anastasiia.exceptions.ServiceException;
import com.anastasiia.exceptions.ValidationException;
import com.anastasiia.services.impl.OccupancyOfRoomService;
import com.anastasiia.services.Validation;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Pages;
import com.anastasiia.utils.Status;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;
import com.anastasiia.web.context.AppContext;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

public class CheckInRoom implements Command {
    private static final Logger log = Logger.getLogger(CheckInRoom.class);
    OccupancyOfRoomService occupancyOfRoomService;

    public CheckInRoom(AppContext appContext) {
        occupancyOfRoomService = appContext.getOccupancyOfRoomService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        log.debug("Method starts");
        int roomId;
        Date checkIn;
        Date checkOut;
        try {
            roomId = Validation.validIntField(request.getParameter(JspAttributes.ROOM_ID));
            checkIn = Validation.validDate(request.getParameter(JspAttributes.CHECK_IN));
            checkOut = Validation.validDate(request.getParameter(JspAttributes.CHECK_OUT));
            Validation.validDateToCheckIn(checkIn,checkOut);

            occupancyOfRoomService.updateStatus(roomId, Status.BUSY, checkIn,checkOut);
            request.getSession().setAttribute(JspAttributes.SUCCESS_MESSAGE, JspAttributes.CHECK_IN_SUCCESS);
            request.getSession().setAttribute("showMessage", true);
        } catch (ValidationException e) {
            log.error("ValidationException was caught. Cause : "+ e);
            request.getSession().setAttribute(JspAttributes.EXCEPTION_MESSAGE, e.getMessage());
            request.getSession().setAttribute("showMessage", true);
            return new CommandResult(Pages.MANAGER_COMMAND_HOME, true);
        }catch (ServiceException e) {
            log.error("ServiceException was caught. Cause : "+ e);
        }
        return new CommandResult(Pages.MANAGER_COMMAND_HOME, true);
    }
}
