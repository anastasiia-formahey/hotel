package com.anastasiia.web.command;

import com.anastasiia.web.command.client.*;
import com.anastasiia.web.command.common.*;
import com.anastasiia.web.command.manager.*;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

public class CommandContainer {

    private static final Logger log = Logger.getLogger(CommandContainer.class);

    private static final Map<String, Command> commandMap = new TreeMap<>();

    static {
        commandMap.put("home", new HomeCommand());
        commandMap.put("locale", new LocaleCommand());
        commandMap.put("loginPage", new LoginPageCommand());
        commandMap.put("login", new LoginCommand());
        commandMap.put("signUpPage", new SignUpPageCommand());
        commandMap.put("signUp", new SignUpCommand());
        commandMap.put("logout", new LogOutCommand());
        commandMap.put("about", new AboutPageCommand());
        commandMap.put("rooms", new RoomPageCommand());
        commandMap.put("addRoomPage", new AddRoomPageCommand());
        commandMap.put("addRoom", new AddRoomCommand());
        commandMap.put("viewRoom", new ViewRoomCommand());
        commandMap.put("editRoomPage", new EditRoomPageCommand());
        commandMap.put("editRoom", new EditRoomCommand());
        commandMap.put("findRoomForBooking", new FindRoomCommand());
        commandMap.put("bookRoomPage", new BookRoomPageCommand());
        commandMap.put("bookRoom", new BookRoomCommand());
        commandMap.put("viewBooking", new ViewBookingCommand());
        commandMap.put("payForBooking", new PayForBookingCommand());
        commandMap.put("addApplication", new AddApplicationCommand());
        commandMap.put("viewApplications", new ViewApplicationsCommand());
        commandMap.put("reviewApplication", new ReviewApplicationCommand());
        commandMap.put("insertRoomInReview", new InsertRoomInReviewCommand());
        commandMap.put("deleteRoomFromReview", new DeleteRoomFromReviewCommand());
        commandMap.put("sendRequest", new SendRequestCommand());
        commandMap.put("getRequest", new GetRequestCommand());
    }

    /**
     * Returns command object with the given name
     * @param commandName
     *          Name of the command
     * @return Command object
     * */
    public static Command get(String commandName) {
        if(commandName == null || !commandMap.containsKey(commandName)){
            log.trace("Command not found, name => " + commandName);
            return commandMap.get("home");
        }
        return commandMap.get(commandName);
    }



}
