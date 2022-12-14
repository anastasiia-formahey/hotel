package com.anastasiia.dao;

import com.anastasiia.entity.Booking;
import com.anastasiia.entity.EntityMapper;
import com.anastasiia.entity.Room;
import com.anastasiia.entity.User;
import com.anastasiia.utils.Fields;
import com.anastasiia.utils.Role;
import com.anastasiia.utils.SqlQuery;
import com.anastasiia.utils.Status;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    private static final Logger log = Logger.getLogger(BookingDAO.class);

    private static BookingDAO instance = null;

    private BookingDAO(){}

    public static synchronized BookingDAO getInstance() {
        if(instance ==null){
            instance = new BookingDAO();
        }
        return instance;
    }

    public void insertBooking(Booking booking){
        log.debug("Method starts");

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;

        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SqlQuery.SQL_INSERT_BOOKING, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1,booking.getRoomId());
            preparedStatement.setInt(2,booking.getClientId());
            preparedStatement.setDate(3,booking.getCheckInDate());
            preparedStatement.setDate(4,booking.getCheckOutDate());
            preparedStatement.setDouble(5,booking.getPrice());
            preparedStatement.setDate(6,booking.getDateOfBooking());
            preparedStatement.setString(7,booking.getStatusOfBooking().name());
            preparedStatement.executeUpdate();
            log.trace("Query execution => " + preparedStatement);
            resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                booking.setId(resultSet.getInt(1));
            }
            resultSet.close();
        }catch (SQLException ex){
            DBManager.getInstance().rollbackAndClose(connection);
            log.error("Cannot execute the query ==> " + ex);
            log.trace("Close connection with DBManager");
        }finally {
            try {
                assert preparedStatement != null;
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBManager.getInstance().commitAndClose(connection);
            log.trace("Close connection with DBManager");
        }
        log.debug("Method finished");
    }


    public void updateStatus(Booking booking) {
        log.debug("Method starts");

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SqlQuery.SQL_UPDATE_BOOKING_STATUS);

            preparedStatement.setString(1,booking.getStatusOfBooking().name());
            preparedStatement.setInt(2,booking.getId());

            preparedStatement.executeUpdate();
            log.trace("Query execution => " + preparedStatement);

        }catch (SQLException ex){
            DBManager.getInstance().rollbackAndClose(connection);
            log.error("Cannot execute the query ==> " + ex);
            log.trace("Close connection with DBManager");
        }finally {
            try {
                assert preparedStatement != null;
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBManager.getInstance().commitAndClose(connection);
            log.trace("Close connection with DBManager");
        }
        log.debug("Method finished");
    }

    public List<Booking> selectAll() {
        ArrayList<Booking> listOfBookings = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SqlQuery.SQL_SELECT_ALL_BOOKINGS);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                listOfBookings.add(
                        new BookingMapper().mapRow(resultSet));
            }
        }catch (SQLException ex){
            DBManager.getInstance().rollbackAndClose(connection);
            log.error("Cannot execute the query ==> " + ex);
            log.trace("Close connection with DBManager");
        }finally {
            try {
                assert preparedStatement != null;
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBManager.getInstance().commitAndClose(connection);
            log.trace("Close connection with DBManager");
        }
        return listOfBookings;
    }
    public List<Booking> selectAll(int currentPage, int amount, String orderBy) {
        ArrayList<Booking> listOfBookings = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(
                    SqlQuery.SQL_SELECT_ALL_BOOKINGS + " ORDER BY "+ orderBy + " LIMIT "+ currentPage +"," + amount);
            log.debug(preparedStatement.executeQuery());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                listOfBookings.add(
                        new BookingMapper().mapRow(resultSet));
            }
        }catch (SQLException ex){
            DBManager.getInstance().rollbackAndClose(connection);
            log.error("Cannot execute the query ==> " + ex);
            log.trace("Close connection with DBManager");
        }finally {
            try {
                assert preparedStatement != null;
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBManager.getInstance().commitAndClose(connection);
            log.trace("Close connection with DBManager");
        }
        return listOfBookings;
    }
    public List<Booking> selectAllByUserId(int userId) {
        ArrayList<Booking> listOfBookings = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(
                    SqlQuery.SQL_SELECT_ALL_BOOKINGS_BY_USER_ID);
            preparedStatement.setInt(1, userId);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                listOfBookings.add(
                        new BookingMapper().mapRow(resultSet));
            }
        }catch (SQLException ex){
            DBManager.getInstance().rollbackAndClose(connection);
            log.error("Cannot execute the query ==> " + ex);
            log.trace("Close connection with DBManager");
        }finally {
            try {
                assert preparedStatement != null;
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBManager.getInstance().commitAndClose(connection);
            log.trace("Close connection with DBManager");
        }
        return listOfBookings;
    }
    public List<Booking> selectAllByUserId(int currentPage, int amount, String orderBy,int userId) {
        ArrayList<Booking> listOfBookings = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(
                    SqlQuery.SQL_SELECT_ALL_BOOKINGS_BY_USER_ID + " ORDER BY "+ orderBy + " LIMIT "+ currentPage +"," + amount);
            preparedStatement.setInt(1, userId);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                listOfBookings.add(
                        new BookingMapper().mapRow(resultSet));
            }
        }catch (SQLException ex){
            DBManager.getInstance().rollbackAndClose(connection);
            log.error("Cannot execute the query ==> " + ex);
            log.trace("Close connection with DBManager");
        }finally {
            try {
                assert preparedStatement != null;
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBManager.getInstance().commitAndClose(connection);
            log.trace("Close connection with DBManager");
        }
        return listOfBookings;
    }

    public List<Booking> selectByRoom(Room room) {
        ArrayList<Booking> listOfBookings = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SqlQuery.SQL_SELECT_BOOKING_BY_ROOM);
            preparedStatement.setInt(1, room.getId());

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                listOfBookings.add(
                        new BookingMapper().mapRow(resultSet));
            }
        }catch (SQLException ex){
            DBManager.getInstance().rollbackAndClose(connection);
            log.error("Cannot execute the query ==> " + ex);
            log.trace("Close connection with DBManager");
        }finally {
            try {
                assert preparedStatement != null;
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBManager.getInstance().commitAndClose(connection);
            log.trace("Close connection with DBManager");
        }
        return listOfBookings;
    }

    private static class BookingMapper implements EntityMapper<Booking> {
        public Booking mapRow(ResultSet resultSet) {
            log.debug("Mapper starts");
            try{
                Booking booking = new Booking();
                booking.setId(resultSet.getInt(Fields.BOOKING_ID));
                booking.setRoomId(resultSet.getInt(Fields.BOOKING_ROOM_ID));
                booking.setClientId(resultSet.getInt(Fields.BOOKING_CLIENT_ID));
                booking.setCheckInDate(resultSet.getDate(Fields.BOOKING_CHECK_IN));
                booking.setCheckOutDate(resultSet.getDate(Fields.BOOKING_CHECK_OUT));
                booking.setPrice(resultSet.getDouble(Fields.BOOKING_PRICE));
                booking.setDateOfBooking(resultSet.getDate(Fields.BOOKING_DATE_OF_BOOKING));
                booking.setStatusOfBooking(Status.valueOf(resultSet.getString(Fields.BOOKING_STATUS)));
                log.debug("Mapper finished");
                log.debug(booking.toString());

                return booking;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
