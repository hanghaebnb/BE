package com.cloneweek.hanghaebnb.repository;

import com.cloneweek.hanghaebnb.entity.Room;
import com.cloneweek.hanghaebnb.entity.RoomLike;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface RoomLikeRepository extends JpaRepository<RoomLike, Long> {

    Optional<RoomLike> findByRoomIdAndUserId(Long roomId, Long userId);

    void deleteByRoomIdAndUserId(Long roomId, Long userId);

    @Transactional
    void deleteAllByRoom(Room room);
}
