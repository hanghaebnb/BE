package com.cloneweek.hanghaebnb.entity;

import com.cloneweek.hanghaebnb.dto.RoomRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    @JoinColumn(name = "userid", nullable = false)
    private User user;

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
