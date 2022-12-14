package com.anastasiia.web.command.general;

import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Pages;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogOutCommand implements Command {
    private static final Logger log = Logger.getLogger(LogOutCommand.class);
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        log.debug("LogOutCommand starts");
        HttpSession session = request.getSession();
        session.removeAttribute(JspAttributes.USER);
            session.invalidate();
        log.debug("LogOutCommand finished");
        return new CommandResult(Pages.INDEX, true);
    }
}
