package com.anastasiia.dto;

import com.anastasiia.entity.Application;
import com.anastasiia.services.UserService;
import com.anastasiia.utils.ClassOfRoom;
import com.anastasiia.utils.Status;

public class ApplicationDTO {
    private int id;
    private UserDTO userDTO;
    private int numberOfGuests;
    private ClassOfRoom classOfRoom;
    private int lengthOfStay;
    private Status status;


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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Application dtoToEntity(){
        Application application = new Application();
        application.setId(this.id);
        application.setClientId(this.userDTO.getId());
        application.setNumberOfGuests(this.numberOfGuests);
        application.setClassOfRoom(this.classOfRoom);
        application.setLengthOfStay(this.lengthOfStay);
        application.setStatus(this.getStatus());
        return application;
    }
    public ApplicationDTO entityToDTO(Application application){
        ApplicationDTO applicationDTO = new ApplicationDTO();
        applicationDTO.setId(application.getId());
        applicationDTO.setUserDTO(new UserService().getUser(application.getClientId()));
        applicationDTO.setClassOfRoom(application.getClassOfRoom());
        applicationDTO.setNumberOfGuests(application.getNumberOfGuests());
        applicationDTO.setLengthOfStay(application.getLengthOfStay());
        applicationDTO.setStatus(application.getStatus());
        return applicationDTO;
    }

    @Override
    public String toString() {
        return "ApplicationDTO{" +
                "id=" + id +
                ", userDTO=" + userDTO +
                ", numberOfGuests=" + numberOfGuests +
                ", classOfRoom=" + classOfRoom +
                ", lengthOfStay=" + lengthOfStay +
                ", status=" + status +
                '}';
    }
}
