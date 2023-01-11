package com.anastasiia.web.command.manager;

import com.anastasiia.dao.FeaturesDAO;
import com.anastasiia.dao.RoomDAO;
import com.anastasiia.entity.Feature;
import com.anastasiia.entity.Room;
import com.anastasiia.services.FeatureService;
import com.anastasiia.utils.ClassOfRoom;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.web.command.Command;
import com.anastasiia.web.command.CommandResult;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AddRoomCommand implements Command {
    private static final Logger log = Logger.getLogger(AddRoomCommand.class);
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Room room = new Room(
                Integer.parseInt(request.getParameter(JspAttributes.NUMBER_OF_PERSONS)),
                Integer.parseInt(request.getParameter(JspAttributes.PRICE)),
                ClassOfRoom.valueOf(request.getParameter(JspAttributes.CLASS_OF_ROOM)),
                request.getParameter(JspAttributes.IMAGE)
        );
        List<Feature> features = FeatureService.getFeaturesForRoom(request);
        boolean flag = RoomDAO.getInstance().insertRoom(room);
        FeatureService.insertRoomFeatures(room, features);
        if(flag){
            request.getSession().setAttribute(JspAttributes.MESSAGE_SUCCESS, "Room was added");
        }
        return new CommandResult(request.getHeader("referer"), true);
    }
}
