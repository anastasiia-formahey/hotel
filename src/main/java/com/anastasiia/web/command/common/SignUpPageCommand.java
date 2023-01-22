package com.anastasiia.web.command.common;

import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Pages;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignUpPageCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute(JspAttributes.ERROR);
        return new CommandResult(Pages.SIGN_UP, false);
    }
}
