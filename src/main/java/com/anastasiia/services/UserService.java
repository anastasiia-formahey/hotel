package com.anastasiia.services;

import com.anastasiia.dao.DBManager;
import com.anastasiia.dao.UserDAO;
import com.anastasiia.dto.UserDTO;
import com.anastasiia.entity.User;
import com.anastasiia.utils.Role;
import org.apache.log4j.Logger;

import java.sql.SQLException;

public class UserService {
    private static final Logger log = Logger.getLogger(UserService.class);
    private final UserDAO userDAO = new UserDAO(DBManager.getInstance());
    User user;
    public boolean validateUserByEmail(String email){
        return userDAO.findUserByEmail(email) != null;
    }

    public boolean validateUserByEmailAndPassword(String email, String password){
        user = userDAO.findUserByEmail(email);
        if(user !=null) {
            return PasswordEncoder.encode(password).equals(user.getPassword());

        }else return false;
    }

    public Role getRole(String email) {
        user = userDAO.findUserByEmail(email);
        return user.getRole();
    }

    public Object getId(String email) {
        user = userDAO.findUserByEmail(email);
        return user.getId();
    }

    public UserDTO getUser(String email) {
        user = userDAO.findUserByEmail(email);
        return new UserDTO().entityToDTO(user);
    }
    public UserDTO getUser(int id) {
        try {
            user = userDAO.findUserById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new UserDTO().entityToDTO(user);
    }

    public void insertUser(User user) {
        try {
            userDAO.insertUser(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
