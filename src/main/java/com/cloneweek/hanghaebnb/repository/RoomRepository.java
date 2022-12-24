package com.cloneweek.hanghaebnb.repository;

import com.cloneweek.hanghaebnb.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
//    List<Room> findAllByOrderByModifiedAtAsc();
    Page<Room> findAll(Pageable pageable);

}
