package com.anastasiia.web.command;

import com.anastasiia.exceptions.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

public interface Command extends Serializable {
    CommandResult execute(HttpServletRequest request, HttpServletResponse response);

}
