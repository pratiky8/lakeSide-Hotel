package com.athina.lakesidehotel.service;

import com.athina.lakesidehotel.model.Room;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface RoomService {

    Room addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice) throws IOException, SQLException;

    List<Room> getAllRooms();

    List<String> getAllRoomTypes();

    byte[] getRoomPhotoByRoomId(long roomId) throws SQLException;

    void deleteRoom(Long roomId);
}
