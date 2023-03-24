package com.anastasiia.web.command;

import com.anastasiia.web.command.client.*;
import com.anastasiia.web.command.common.*;
import com.anastasiia.web.command.manager.*;
import com.anastasiia.web.context.AppContext;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

public class CommandContainer {

    private static final Logger log = Logger.getLogger(CommandContainer.class);
    private static final AppContext appContext = AppContext.getAppContext();
    private static final Map<String, Command> commandMap = new TreeMap<>();

    static {
        commandMap.put("locale", new LocaleCommand());
        commandMap.put("loginPage", new LoginPageCommand());
        commandMap.put("signUpPage", new SignUpPageCommand());
        commandMap.put("logout", new LogOutCommand());
        commandMap.put("about", new AboutPageCommand());
        commandMap.put("home", new HomeCommand(appContext));
        commandMap.put("login", new LoginCommand(appContext));
        commandMap.put("signUp", new SignUpCommand(appContext));
        commandMap.put("editProfile", new EditProfile(appContext));
        commandMap.put("editPassword", new EditPassword(appContext));
        commandMap.put("rooms", new RoomPageCommand(appContext));
        commandMap.put("addRoomPage", new AddRoomPageCommand(appContext));
        commandMap.put("addRoom", new AddRoomCommand(appContext));
        commandMap.put("viewRoom", new ViewRoomCommand(appContext));
        commandMap.put("editRoomPage", new EditRoomPageCommand(appContext));
        commandMap.put("editRoom", new EditRoomCommand(appContext));
        commandMap.put("findRoomForBooking", new FindRoomCommand(appContext));
        commandMap.put("findRoomPage", new FindRoomPageCommand());
        commandMap.put("bookRoomPage", new BookRoomPageCommand(appContext));
        commandMap.put("bookRoom", new BookRoomCommand(appContext));
        commandMap.put("viewBooking", new ViewBookingCommand(appContext));
        commandMap.put("payForBooking", new PayForBookingCommand(appContext));
        commandMap.put("addApplication", new AddApplicationCommand(appContext));
        commandMap.put("viewApplications", new ViewApplicationsCommand(appContext));
        commandMap.put("reviewApplication", new ReviewApplicationCommand(appContext));
        commandMap.put("reviewApplicationPage", new ReviewApplicationPageCommand());
        commandMap.put("insertRoomInReview", new InsertRoomInReviewCommand(appContext));
        commandMap.put("deleteRoomFromReview", new DeleteRoomFromReviewCommand(appContext));
        commandMap.put("sendRequest", new SendRequestCommand(appContext));
        commandMap.put("getRequest", new GetRequestCommand(appContext));
        commandMap.put("cancelRequest", new CancelRequestCommand(appContext));
        commandMap.put("getOccupancyOfRoom", new GetOccupancyOfRoom(appContext));
        commandMap.put("viewOccupancyOfRoom", new ViewOccupancyOfRoom(appContext));
        commandMap.put("checkInRoom", new CheckInRoom(appContext));
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
