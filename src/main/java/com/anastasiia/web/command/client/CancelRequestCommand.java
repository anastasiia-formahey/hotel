package com.anastasiia.web.command.client;

import com.anastasiia.dto.RequestDTO;
import com.anastasiia.exceptions.ServiceException;
import com.anastasiia.services.OccupancyOfRoomService;
import com.anastasiia.services.RequestService;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Pages;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;
import com.anastasiia.web.context.AppContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CancelRequestCommand implements Command {
    RequestService requestService;
    OccupancyOfRoomService occupancyOfRoomService;
    public CancelRequestCommand(AppContext appContext) {
        requestService = appContext.getRequestService();
        occupancyOfRoomService = appContext.getOccupancyOfRoomService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response){
        RequestDTO requestDTO;
        try {
            requestDTO = requestService.getRequestByApplicationId(
                    Integer.parseInt(request.getParameter(JspAttributes.APPLICATION_ID)));
            requestService.cancelRequest(requestDTO);

            request.getSession().setAttribute(JspAttributes.SUCCESS_MESSAGE, JspAttributes.CANCEL_REQUEST_SUCCESS);
        } catch (ServiceException e) {
            request.getSession().setAttribute(JspAttributes.EXCEPTION_MESSAGE, e.getMessage());
        }

        return new CommandResult(Pages.CLIENT_VIEW_APPLICATIONS_COMMAND, true);
    }
}
