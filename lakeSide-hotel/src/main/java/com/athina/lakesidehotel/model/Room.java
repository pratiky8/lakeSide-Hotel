package com.athina.lakesidehotel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String  roomType;
    private BigDecimal roomPrice;
    private boolean isBooked=false;
    @Lob                                                                                    //Tells JPA/Hibernate that this field will store a large object like an image, video, or large text
    private Blob photo;                                                                           //(Binary Larg Object)A binary data type used to store images, videos, or other large binary files in a database.

    @OneToMany(mappedBy ="room",fetch = FetchType.LAZY, cascade = CascadeType.ALL)//  Why use LAZY? ->not fetching all bookings unless needed.
    private List<BookedRoom> bookings;                                                   //CascadeType.ALL means any change to Room affects its BookedRoom records.
                                                                                            //Without mappedBy, JPA would create an extra join table, which we don’t want.
    public Room() {
        this.bookings = new ArrayList<>();                                          //we write bcz to avoid null pointer axception
    }

    public void addBooking(BookedRoom booking){
        if(booking==null){
            bookings=new ArrayList<>();                                             //Makes sure the list is not empty
        }
        bookings.add(booking);                                                      //Adds the new booking to the room
        booking.setRoom(this);                                                      //Ensures the booking knows which room it belongs to
        isBooked=true;                                                              //	Marks the room as occupied
        String bookingCode= RandomStringUtils.randomNumeric(10);                //Generates a 10-digit booking confirmation code
        booking.setBookingConfirmationCode(bookingCode);                                //Stores the booking code in the BookedRoom object
    }
}
