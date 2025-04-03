package com.athina.lakesidehotel.repository;

import com.athina.lakesidehotel.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("SELECT distinct r.roomType FROM Room r")
    List<String> findDistinctRoomTypes();
}
