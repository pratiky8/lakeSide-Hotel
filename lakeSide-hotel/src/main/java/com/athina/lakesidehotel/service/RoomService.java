package com.athina.lakesidehotel.service;

import com.athina.lakesidehotel.model.Room;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface RoomService {

    Room addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice) throws IOException, SQLException;

    List<Room> getAllRooms();

    List<String> getAllRoomTypes();

    byte[] getRoomPhotoByRoomId(long roomId) throws SQLException;

    void deleteRoom(Long roomId);

    Room updateRoom(Long roomId, String roomType, BigDecimal roomPrice, byte[] photoBytes);

    Optional<Room> getRoomBYId(Long roomId);
}
