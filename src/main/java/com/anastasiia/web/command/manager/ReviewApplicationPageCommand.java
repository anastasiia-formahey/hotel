package com.anastasiia.web.command.manager;

import com.anastasiia.exceptions.ServiceException;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Pages;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReviewApplicationPageCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response){
        request.setAttribute(JspAttributes.EXCEPTION_MESSAGE, request.getSession().getAttribute(JspAttributes.EXCEPTION_MESSAGE));
        request.getSession().removeAttribute(JspAttributes.EXCEPTION_MESSAGE);
        request.setAttribute(JspAttributes.INFO_MESSAGE, request.getSession().getAttribute(JspAttributes.INFO_MESSAGE));
        request.getSession().removeAttribute(JspAttributes.INFO_MESSAGE);

        return new CommandResult(Pages.MANAGER_REVIEW_APPLICATIONS, false);
    }
}
