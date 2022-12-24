package com.cloneweek.hanghaebnb.common.s3;

import com.cloneweek.hanghaebnb.common.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AmazonS3Controller {

    private final AmazonS3Service s3Service;

    // @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    // public String uploadImage(@RequestPart List<MultipartFile> imagelist,
    //                           @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException{
    //    s3Service.upload(imagelist,"static",userDetails.getUser());
    //    return "success";
    // }
}
