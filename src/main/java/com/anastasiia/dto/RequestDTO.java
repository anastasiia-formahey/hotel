package com.anastasiia.dto;

import com.anastasiia.utils.Status;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
/**
 * <code>RequestDTO</code> - class implements data transfer object for <code>Request entity</code>
 */
public class RequestDTO implements Serializable {

    private static final long serialVersionUID = -2901690973440243570L;
    private int applicationId;
    private List<RequestElement> requestElements = new ArrayList<>();
    private Status statusOfRequest;

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public List<RequestElement> getRequestElements() {
        return requestElements;
    }

    public void setRequestElements(List<RequestElement> requestElements) {
        this.requestElements = requestElements;
    }
    public void setRequestElements(RoomDTO room, Date checkInDate, Date checkOutDate) {
        this.requestElements.add(new RequestElement(checkInDate, checkOutDate, room));
    }
    public void removeRequestElement(RequestElement requestElement) {
        this.requestElements.remove(requestElement);
    }

    public Status getStatusOfRequest() {
        return statusOfRequest;
    }

    public void setStatusOfRequest(Status statusOfRequest) {
        this.statusOfRequest = statusOfRequest;
    }

    @Override
    public String toString() {
        return "RequestDTO{" +
                "applicationId=" + applicationId +
                ", requestElements=" + requestElements.toString() +
                ", statusOfRequest=" + statusOfRequest +
                '}';
    }

    public static class RequestElement{
        private Date checkInDate;
        private Date checkOutDate;
        private RoomDTO room;

        public RequestElement(){}
        public RequestElement(Date checkInDate, Date checkOutDate, RoomDTO room) {
            this.checkInDate = checkInDate;
            this.checkOutDate = checkOutDate;
            this.room = room;
        }

        public Date getCheckInDate() {
            return checkInDate;
        }

        public Date getCheckOutDate() {
            return checkOutDate;
        }

        public RoomDTO getRoom() {
            return room;
        }

        @Override
        public String toString() {
            return "RequestElement{" +
                    "checkInDate=" + checkInDate +
                    ", checkOutDate=" + checkOutDate +
                    ", room=" + room +
                    '}';
        }
    }
}
