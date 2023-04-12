package com.anastasiia.services;

import com.anastasiia.dto.UserDTO;
import com.anastasiia.entity.User;
import com.anastasiia.exceptions.InvalidUserException;
import com.anastasiia.exceptions.ServiceException;
import com.anastasiia.utils.Role;

/**
 * The interface implements behavior for UserService.
 */
public interface IUserService {
    /**
     * Method validate user email searching user in database
     * @param email user email
     * @return <b>true</b> if user with this email doesn't exist
     */
    boolean validateUserByEmail(String email) throws InvalidUserException;
    /**
     * method validate user by email and password fields matching with database
     * @param email user email
     * @param password user password
     * @return <b>true</b> if user with this email is exist and password is correct
     */
    UserDTO validateUserByEmailAndPassword(String email, String password) throws InvalidUserException;

    /**
     * Method gets Role object by user email
     * @param email user email
     * @return Role object (<code>MANGER, CLIENT, UNREGISTERED</code>)
     */
    Role getRole(String email) throws ServiceException;

    /**
     * Method gets User by user email identity
     * @param id user identity
     * @return UserDTO object
     */
    UserDTO getUser(int id) throws ServiceException;

    /**
     * Method inserts new User
     * @param user User entity object
     */
    boolean insertUser(User user) throws ServiceException;

    /**
     * Method generates UserDTO object from User entity
     * @param user User entity
     * @return UserDTO object
     */
    UserDTO entityToDTO(User user);

    /**
     * Method generates User entity from UserDTO object
     * @param userDTO UserDTO object
     * @return User entity
     */
    User dtoToEntity(UserDTO userDTO) throws ServiceException;
}
