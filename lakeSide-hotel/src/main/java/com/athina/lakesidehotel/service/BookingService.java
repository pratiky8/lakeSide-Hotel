package com.athina.lakesidehotel.service;

import com.athina.lakesidehotel.model.BookedRoom;
import org.springframework.stereotype.Service;

import java.util.List;


public interface BookingService {

    List<BookedRoom> getAllBookingsByRoomId(long roomId);
}
