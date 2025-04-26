package com.athina.lakesidehotel.controller;

import com.athina.lakesidehotel.exception.InternalServerException;
import com.athina.lakesidehotel.exception.PhotoRetrievalException;
import com.athina.lakesidehotel.exception.ResourceNotFoundException;
import com.athina.lakesidehotel.model.BookedRoom;
import com.athina.lakesidehotel.model.Room;
import com.athina.lakesidehotel.response.BookingResponse;
import com.athina.lakesidehotel.response.RoomResponse;
import com.athina.lakesidehotel.service.BookingService;
import com.athina.lakesidehotel.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
//used to create RESTful APIs.combination of @Controller(for handling web requests), @ResponseBody(to return JSON data instead of a view)
@RequiredArgsConstructor
@RequestMapping("/rooms")
//It generates a constructor with parameters for all final fields
public class RoomController {

    private final RoomService roomService;
    private final BookingService bookingService;

    //add new room
    @PostMapping("/add/new-room")
    public ResponseEntity<RoomResponse> addNewRoom(@RequestParam("photo") MultipartFile photo,                     //@RequestParam used in Spring Boot to extract query parameters or form data from an HTTP request.
                                                   @RequestParam("roomType") String roomType,
                                                   @RequestParam("roomPrice") BigDecimal roomPrice) throws SQLException, IOException {

        Room savedRoom = roomService.addNewRoom(photo, roomType, roomPrice);
        RoomResponse Response = new RoomResponse(savedRoom.getId(), savedRoom.getRoomType(), savedRoom.getRoomPrice());
        return ResponseEntity.ok(Response);

    }
//show room type
    @GetMapping("/room/types")   //take the data from data base and show them in frontend
    public List<String> getRoomTypes() {
        return roomService.getAllRoomTypes();
    }

    //get all room
    @GetMapping("/all-rooms") // data come from roomResponse package
    public ResponseEntity<List<RoomResponse>> getAllRooms() throws SQLException {
        List<Room> rooms = roomService.getAllRooms();
        List<RoomResponse> response = new ArrayList<>();
        for (Room room : rooms) {
            byte[] photoBytes = roomService.getRoomPhotoByRoomId(room.getId());
            if (photoBytes != null && photoBytes.length > 0) {
                String base64Photo = Base64.getEncoder().encodeToString(photoBytes);
                RoomResponse roomResponse = getRoomResponse(room);
                roomResponse.setPhoto(base64Photo);
                response.add(roomResponse);
            }
        }
        return ResponseEntity.ok(response);
    }

    //delete room by id
    @DeleteMapping("/delete/room/{roomId}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long roomId) {
        roomService.deleteRoom(roomId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //update room by id
    @PutMapping("/update/{roomId}")
    public ResponseEntity<RoomResponse> updateRoom(@PathVariable Long roomId,
                                                   @RequestParam(required = false) String roomType,
                                                   @RequestParam(required = false) BigDecimal roomPrice,
                                                   @RequestParam(required = false) MultipartFile photo) throws IOException, SQLException {
        byte[] photoBytes=photo!= null && !photo.isEmpty()?
                photo.getBytes():roomService.getRoomPhotoByRoomId(roomId);
        Blob photoBlob=photoBytes!=null && photoBytes.length > 0 ? new SerialBlob(photoBytes):null;
        Room theRoom=roomService.updateRoom(roomId,roomType, roomPrice, photoBytes);
        theRoom.setPhoto(photoBlob);
        RoomResponse roomResponse = getRoomResponse(theRoom);
        return ResponseEntity.ok(roomResponse);
    }

    //Get room by ID
    @GetMapping("/room/{roomId}")
    public ResponseEntity<Optional<RoomResponse>> getRoomById(@PathVariable Long roomId){
    Optional<Room> theRoom=roomService.getRoomBYId(roomId);
    return theRoom.map(room->{
        RoomResponse roomResponse = getRoomResponse(room);
        return ResponseEntity.ok(Optional.of(roomResponse));
    }).orElseThrow(()->new ResourceNotFoundException("Room not found"));
    }




    private RoomResponse getRoomResponse(Room room) {
        List<BookedRoom> bookings = getAllBookingsByRoomId(room.getId());
//        List<BookingResponse> bookingInfo = bookings
//                .stream()
//                .map(booking -> new BookingResponse(booking.getBookingId(),
//                        booking.getCheckInDate(),
//                        booking.getCheckOutDate(),
//                        booking.getBookingConfirmationCode()))
//                .toList();
        byte[] photoByte = null;
        Blob photoBlob = room.getPhoto();
        if (photoBlob != null) {
            try {
                photoByte = photoBlob.getBytes(1, (int) photoBlob.length());
            } catch (SQLException e) {
                throw new PhotoRetrievalException("Error retrieving photo");
            }
        }
        return new RoomResponse(room.getId(),
                room.getRoomType(),
                room.getRoomPrice(),
                room.isBooked(),photoByte);

    }



    private List<BookedRoom> getAllBookingsByRoomId(long roomId) {
        return bookingService.getAllBookingsByRoomId(roomId);

    }




}
