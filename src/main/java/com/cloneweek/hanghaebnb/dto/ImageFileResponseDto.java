package com.cloneweek.hanghaebnb.dto;

import com.cloneweek.hanghaebnb.entity.ImageFile;
import lombok.Getter;

@Getter
public class ImageFileResponseDto {
    private String path;

    public ImageFileResponseDto(ImageFile imageFile) {
        this.path = imageFile.getPath();
    }
}
