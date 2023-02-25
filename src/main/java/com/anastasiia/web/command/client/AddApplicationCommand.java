package com.anastasiia.web.command.client;

import com.anastasiia.dto.ApplicationDTO;
import com.anastasiia.dto.UserDTO;
import com.anastasiia.services.ApplicationService;
import com.anastasiia.utils.ClassOfRoom;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Status;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddApplicationCommand implements Command {
    private static final Logger log = Logger.getLogger(AddApplicationCommand.class);
    ApplicationService applicationService = new ApplicationService();
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        log.debug("Method starts");

        ApplicationDTO applicationDTO = new ApplicationDTO();
        applicationDTO.setUserDTO((UserDTO)(request.getSession().getAttribute(JspAttributes.USER)));
        applicationDTO.setNumberOfGuests(Integer.parseInt(request.getParameter(JspAttributes.NUMBER_OF_PERSON)));
        applicationDTO.setClassOfRoom(ClassOfRoom.valueOf(request.getParameter(JspAttributes.CLASS_OF_ROOM)));
        applicationDTO.setLengthOfStay(Integer.parseInt(request.getParameter(JspAttributes.LENGTH_OF_STAY)));
        applicationDTO.setStatus(Status.NEW);

        applicationService.insertApplication(applicationDTO);

        request.getSession().setAttribute(JspAttributes.APPLICATION_ADDED, true);
        return new CommandResult(request.getHeader("referer"),true );
    }
}
