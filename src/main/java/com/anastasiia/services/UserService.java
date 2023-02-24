package com.anastasiia.services;

import com.anastasiia.dao.DBManager;
import com.anastasiia.dao.UserDAO;
import com.anastasiia.dto.UserDTO;
import com.anastasiia.entity.User;
import com.anastasiia.utils.Role;
import org.apache.log4j.Logger;

import java.sql.SQLException;

/**
 * UserService - class mediates communication between a controller and DAO/repository layer
 */
public class UserService {
    private static final Logger log = Logger.getLogger(UserService.class);
    private final UserDAO userDAO = new UserDAO(DBManager.getInstance());
    User user;

    /**
     * method validate user email searching user in database
     * @param email user email
     * @return <b>true</b> if user with this email is exist
     */
    public boolean validateUserByEmail(String email){
        return userDAO.findUserByEmail(email) != null;
    }

    /**
     * method validate user by email and password fields matching with database
     * @param email user email
     * @param password user password
     * @return <b>true</b> if user with this email is exist and password is correct
     */
    public boolean validateUserByEmailAndPassword(String email, String password){
        user = userDAO.findUserByEmail(email);
        if(user !=null) {
            return PasswordEncoder.encode(password).equals(user.getPassword());

        }else return false;
    }

    /**
     * Method gets Role object by user email
     * @param email user email
     * @return Role object (<code>MANGER, CLIENT, UNREGISTERED</code>)
     */
    public Role getRole(String email) {
        user = userDAO.findUserByEmail(email);
        return user.getRole();
    }

    /**
     * Method gets User by user email
     * @param email user email
     * @return UserDTO object
     */
    public UserDTO getUser(String email) {
        user = userDAO.findUserByEmail(email);
        return entityToDTO(user);
    }
    /**
     * Method gets User by user emaiidentityl
     * @param id user identity
     * @return UserDTO object
     */
    public UserDTO getUser(int id) {
        try {
            user = userDAO.findUserById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return entityToDTO(user);
    }

    /**
     * Method inserts new User
     * @param user User entity object
     */
    public void insertUser(User user) {
        try {
            userDAO.insertUser(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method generates UserDTO object from User entity
     * @param user User entity
     * @return UserDTO object
     */
    public UserDTO entityToDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    /**
     * Method generates User entity from UserDTO object
     * @param userDTO UserDTO object
     * @return User entity
     */
    public User dtoToEntity(UserDTO userDTO){
        return getUser(userDTO.getEmail());
    }
}
