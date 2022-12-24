package com.cloneweek.hanghaebnb.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class ImageFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    // id

    @Column(nullable = false)           // image 경로
    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", nullable = false)
    private User user;                  // userid

    public ImageFile(String path, User user) { // 임시 생성자
        this.path = path;
        this.user = user;
    }



}