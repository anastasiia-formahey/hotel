package com.anastasiia.web.command.common;

import com.anastasiia.dao.Fields;
import com.anastasiia.dto.ApplicationDTO;
import com.anastasiia.dto.UserDTO;
import com.anastasiia.services.ApplicationService;
import com.anastasiia.services.Pagination;
import com.anastasiia.utils.*;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;
import com.anastasiia.web.context.AppContext;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ViewApplicationsCommand implements Command {
    ApplicationService applicationService;
    private static final Logger log = Logger.getLogger(ViewApplicationsCommand.class);

    public ViewApplicationsCommand(AppContext appContext) {
        applicationService = appContext.getApplicationService();
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
        List<ApplicationDTO> applicationList;
        String page;
        int currentPage = Pagination.getCurrentPage(request.getParameter(JspAttributes.CURRENT_PAGE));
        String orderBy = request.getParameter(JspAttributes.ORDER_BY);
        UserDTO userDTO = (UserDTO) request.getSession().getAttribute(JspAttributes.USER);
        int rows = request.getSession().getAttribute(JspAttributes.ROLE).equals(Role.MANAGER)
                ? applicationService.applicationCountAll()
                : applicationService.selectAll(userDTO.getId()).size();
        request.getSession().setAttribute(JspAttributes.ROWS, rows);
        if (orderBy == null){
            orderBy= Fields.STATUS_ID;
        }
        request.getSession().setAttribute(JspAttributes.ORDER_BY, orderBy);
        Pagination.setPagination(request);
        switch ((Role)request.getSession().getAttribute(JspAttributes.ROLE)){
            case CLIENT : {
                applicationList = applicationService.selectAll(currentPage, Pagination.RECORDS_PER_PAGE, orderBy,userDTO.getId());
                page = Pages.CLIENT_VIEW_APPLICATIONS;
                break;
            }
            case MANAGER : {
                request.getSession().setAttribute(JspAttributes.APPLICATION_COUNT, applicationService.applicationCountByStatus(Status.NEW));
                applicationList = applicationService.selectAll(currentPage, Pagination.RECORDS_PER_PAGE, orderBy);
                page = Pages.MANAGER_VIEW_APPLICATIONS;
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + request.getSession().getAttribute(JspAttributes.ROLE));
        }
        request.getSession().setAttribute(JspAttributes.APPLICATIONS, applicationList);
       return new CommandResult(page, false);
    }
}
