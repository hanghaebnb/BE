package com.cloneweek.hanghaebnb.repository;

import com.cloneweek.hanghaebnb.entity.ImageFile;
import com.cloneweek.hanghaebnb.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ImageFileRepository extends JpaRepository<ImageFile, Long> {

    List<ImageFile> findAllByRoom(Room room);

    @Transactional
    void deleteAllByRoom(Room room);

}
