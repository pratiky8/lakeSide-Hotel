package com.athina.lakesidehotel.repository;

import com.athina.lakesidehotel.LakeSideHotelApplication;
import com.athina.lakesidehotel.model.BookedRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<BookedRoom, Long> {
    BookedRoom findByBookingConfirmationCode(String confirmationCode);

    List<BookedRoom> findByRoomId(Long roomId);


}
