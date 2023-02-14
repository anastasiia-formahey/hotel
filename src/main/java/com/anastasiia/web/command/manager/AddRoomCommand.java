package com.anastasiia.web.command.manager;

import com.anastasiia.dto.RoomDTO;
import com.anastasiia.entity.Feature;
import com.anastasiia.services.FeatureService;
import com.anastasiia.services.RoomService;
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
    RoomService roomService = new RoomService();
    FeatureService featureService = new FeatureService();
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        RoomDTO room = new RoomDTO();

               room.setNumberOfPerson(Integer.parseInt(request.getParameter(JspAttributes.NUMBER_OF_PERSONS)));
               room.setPrice(Integer.parseInt(request.getParameter(JspAttributes.PRICE)));
               room.setClassOfRoom(ClassOfRoom.valueOf(request.getParameter(JspAttributes.CLASS_OF_ROOM)));
               room.setImage(request.getParameter(JspAttributes.IMAGE));

        List<Feature> features = featureService.getFeaturesForRoom(request);
        room.setId(roomService.insertRoom(room));
        featureService.insertRoomFeatures(room, features);

        return new CommandResult(request.getHeader("referer"), true);
    }
}
