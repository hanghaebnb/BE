package com.cloneweek.hanghaebnb.email;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CodeRepository extends JpaRepository<Code, Long> {
    List<Code> findByEmail(String email);
    Code findByEmailAndId(String email, Long id);
}
