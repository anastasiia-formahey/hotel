package com.anastasiia.web.command.common;

import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Pages;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginPageCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(JspAttributes.EXCEPTION_MESSAGE, request.getSession().getAttribute(JspAttributes.EXCEPTION_MESSAGE));
        request.setAttribute(JspAttributes.ERROR, request.getSession().getAttribute(JspAttributes.ERROR));
        request.setAttribute(JspAttributes.IS_SUCCESS, request.getSession().getAttribute(JspAttributes.IS_SUCCESS));

        request.getSession().removeAttribute(JspAttributes.ERROR);
        request.getSession().removeAttribute(JspAttributes.EXCEPTION_MESSAGE);
        request.getSession().removeAttribute(JspAttributes.IS_SUCCESS);
        return new CommandResult(Pages.LOGIN, false);
    }
}
