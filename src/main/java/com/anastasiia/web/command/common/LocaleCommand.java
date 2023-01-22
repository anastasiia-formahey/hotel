package com.anastasiia.web.command.common;

import com.anastasiia.utils.JspAttributes;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LocaleCommand implements Command {
    private static final Logger log = Logger.getLogger(LocaleCommand.class);
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String language = request.getParameter(JspAttributes.LOCALE);
        request.getSession().setAttribute(JspAttributes.LOCALE, language);
        log.debug("Change locale to " + language);
        return new CommandResult(request.getHeader("referer"), true);

    }
}
