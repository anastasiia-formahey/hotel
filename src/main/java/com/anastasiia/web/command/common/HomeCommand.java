package com.anastasiia.web.command.common;

import com.anastasiia.dto.UserDTO;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Pages;
import com.anastasiia.utils.Role;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HomeCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Role roleInSession;
        HttpSession session = request.getSession();
        UserDTO userDTO = (UserDTO) session.getAttribute(JspAttributes.USER);
        if(userDTO == null){
            roleInSession = Role.UNREGISTERED;
        }else {
            roleInSession = userDTO.getRole();
        }
        session.removeAttribute(JspAttributes.ERROR);
        session.removeAttribute(JspAttributes.EMAIL_EXISTS);
        String homePage;
        switch (roleInSession){
            case CLIENT: {
                homePage = Pages.CLIENT_HOME;
                break;
            }
            case MANAGER: {
                homePage = Pages.MANAGER_HOME;
                break;
            }
            case ADMIN: {
                homePage = Pages.ADMIN_HOME;
                break;
            }
            default:{
                homePage = Pages.INDEX;
            }

        }
        return new CommandResult(homePage, true);
    }
}
