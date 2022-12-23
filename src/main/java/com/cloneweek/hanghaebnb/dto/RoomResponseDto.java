package com.cloneweek.hanghaebnb.dto;

import com.cloneweek.hanghaebnb.entity.Room;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class RoomResponseDto {
    private Long id;
    private String nickname;
    private String title;
    private String description;
    private String address;
    private String type;
    private int price;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    //좋아요 부분
    private int likeNum;
    private boolean likeCheck;

    public RoomResponseDto(Room room, String nickname){
        this.nickname = nickname;
        this.id = room.getId();
        this.title = room.getTitle();
        this.type = room.getType();
        this.description = room.getDescription();
        this.address = room.getAddress();
        this.price = room.getPrice();
        this.createdAt = room.getCreatedAt();
        this.modifiedAt = room.getModifiedAt();

    }

    public RoomResponseDto(Room room, String nickname, boolean likeCheck){
        this.nickname = nickname;
        this.id = room.getId();
        this.title = room.getTitle();
        this.type = room.getType();
        this.description = room.getDescription();
        this.address = room.getAddress();
        this.price = room.getPrice();
        this.createdAt = room.getCreatedAt();
        this.modifiedAt = room.getModifiedAt();
        this.likeCheck = likeCheck;
        this.likeNum = room.getLikeList().size();

    }
}
