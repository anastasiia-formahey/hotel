package com.anastasiia.dto;

import com.anastasiia.entity.User;
import com.anastasiia.services.UserService;
import com.anastasiia.utils.Role;
import org.apache.log4j.Logger;

public class UserDTO extends User {

    private static final Logger log = Logger.getLogger(UserDTO.class);
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;

    public UserDTO(){}
    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Role getRole() {
        return role;
    }

    @Override
    public void setRole(Role role) {
        this.role = role;
    }
    public UserDTO entityToDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        log.debug(userDTO);
        return userDTO;
    }
    public User dtoToEntity(){
       return new UserService().getUser(this.getEmail());
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
