package com.anastasiia.dao;

import com.anastasiia.entity.Booking;
import com.anastasiia.entity.EntityMapper;
import com.anastasiia.entity.Room;
import com.anastasiia.entity.User;
import com.anastasiia.utils.*;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    private static final Logger log = Logger.getLogger(BookingDAO.class);
    private DataSource dataSource;
    public BookingDAO(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public boolean insertBooking(List<Booking> bookings) throws SQLException {
        log.debug("Method starts");
        Room room = null;
        boolean isSuccess = false;
        ResultSet resultSet;

        try (Connection connection = dataSource.getConnection();
        PreparedStatement  preparedStatement1 = connection.prepareStatement(SqlQuery.SQL_SELECT_ROOM_FROM_OCCUPANCY_OF_ROOM);
        PreparedStatement  preparedStatement = connection.prepareStatement(SqlQuery.SQL_INSERT_BOOKING);
        ){
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            for(Booking booking: bookings) {

                preparedStatement1.setInt(1, booking.getRoomId());
                preparedStatement1.setDate(2, booking.getCheckInDate());
                preparedStatement1.setInt(3, booking.getRoomId());
                preparedStatement1.setDate(4, booking.getCheckOutDate());
                resultSet = preparedStatement1.executeQuery();
                if (resultSet.next()){
                    room = new Room();
                    room.setId(resultSet.getInt("id"));
                    room.setNumberOfPerson(resultSet.getInt("number_of_person"));
                    room.setPrice(resultSet.getDouble("price"));
                    room.setClassOfRoom(ClassOfRoom.valueOf(resultSet.getString("class_of_room")));
                    room.setImage(resultSet.getString("image"));
                    log.trace(room.toString());
                }
                if (room == null) {
                    preparedStatement.setInt(1, booking.getRoomId());
                    preparedStatement.setInt(2, booking.getClientId());
                    preparedStatement.setDate(3, booking.getCheckInDate());
                    preparedStatement.setDate(4, booking.getCheckOutDate());
                    preparedStatement.setDouble(5, booking.getPrice());
                    preparedStatement.setDate(6, booking.getDateOfBooking());
                    preparedStatement.setString(7, booking.getStatusOfBooking().name());
                    preparedStatement.executeUpdate();
                    isSuccess = true;
                    connection.commit();
                }else {
                    log.trace(room.toString());
                    log.trace("Booking already exists");
                    isSuccess = false;
                }
            }
        }catch (SQLException ex){
            log.error("Cannot execute the query ==> " + ex);
            log.trace("Close connection with DBManager");
            throw new SQLException(ex);
        }finally {
            log.trace("Close connection with DBManager");
        }
        log.debug("Method finished");
        return isSuccess;
    }

    public boolean insertBooking(List<Booking> bookings, boolean isConfirm) throws SQLException {
        log.debug("Method starts");
       boolean isSuccess = false;

        try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.SQL_INSERT_BOOKING);
        ){
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            for(Booking booking: bookings) {
                     preparedStatement.setInt(1, booking.getRoomId());
                    preparedStatement.setInt(2, booking.getClientId());
                    preparedStatement.setDate(3, booking.getCheckInDate());
                    preparedStatement.setDate(4, booking.getCheckOutDate());
                    preparedStatement.setDouble(5, booking.getPrice());
                    preparedStatement.setDate(6, booking.getDateOfBooking());
                    preparedStatement.setString(7, booking.getStatusOfBooking().name());
                    preparedStatement.executeUpdate();
            }
            isSuccess = true;
            connection.commit();
        }catch (SQLException ex){
            log.error("Cannot execute the query ==> " + ex);
            log.trace("Close connection with DBManager");
            throw new SQLException(ex);
        }finally {
            log.trace("Close connection with DBManager");
        }
        log.debug("Method finished");
        return isSuccess;
    }

    public void updateStatus(int bookingId, Status status) throws SQLException {
        log.debug("Method starts");
        try(Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.SQL_UPDATE_BOOKING_STATUS);
                ) {
             preparedStatement.setString(1,status.name());
            preparedStatement.setInt(2,bookingId);

            preparedStatement.executeUpdate();
            log.trace("Query execution => " + preparedStatement);
            connection.commit();
        }catch (SQLException ex){
            log.error("Cannot execute the query ==> " + ex);
            log.trace("Close connection with DBManager");
            throw new SQLException(ex);
        }finally {
            log.trace("Close connection with DBManager");
        }
        log.debug("Method finished");
    }

    public List<Booking> selectAll() throws SQLException {
        ArrayList<Booking> listOfBookings = new ArrayList<>();
        ResultSet resultSet;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement  preparedStatement = connection.prepareStatement(SqlQuery.SQL_SELECT_ALL_BOOKINGS);){

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                listOfBookings.add(
                        new BookingMapper().mapRow(resultSet));
            }
        }catch (SQLException ex){
            log.error("Cannot execute the query ==> " + ex);
            log.trace("Close connection with DBManager");
            throw  new SQLException(ex);
        }finally {
            log.trace("Close connection with DBManager");
        }
        return listOfBookings;
    }
    public List<Booking> selectAll(int currentPage, int amount, String orderBy) {
        ArrayList<Booking> listOfBookings = new ArrayList<>();
        ResultSet resultSet;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    SqlQuery.SQL_SELECT_ALL_BOOKINGS + " ORDER BY "+ orderBy + " LIMIT "+ currentPage +"," + amount);
        ){
            log.debug(preparedStatement.executeQuery());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                listOfBookings.add(
                        new BookingMapper().mapRow(resultSet));
            }
        }catch (SQLException ex){
            log.error("Cannot execute the query ==> " + ex);
            log.trace("Close connection with DBManager");
        }finally {
           log.trace("Close connection with DBManager");
        }
        return listOfBookings;
    }
    public List<Booking> selectAllByUserId(int userId) {
        ArrayList<Booking> listOfBookings = new ArrayList<>();
        ResultSet resultSet;
        try(Connection connection = dataSource.getConnection();
        PreparedStatement  preparedStatement = connection.prepareStatement(
                SqlQuery.SQL_SELECT_ALL_BOOKINGS_BY_USER_ID);){
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                listOfBookings.add(
                        new BookingMapper().mapRow(resultSet));
            }
        }catch (SQLException ex){
            log.error("Cannot execute the query ==> " + ex);
            log.trace("Close connection with DBManager");
        }finally {
            log.trace("Close connection with DBManager");
        }
        return listOfBookings;
    }
    public List<Booking> selectAllByUserId(int currentPage, int amount, String orderBy,int userId) {
        ArrayList<Booking> listOfBookings = new ArrayList<>();
        ResultSet resultSet;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement  preparedStatement = connection.prepareStatement(
                    SqlQuery.SQL_SELECT_ALL_BOOKINGS_BY_USER_ID + " ORDER BY "+ orderBy + " LIMIT "+ currentPage +"," + amount);
        ){
           preparedStatement.setInt(1, userId);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                listOfBookings.add(
                        new BookingMapper().mapRow(resultSet));
            }
        }catch (SQLException ex){
            log.error("Cannot execute the query ==> " + ex);
            log.trace("Close connection with DBManager");
        }finally {
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
