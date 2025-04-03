package com.athina.lakesidehotel.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;

import java.math.BigDecimal;
import java.util.List;
//purpose of this response is to just data transfer object

@Data
@NoArgsConstructor
public class RoomResponse {

    private long id;
    private String  roomType;
    private BigDecimal roomPrice;
    private boolean isBooked;
    private String photo;
    private List<BookingResponse> bookings;//if it will not initialise it will show nullPointerException

    public RoomResponse(long id, String roomType, BigDecimal roomPrice) {
        this.id = id;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
    }

    public RoomResponse(long id, String roomType, BigDecimal roomPrice, boolean isBooked,
                        byte[] photoBytes/*, List<BookingResponse> booking*/) {
        this.id = id;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.isBooked = isBooked;
        //Base64 encoding converts byte[] into a string that can be safely transmitted over JSON or APIs.
        //and Base64 Storing images in JSON API responses
        //encodeBase64String(photoBytes)->Converts this byte array into a Base64 string.
        this.photo = photoBytes != null ? Base64.encodeBase64String(photoBytes) : null;
//        this.bookings = booking;
    }

}
