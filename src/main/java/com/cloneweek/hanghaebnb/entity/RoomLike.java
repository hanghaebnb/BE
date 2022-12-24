package com.cloneweek.hanghaebnb.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class RoomLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ROOM_ID", nullable = false)
    private Room room;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    public RoomLike(Room room, User user) {
        this.room = room;
        this.user = user;
    }

}
