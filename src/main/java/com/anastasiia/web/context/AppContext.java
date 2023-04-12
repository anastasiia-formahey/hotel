package com.anastasiia.web.context;

import com.anastasiia.dao.DBManager;
import com.anastasiia.services.impl.*;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

public class AppContext {
    private static AppContext appContext;
    private static DataSource dataSource;
    private final ApplicationService applicationService;
    private final BookingService bookingService;
    private final FeatureService featureService;
    private final OccupancyOfRoomService occupancyOfRoomService;
    private final RequestService requestService;
    private final RoomService roomService;
    private final UserService userService;

    private AppContext (ServletContext servletContext){
        dataSource = DBManager.getInstance();
        applicationService = new ApplicationService();
        bookingService = new BookingService();
        featureService = new FeatureService();
        occupancyOfRoomService = new OccupancyOfRoomService();
        requestService = new RequestService();
        roomService = new RoomService();
        userService = new UserService();
    }

    public static AppContext getAppContext() {
        return appContext;
    }

    public static void createAppContext(ServletContext servletContext){
        appContext = new AppContext(servletContext);
    }

    public ApplicationService getApplicationService() {
        return applicationService;
    }

    public BookingService getBookingService() {
        return bookingService;
    }

    public FeatureService getFeatureService() {
        return featureService;
    }

    public OccupancyOfRoomService getOccupancyOfRoomService() {
        return occupancyOfRoomService;
    }

    public RequestService getRequestService() {
        return requestService;
    }

    public RoomService getRoomService() {
        return roomService;
    }

    public UserService getUserService() {
        return userService;
    }
}
