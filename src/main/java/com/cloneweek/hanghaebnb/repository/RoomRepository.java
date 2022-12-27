package com.cloneweek.hanghaebnb.repository;

import com.cloneweek.hanghaebnb.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Page<Room> findByTitleContaining(String keyword, Pageable pageable);          // 키워드 검색
    Page<Room> findByType(String type, Pageable pageable);                        // 타입별 필터링
    Page<Room> findByPriceBetween(int minPrice, int maxPrice, Pageable pageable); // 가격별 필터링
    @Query(countQuery = "select count(*) from room r where (r.price between :minPrice and :maxPrice) and r.type = :type", nativeQuery = true)
    Page<Room> findByPriceBetweenAndType(@Param("minPrice") int minPrice,
                                         @Param("maxPrice") int maxPrice,
                                         @Param("type") String type,
                                         Pageable pageable);                      // 타입+가격별 필터링
}
