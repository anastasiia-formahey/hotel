package com.anastasiia.dao;

import com.anastasiia.entity.Booking;
import com.anastasiia.entity.EntityMapper;
import com.anastasiia.entity.Room;
import com.anastasiia.utils.ClassOfRoom;
import com.anastasiia.utils.Status;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *<Code>BookingDAO</Code> - class implements data access object for <code>Booking</code> entity
 * */
public class BookingDAO {

    private static final Logger log = Logger.getLogger(BookingDAO.class);
    private final DataSource dataSource;
    public BookingDAO(DataSource dataSource){
        this.dataSource = dataSource;
    }

    /**
     * Method inserts new object into table
     * @param bookings list of objects <code>Booking.class</code> to insert
     * @return true - if objects were inserted
     * @throws SQLException
     */
    public boolean insertBooking(List<Booking> bookings) throws SQLException {
        log.debug("Method starts");
        Room room = null;
        boolean isSuccess = false;
        ResultSet resultSet;
        try (Connection connection = dataSource.getConnection();
        PreparedStatement  preparedStatement1 = connection.prepareStatement(SqlQuery.SQL_SELECT_ROOM_FROM_OCCUPANCY_OF_ROOM);
        PreparedStatement  preparedStatement = connection.prepareStatement(SqlQuery.SQL_INSERT_BOOKING)
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
                    log.trace("Booking already exists");
                    isSuccess = false;
                }
            }
        }catch (SQLException ex){
            log.error("Cannot execute the query ==> " + ex);
            throw new SQLException(ex);
        }
        log.debug("Method finished");
        return isSuccess;
    }

    /**
     * Method inserts new object into table
     * @param bookings list of objects <code>Booking.class</code> to insert
     * @param isConfirm <b>true</b> if inserted bookings are confirmation of request on application
     * @return true - if objects were inserted
     * @throws SQLException
     */
    public boolean insertBooking(List<Booking> bookings, boolean isConfirm) throws SQLException {
        log.debug("Method starts");
       boolean isSuccess = false;
        try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.SQL_INSERT_BOOKING)
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
            throw new SQLException(ex);
        }
        log.debug("Method finished");
        return isSuccess;
    }

    /**
     * Method updates status of a record in table
     * @param bookingId booking identity
     * @param status booking status to update
     * @throws SQLException
     */
    public void updateStatus(int bookingId, Status status) throws SQLException {
        log.debug("Method starts");
        try(Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.SQL_UPDATE_BOOKING_STATUS)
                ) {
             preparedStatement.setString(1,status.name());
            preparedStatement.setInt(2,bookingId);
            preparedStatement.executeUpdate();
            connection.commit();
        }catch (SQLException ex){
            log.error("Cannot execute the query ==> " + ex);
            throw new SQLException(ex);
        }
        log.debug("Method finished");
    }

    /**
     * Method selects all records from the table
     * @return list of objects <code>Booking.class</code>
     * @throws SQLException
     */
    public List<Booking> selectAll() throws SQLException {
        log.debug("Method starts");
        ArrayList<Booking> listOfBookings = new ArrayList<>();
        ResultSet resultSet;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement  preparedStatement = connection.prepareStatement(SqlQuery.SQL_SELECT_ALL_BOOKINGS)){
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                listOfBookings.add(
                        new BookingMapper().mapRow(resultSet));
            }
        }catch (SQLException ex){
            log.error("Cannot execute the query ==> " + ex);
            throw  new SQLException(ex);
        }
        log.debug("Method finished");
        return listOfBookings;
    }

    /**
     * Method selects all records from the table with limits. This method uses to implement a pagination
     * @param currentPage start record to selecting
     * @param amount number of records to select
     * @param orderBy parameter for sorting records
     * @return list of objects <code>Booking.class</code> with certain number of records
     */
    public List<Booking> selectAll(int currentPage, int amount, String orderBy) {
        log.debug("Method starts");
        ArrayList<Booking> listOfBookings = new ArrayList<>();
        ResultSet resultSet;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    SqlQuery.SQL_SELECT_ALL_BOOKINGS + " ORDER BY "+ orderBy + " LIMIT "+ currentPage +"," + amount);
        ){
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                listOfBookings.add(
                        new BookingMapper().mapRow(resultSet));
            }
        }catch (SQLException ex){
            log.error("Cannot execute the query ==> " + ex);
        }log.debug("Method finished");
        return listOfBookings;
    }

    /**
     * Method selects all records from the table by user identity
     * @param userId user identity
     * @return list of objects <code>Booking.class</code> by specific user
     */
    public List<Booking> selectAllByUserId(int userId) {
        log.debug("Method starts");
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
        }log.debug("Method finished");
        return listOfBookings;
    }

    /**
     * Method selects all records from the table with limits. This method uses to implement a pagination
     * @param currentPage start record to selecting
     * @param amount number of records to select
     * @param orderBy parameter for sorting records
     * @param userId user identity
     * @return list of objects <code>Booking.class</code> with certain number of records by specific user
     */
    public List<Booking> selectAllByUserId(int currentPage, int amount, String orderBy,int userId) {
        log.debug("Method starts");
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
        }
        log.debug("Method finished");
        return listOfBookings;
    }
    /**
     * <Code>BookingMapper</Code> class that help to create object of <Code>Booking.class</Code>
     * from <code>ResultSet</code>
     *
     */
    private static class BookingMapper implements EntityMapper<Booking> {
        /**
         * Method creates object of <code>Booking</code> from <code>ResultSet</code>
         * @param resultSet <code>ResultSet</code> object
         * @return <code>Booking</code> object
         */
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
                return booking;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
