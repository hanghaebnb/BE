package com.cloneweek.hanghaebnb.repository;

import com.cloneweek.hanghaebnb.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByNickname(String nickname);
    Optional<User> findByKakaoId(Long kakaoId);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
}
