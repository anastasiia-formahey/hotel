package com.anastasiia.web.command.manager;

import com.anastasiia.dto.ApplicationDTO;
import com.anastasiia.dto.RequestDTO;
import com.anastasiia.dto.RoomDTO;
import com.anastasiia.services.ApplicationService;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Pages;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class ReviewApplicationCommand implements Command {
    private static final Logger log = Logger.getLogger(ReviewApplicationCommand.class);
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute("rooms");
        List<ApplicationDTO> applicationDTOList = (ArrayList<ApplicationDTO>)request.getSession().getAttribute(JspAttributes.APPLICATIONS);
        ArrayList<Integer> addedRooms = new ArrayList<>();
        RequestDTO requestDTO = new RequestDTO();
        ApplicationDTO applicationDTO = new ApplicationService()
                .get(applicationDTOList, Integer.parseInt(request.getParameter("applicationToReview")));
        request.getSession().setAttribute("app", applicationDTO);
        log.debug(applicationDTO);
        request.getSession().setAttribute("listOfRoomInReview", new ArrayList<RoomDTO>());
        request.getSession().setAttribute("addedRooms", addedRooms);
        request.getSession().setAttribute("requestDTO", requestDTO);

        return new CommandResult(Pages.MANAGER_REVIEW_APPLICATIONS, true);
    }
}
