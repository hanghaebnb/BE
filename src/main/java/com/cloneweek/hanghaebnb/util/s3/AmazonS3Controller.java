package com.cloneweek.hanghaebnb.util.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AmazonS3Controller { //이 파일은 S3 이미지 파일업르드 확인을 위한 파일입니다.

    private final AmazonS3Service s3Service;

    // @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    // public String uploadImage(@RequestPart List<MultipartFile> imagelist,
    //                           @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException{
    //    s3Service.upload(imagelist,"static",userDetails.getUser());
    //    return "success";
    // }
}
