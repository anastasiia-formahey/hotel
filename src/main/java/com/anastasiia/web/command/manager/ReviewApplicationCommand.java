package com.anastasiia.web.command.manager;

import com.anastasiia.dto.ApplicationDTO;
import com.anastasiia.dto.RequestDTO;
import com.anastasiia.dto.RoomDTO;
import com.anastasiia.services.ApplicationService;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Pages;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;
import com.anastasiia.web.context.AppContext;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class ReviewApplicationCommand implements Command {
    private static final Logger log = Logger.getLogger(ReviewApplicationCommand.class);
    ApplicationService applicationService;

    public ReviewApplicationCommand(AppContext appContext) {
        applicationService = appContext.getApplicationService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        log.debug("Method starts");
        request.getSession().removeAttribute(JspAttributes.ROOMS);
        List<ApplicationDTO> applicationDTOList = (ArrayList<ApplicationDTO>)request.getSession().getAttribute(JspAttributes.APPLICATIONS);
        ArrayList<Integer> addedRooms = new ArrayList<>();
        RequestDTO requestDTO = new RequestDTO();
        ApplicationDTO applicationDTO = applicationService
                .get(applicationDTOList, Integer.parseInt(request.getParameter(JspAttributes.APPLICATION_TO_REVIEW)));
        request.getSession().setAttribute(JspAttributes.APP, applicationDTO);
        request.getSession().setAttribute(JspAttributes.LIST_OF_ROOM_IN_REVIEW, new ArrayList<RoomDTO>());
        request.getSession().setAttribute(JspAttributes.ADDED_ROOMS, addedRooms);
        request.getSession().setAttribute(JspAttributes.REQUEST_DTO, requestDTO);

        return new CommandResult(Pages.MANAGER_REVIEW_APPLICATIONS, true);
    }
}
