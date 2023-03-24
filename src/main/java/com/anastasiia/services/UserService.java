package com.anastasiia.services;

import com.anastasiia.dao.DBManager;
import com.anastasiia.dao.UserDAO;
import com.anastasiia.dto.UserDTO;
import com.anastasiia.entity.User;
import com.anastasiia.exceptions.DAOException;
import com.anastasiia.exceptions.InvalidUserException;
import com.anastasiia.exceptions.ServiceException;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Role;
import org.apache.log4j.Logger;

import javax.sql.DataSource;

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
     * @return <b>true</b> if user with this email doesn't exist
     */
    public boolean validateUserByEmail(String email) throws InvalidUserException {
        try {
            user = userDAO.findUserByEmail(email);
        } catch (DAOException e) {
            log.error("DAOException was caught. Cause: " +e.getMessage());
            throw new InvalidUserException(e.getMessage());
        }
        if (user != null){
            throw new InvalidUserException(JspAttributes.EMAIL_EXISTS);
        }else return true;
    }

    /**
     * method validate user by email and password fields matching with database
     * @param email user email
     * @param password user password
     * @return <b>true</b> if user with this email is exist and password is correct
     */
    public UserDTO validateUserByEmailAndPassword(String email, String password) throws InvalidUserException {
        try {
            user = userDAO.findUserByEmail(email);
        } catch (DAOException e) {
            log.error("DAOException was caught. Cause: " +e.getMessage());
            throw new InvalidUserException(e);
        }
        if(user !=null && PasswordEncoder.encode(password).equals(user.getPassword())) {
            return entityToDTO(user);
        }else throw new InvalidUserException(JspAttributes.WRONG_EMAIL_OR_PASSWORD);
    }

    /**
     * Method gets Role object by user email
     * @param email user email
     * @return Role object (<code>MANGER, CLIENT, UNREGISTERED</code>)
     */
    public Role getRole(String email) throws ServiceException {
        try {
            user = userDAO.findUserByEmail(email);
        } catch (DAOException e) {
            log.error("DAOException was caught. Cause: " +e.getMessage());
            throw new ServiceException(e);
        }
        return user.getRole();
    }

    /**
     * Method gets User by user email identity
     * @param id user identity
     * @return UserDTO object
     */
    public UserDTO getUser(int id) throws ServiceException {
        try {
            user = userDAO.findUserById(id);
        } catch (DAOException e) {
            log.error("DAOException was caught. Cause: " +e.getMessage());
            throw new ServiceException(e);
        }
        return entityToDTO(user);
    }

    /**
     * Method inserts new User
     * @param user User entity object
     */
    public boolean insertUser(User user) throws ServiceException {
        try {
            userDAO.insertUser(user);
        } catch (DAOException e) {
            log.error("DAOException was caught. Cause: " +e.getMessage());
            throw new ServiceException(e);
        }
        return true;
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
    public User dtoToEntity(UserDTO userDTO) throws ServiceException {
        try {
            return userDAO.findUserById(userDTO.getId());
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public void editUserProfile(String firstName, String lastName, String email, UserDTO userDTO) throws ServiceException {
        try {
            if(!userDTO.getEmail().equals(email)){
                validateUserByEmail(email);
            }
            userDAO.updateUser(firstName, lastName, email, userDTO.getId());
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
    public void editPassword(String password, int id) throws ServiceException {
        try {
            Validation.validPassword(password);
            userDAO.updatePassword(PasswordEncoder.encode(password), id);
        } catch (DAOException e){
            throw new ServiceException(e);
        }
    }

}
