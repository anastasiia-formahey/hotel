package com.anastasiia.dto;

import com.anastasiia.entity.User;
import com.anastasiia.services.UserService;
import com.anastasiia.utils.Role;
import org.apache.log4j.Logger;

import java.io.Serializable;

/**
 * <code>UserDTO</code> - class implements data transfer object for <code>User entity</code>
 */
public class UserDTO implements Serializable {

    private static final Logger log = Logger.getLogger(UserDTO.class);
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;

    public UserDTO(){}

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public Role getRole() {
        return role;
    }


    public void setRole(Role role) {
        this.role = role;
    }


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
