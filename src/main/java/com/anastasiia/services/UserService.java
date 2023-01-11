package com.anastasiia.services;

import com.anastasiia.dao.UserDAO;
import com.anastasiia.dto.UserDTO;
import com.anastasiia.entity.User;
import com.anastasiia.utils.Role;
import org.apache.log4j.Logger;

public class UserService {
    private static final Logger log = Logger.getLogger(UserService.class);
    User user;
    public boolean validateUserByEmail(String email){
        return UserDAO.findUserByEmail(email) != null;
    }

    public boolean validateUserByEmailAndPassword(String email, String password){
        user = UserDAO.findUserByEmail(email);
        if(user !=null) {
            return PasswordEncoder.encode(password).equals(user.getPassword());

        }else return false;
    }

    public Role getRole(String email) {
        user = UserDAO.findUserByEmail(email);
        return user.getRole();
    }

    public Object getId(String email) {
        user = UserDAO.findUserByEmail(email);
        return user.getId();
    }

    public UserDTO getUser(String email) {
        user = UserDAO.findUserByEmail(email);
        return new UserDTO().entityToDTO(user);
    }
    public UserDTO getUser(int id) {
        user = UserDAO.findUserById(id);
        return new UserDTO().entityToDTO(user);
    }

    public void insertUser(User user) {
        UserDAO.getInstance().insertUser(user);
    }
}
