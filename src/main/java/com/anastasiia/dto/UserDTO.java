package com.anastasiia.dto;

import com.anastasiia.entity.User;
import com.anastasiia.services.UserService;
import com.anastasiia.utils.Role;

public class UserDTO extends User {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;

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
        this.setId(user.getId());
        this.setFirstName(user.getFirstName());
        this.setLastName(user.getLastName());
        this.setEmail(user.getEmail());
        this.setRole(user.getRole());
        return this;
    }
    public User dtoToEntity(){
       return new UserService().getUser(this.getEmail());
    }

}
