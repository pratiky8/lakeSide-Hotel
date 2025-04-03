package com.athina.lakesidehotel.service;

import com.athina.lakesidehotel.model.BookedRoom;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Override
    public List<BookedRoom> getAllBookingsByRoomId(long roomId) {
        return null;
    }
}
