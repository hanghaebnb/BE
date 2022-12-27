package com.cloneweek.hanghaebnb.repository;

import com.cloneweek.hanghaebnb.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>, JpaSpecificationExecutor<Room> {
    Page<Room> findAll(Pageable pageable);
//    List<Room> findAllByOrderByCreatedAtDesc();

    Page<Room> findByTitleContaining(String keyword, Pageable pageable);
}
