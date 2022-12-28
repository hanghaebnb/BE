package com.cloneweek.hanghaebnb.dto.RequestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoomRequestDto {
    private String title;
    private String description;
    private String address;
    private String type;
    private int price;

}
