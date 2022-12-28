package com.cloneweek.hanghaebnb.dto.ResponseDto;

import com.cloneweek.hanghaebnb.entity.ImageFile;
import lombok.Getter;

@Getter
public class ImageFileResponseDto {
    private String path;

    public ImageFileResponseDto(ImageFile imageFile) {
        this.path = imageFile.getPath();
    }
}
