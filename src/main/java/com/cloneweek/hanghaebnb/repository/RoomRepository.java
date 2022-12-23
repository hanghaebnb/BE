package com.cloneweek.hanghaebnb.repository;

import com.cloneweek.hanghaebnb.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findAllByOrderByModifiedAtAsc();

}
