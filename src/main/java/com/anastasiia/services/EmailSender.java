package com.anastasiia.services;

import com.anastasiia.dto.RoomDTO;
import com.anastasiia.dto.UserDTO;
import com.anastasiia.exceptions.ServiceException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Date;
import java.util.Calendar;
import java.util.Properties;


public class EmailSender {

    private final static Logger logger = Logger.getLogger(EmailSender.class);
    private static final String PATH_CONFIG = "mail.properties";
    private static final String USER = "mail.user";
    private static final String PASSWORD = "mail.password";

    public void send(String sendTo, String subject, String messageToSend) throws ServiceException {
        Properties properties = loadProperties(PATH_CONFIG);
        final String user = properties.getProperty(USER);
        final String password = properties.getProperty(PASSWORD);

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(sendTo));
            message.setSubject(subject);
            message.setContent(messageToSend, "text/html");
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            logger.error("Error sending email!", e);
            throw new ServiceException(e);
        }
    }

    public void sendWelcomeLetter(String sendTo) throws ServiceException {
        send(sendTo, "Welcome to Hotel", getWelcomeContent());
    }

    public void sendSuccessBooking(UserDTO userDTO, int bookingId, String totalCost, RoomDTO roomDTO, Date checkIn, Date checkOut) throws ServiceException {
        String bookingLetter = getBookingContent()
                .replaceFirst("clientFirstName", String.valueOf(userDTO.getFirstName()))
                .replaceFirst("clientLastName", String.valueOf(userDTO.getLastName()))
                .replaceFirst("clientEmailAddress", String.valueOf(userDTO.getEmail()))
                .replaceFirst("bookingIdParam", String.valueOf(bookingId))
                .replaceFirst("totalCostParam", totalCost)
                .replaceFirst("dateOfPayment", String.valueOf(new Date(Calendar.getInstance().getTime().getTime())))
                .replaceFirst("checkInParam", String.valueOf(checkIn))
                .replaceFirst("checkOutParam", String.valueOf(checkOut))
                .replaceFirst("roomIdParam", String.valueOf(roomDTO.getId()))
                .replaceFirst("roomClassOfRoomParam", String.valueOf(roomDTO.getClassOfRoom()))
                .replaceFirst("roomNumberOfPersonParam", String.valueOf(roomDTO.getNumberOfPerson()));
        send(userDTO.getEmail(), "Booking success", bookingLetter);
    }

    private Properties loadProperties(String fileName) {
        Properties properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.log(Level.FATAL, "Reading file error", e);
            throw new RuntimeException(e);
        }
        return properties;
    }

    private String getWelcomeContent(){
            return readFromFile("/email/welcome_letter.html");
    }

    private String getBookingContent(){
            return readFromFile("/email/booking_success_letter.html");
    }

    private String readFromFile(String fileName){
        StringBuilder contentBuilder = new StringBuilder();
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            URL resource = classLoader.getResource(fileName);
            File file  = new File(resource.toURI());
            BufferedReader in = new BufferedReader(new FileReader(file));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }catch (IOException e) {
            logger.log(Level.FATAL, "Reading file error", e);
        }
        return contentBuilder.toString();
    }
}
