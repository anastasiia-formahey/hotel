package com.anastasiia.dto;

import com.anastasiia.entity.Application;
import com.anastasiia.services.UserService;
import com.anastasiia.utils.ClassOfRoom;

public class ApplicationDTO {
    private int id;
    private UserDTO userDTO;
    private int numberOfGuests;
    private ClassOfRoom classOfRoom;
    private int lengthOfStay;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public ClassOfRoom getClassOfRoom() {
        return classOfRoom;
    }

    public void setClassOfRoom(ClassOfRoom classOfRoom) {
        this.classOfRoom = classOfRoom;
    }

    public int getLengthOfStay() {
        return lengthOfStay;
    }

    public void setLengthOfStay(int lengthOfStay) {
        this.lengthOfStay = lengthOfStay;
    }
    public Application dtoToEntity(){
        Application application = new Application();
        application.setId(this.id);
        application.setClientId(this.userDTO.getId());
        application.setNumberOfGuests(this.numberOfGuests);
        application.setLengthOfStay(this.lengthOfStay);
        return application;
    }
    public ApplicationDTO entityToDTO(Application application){
        ApplicationDTO applicationDTO = new ApplicationDTO();
        applicationDTO.setId(application.getId());
        applicationDTO.setUserDTO(new UserService().getUser(application.getClientId()));
        applicationDTO.setNumberOfGuests(application.getNumberOfGuests());
        applicationDTO.setLengthOfStay(application.getLengthOfStay());
        return applicationDTO;
    }
}
