package com.anastasiia.web.command.common;

import com.anastasiia.dao.Fields;
import com.anastasiia.dto.ApplicationDTO;
import com.anastasiia.dto.UserDTO;
import com.anastasiia.services.ApplicationService;
import com.anastasiia.services.Pagination;
import com.anastasiia.utils.*;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ViewApplicationsCommand implements Command {
    ApplicationService applicationService = new ApplicationService();
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        List<ApplicationDTO> applicationList;
        String page;
        int currentPage = Pagination.getCurrentPage(request.getParameter(JspAttributes.CURRENT_PAGE));
        String orderBy = request.getParameter(JspAttributes.ORDER_BY);
        UserDTO userDTO = (UserDTO) request.getSession().getAttribute(JspAttributes.USER);
        int rows = request.getSession().getAttribute(JspAttributes.ROLE).equals(Role.MANAGER)
                ? applicationService.selectAll().size()
                : applicationService.selectAll(userDTO.getId()).size();
        request.getSession().setAttribute("rows", rows);
        if (orderBy == null){
            orderBy= Fields.USER_ID;
        }
        Pagination.setPagination(request);
        switch ((Role)request.getSession().getAttribute(JspAttributes.ROLE)){
            case CLIENT : { applicationList = applicationService.selectAll(
                    currentPage, Pagination.RECORDS_PER_PAGE, orderBy,userDTO.getId());
                page = Pages.CLIENT_VIEW_APPLICATIONS;
                break;
            }
            case MANAGER:{
                request.getSession().setAttribute("applicationCount", applicationService.applicationCountByStatus(Status.NEW));
                applicationList = applicationService.selectAll(currentPage, Pagination.RECORDS_PER_PAGE, orderBy);
                page = Pages.MANAGER_VIEW_APPLICATIONS;
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + (Role) request.getSession().getAttribute(JspAttributes.ROLE));
        }
        request.getSession().setAttribute(JspAttributes.APPLICATIONS, applicationList);
       return new CommandResult(page, false);
    }
}
