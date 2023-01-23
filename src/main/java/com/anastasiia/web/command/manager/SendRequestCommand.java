package com.anastasiia.web.command.manager;

import com.anastasiia.dto.ApplicationDTO;
import com.anastasiia.dto.RequestDTO;
import com.anastasiia.services.ApplicationService;
import com.anastasiia.services.RequestService;
import com.anastasiia.utils.Pages;
import com.anastasiia.utils.Status;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SendRequestCommand implements Command {
    private static final Logger log = Logger.getLogger(SendRequestCommand.class);
    RequestService requestService = new RequestService();
    ApplicationService applicationService = new ApplicationService();
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        RequestDTO requestDTO = (RequestDTO) request.getSession().getAttribute("requestDTO");
        ApplicationDTO applicationDTO = (ApplicationDTO) request.getSession().getAttribute("app");
        requestDTO.setStatusOfRequest(Status.NOT_CONFIRMED);
        log.debug(requestDTO.toString());
        requestService.insertRequest(requestDTO, applicationDTO);
        request.setAttribute("requestSent", true);
        return new CommandResult(Pages.MANAGER_VIEW_APPLICATIONS, true);
    }
}
