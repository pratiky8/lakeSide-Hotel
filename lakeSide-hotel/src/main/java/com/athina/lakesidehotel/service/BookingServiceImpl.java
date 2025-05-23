package com.athina.lakesidehotel.service;

import com.athina.lakesidehotel.exception.InvalidBookingRequestException;
import com.athina.lakesidehotel.model.BookedRoom;
import com.athina.lakesidehotel.model.Room;
import com.athina.lakesidehotel.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RoomService roomService;

    @Override
    public List<BookedRoom> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public List<BookedRoom> getAllBookingsByRoomId(long roomId) {

        return bookingRepository.findByRoomId(roomId);
    }

    @Override
    public void cancelBooking(Long bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    @Override
    public String saveBooking(Long roomId, BookedRoom bookingRequest) {
       if(bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())) {
           throw new InvalidBookingRequestException("Check-in date must come before check-out date");
       }
       Room room = roomService.getRoomBYId(roomId).get();
       List<BookedRoom> existingBookings = room.getBookings();
       boolean roomIsAvailable = roomIsAvailable(bookingRequest, existingBookings);
        if(roomIsAvailable) {
            room.addBooking(bookingRequest);
            bookingRepository.save(bookingRequest);
        }else{
            throw new InvalidBookingRequestException("Sorry, This room is not available for the selected dates");
        }
       return bookingRequest.getBookingConfirmationCode();
    }



    @Override
    public BookedRoom findByBookingConfirmationCode(String confirmationCode) {
        return bookingRepository.findByBookingConfirmationCode(confirmationCode);
    }


    private boolean roomIsAvailable(BookedRoom bookingRequest, List<BookedRoom> existingBookings) {
    return existingBookings.stream()
            .noneMatch(existingBooking->
                    bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())

                    || bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
                    || bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
                    && bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate())
                    || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())
                    && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))

                    || bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())
                    && bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate())

                    || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                    && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))

                    || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                    && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))


                    )
            ;
    }


}
