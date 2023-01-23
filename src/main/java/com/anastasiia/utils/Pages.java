package com.anastasiia.utils;

public interface Pages {

    //general
    String INDEX = "/index.jsp";
    String LOGIN = "/login.jsp";
    String LOGIN_COMMAND = "/?command=loginPage";
    String SIGN_UP = "/sign_up.jsp";
    String ROOMS = "/rooms.jsp";
    String VIEW_ROOM =  "/viewRoom.jsp";;
    String ABOUT = "/about.jsp";

    //client
    String CLIENT_HOME = "/client/home.jsp";
    String CLIENT_ROOMS = "/client/rooms.jsp";
    String CLIENT_VIEW_ROOM =  "/client/viewRoom.jsp";
    String CLIENT_BOOK_ROOM =  "/client/bookRoomPage.jsp";
    String CLIENT_VIEW_APPLICATIONS = "/client/viewApplications.jsp";
    String CLIENT_VIEW_BOOKINGS = "/client/viewBooking.jsp";
    String CLIENT_ROOMS_FOR_BOOKING = "/client/roomsForBooking.jsp";
    String BOOK_ROOM = "/client/?command=viewBooking";
    String CLIENT_VIEW_REQUEST = "/client/viewRequest.jsp";
    //manager
    String MANAGER_HOME = "/manager/home.jsp";
    String MANAGER_ROOMS = "/manager/rooms.jsp";
    String MANAGER_VIEW_ROOM =  "/manager/viewRoom.jsp";
    String MANAGER_ADD_ROOM =  "/manager/addRoom.jsp";
    String MANAGER_EDIT_ROOM =  "/manager/editRoom.jsp";
    String EDIT_ROOM = "/manager/?command=rooms";
    String MANAGER_VIEW_BOOKINGS = "/manager/viewBooking.jsp";

    String MANAGER_VIEW_APPLICATIONS = "/manager/viewApplications.jsp";
    String MANAGER_REVIEW_APPLICATIONS = "/manager/reviewApplications.jsp";

    //admin
    String ADMIN_HOME = "/admin/home.jsp";


    String ERROR = "/errorPage.jsp";

    String EDIT_ROOM_PAGE = "/manager/editRoom.jsp";
    String MANAGER_VIEW_REQUEST = "/manager/viewRequest.jsp";;
}
