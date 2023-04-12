package com.anastasiia.services.impl;

import com.anastasiia.dao.DBManager;
import com.anastasiia.dao.impl.OccupancyOfRoomDAO;
import com.anastasiia.dto.OccupancyOfRoomDTO;
import com.anastasiia.entity.OccupancyOfRoom;
import com.anastasiia.entity.User;
import com.anastasiia.exceptions.DAOException;
import com.anastasiia.exceptions.ServiceException;
import com.anastasiia.services.IOccupancyOfRoomService;
import com.anastasiia.utils.Status;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.util.Map;
/**
 * OccupancyOfRoomService - class mediates communication between a controller and DAO/repository layer
 */
public class OccupancyOfRoomService implements IOccupancyOfRoomService {
    private static final Logger log = Logger.getLogger(OccupancyOfRoomService.class);
    private final OccupancyOfRoomDAO occupancyOfRoomDAO = new OccupancyOfRoomDAO(DBManager.getInstance());

    private final UserService userService = new UserService();


    /**
     *
     * @param roomId <code>Room</code> identity
     * @param clientId <code>User</code> identity
     * @param checkIn the date checking in
     * @param checkOut the date checking out
     * @param status <code>Status</code> object (<tt>NOT_CONFIRMED</tt>, <tt>BOOKED</tt>, <tt>PAID</tt>,
     *     <tt>BUSY</tt>, <tt>CANCELED</tt>)
     */
    public void insertOccupancyOfRoom(int roomId, int clientId, Date checkIn, Date checkOut, Status status){
        try {
            if(occupancyOfRoomDAO
                    .isExist(roomId, clientId, checkIn, checkOut)){
                occupancyOfRoomDAO.updateStatus(roomId, status, checkIn,checkOut);
            }else {
                occupancyOfRoomDAO.insertOccupancyOfRoom(roomId, clientId, checkIn, checkOut, status);
            }
        } catch (DAOException e) {
            log.error("DAOException was caught. Cause : "+ e);
        }
    }

    /**
     * Method selects all OccupancyOfRoom entity object by specific <code>Room</code>
     * @param roomId <code>Room</code> identity
     * @param date the date to select
     * @return OccupancyOfRoomDTO object
     */
       public OccupancyOfRoomDTO getOccupancyOfRoomByRoomId(int roomId, Date date) throws ServiceException {
           Map<OccupancyOfRoom, User> occupancyOfRoomUserMap;
           try {
               occupancyOfRoomUserMap = occupancyOfRoomDAO.selectByRoomId(roomId,date);
               OccupancyOfRoomDTO occupancyOfRoomDTO = null;
               for (Map.Entry<OccupancyOfRoom, User> entry: occupancyOfRoomUserMap.entrySet()) {
                   OccupancyOfRoom occupancyOfRoom = entry.getKey();
                   User user = entry.getValue();
                   occupancyOfRoomDTO = new OccupancyOfRoomDTO(
                           occupancyOfRoom.getRoomId(),
                           userService.entityToDTO(user),
                           occupancyOfRoom.getCheckInDate(),
                           occupancyOfRoom.getCheckOutDate(),
                           occupancyOfRoom.getStatus()
                   );
               }
               return occupancyOfRoomDTO;
           } catch (DAOException e) {
               throw new ServiceException(e);
           }
       }

    /**
     * Method updates status an occupancy of room by <code>Room</code> identity and dates checking in/out
     * @param roomId <code>Room</code> identity
     * @param status <code>Status</code> object
     * @param checkIn the date checking in
     * @param checkOut the date checking out
     */
       public void updateStatus(int roomId, Status status, Date checkIn, Date checkOut) throws ServiceException {
           try {
               occupancyOfRoomDAO.updateStatus(roomId, status, checkIn, checkOut);
           } catch (DAOException e) {
               throw new ServiceException(e);
           }
       }
}
