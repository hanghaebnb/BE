package com.cloneweek.hanghaebnb.entity;

import com.cloneweek.hanghaebnb.dto.RoomRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Room extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private String address;

    @Column
    private String type;

    @Column
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
    private List<RoomLike> likeList = new ArrayList<>();

    public Room(RoomRequestDto requestDto, User user){
        this.title = requestDto.getTitle();
        this.description = requestDto.getDescription();
        this.address = requestDto.getAddress();
        this.type = requestDto.getType();
        this.price = requestDto.getPrice();
        this.user = user;
    }

    public void update(RoomRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.description = requestDto.getDescription();
        this.address = requestDto.getAddress();
        this.type = requestDto.getType();
        this.price = requestDto.getPrice();
    }
}
