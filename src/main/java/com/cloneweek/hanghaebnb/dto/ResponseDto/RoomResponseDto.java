package com.cloneweek.hanghaebnb.dto.ResponseDto;

import com.cloneweek.hanghaebnb.entity.Room;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class RoomResponseDto {
    private Long id;
    private String nickname;
    private String title;
    private String description;
    private String address;
    private String type;
    private int price;
    private List<String> imageList;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
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

    public RoomResponseDto(Room room, boolean likeCheck, List<String> imageFileList){
        this.nickname = room.getUser().getNickname();
        this.id = room.getId();
        this.title = room.getTitle();
        this.type = room.getType();
        this.description = room.getDescription();
        this.address = room.getAddress();
        this.price = room.getPrice();
        this.imageList = imageFileList;
        this.createdAt = room.getCreatedAt();
        this.modifiedAt = room.getModifiedAt();
        this.likeCheck = likeCheck;
        this.likeNum = room.getLikeList().size();

    }
}
