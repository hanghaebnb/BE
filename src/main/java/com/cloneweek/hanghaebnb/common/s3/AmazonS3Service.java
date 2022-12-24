package com.cloneweek.hanghaebnb.common.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.cloneweek.hanghaebnb.entity.ImageFile;
import com.cloneweek.hanghaebnb.entity.Room;
import com.cloneweek.hanghaebnb.entity.User;
import com.cloneweek.hanghaebnb.repository.ImageFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
@Service
public class AmazonS3Service {


    private final AmazonS3Client amazonS3Client;
    private final ImageFileRepository imageFileRepository;

    @Value("${cloud.aws.s3.bucket}")                                                        //bucket 이름
    public String bucket;


        public void upload(List<MultipartFile> multipartFilelist, String dirName, Room room, User user) throws IOException {

            for (MultipartFile multipartFile : multipartFilelist){
                if (multipartFile != null){
                    File uploadFile = convert(multipartFile).orElseThrow(() -> new IllegalArgumentException("파일 전환 실패"));
                    ImageFile imageFile = new ImageFile(upload(uploadFile, dirName), user,room);
                    imageFileRepository.save(imageFile);
                }
            }
    }

    // S3로 파일 업로드하기
    private String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + UUID.randomUUID(); // S3에 저장된 파일 이름
        String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    // S3로 업로드
    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }


    // 로컬에 저장된 이미지 지우기
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("File delete success");
            return;
        }
        log.info("File delete fail");
    }

    private Optional<File> convert(MultipartFile multipartFile) throws IOException {
//        File convertFile = new File(System.getProperty("user.dir") + "/" + multipartFile.getOriginalFilename()); //이전코드 12.23 11:37
        File convertFile = new File(multipartFile.getOriginalFilename());
        // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
                fos.write(multipartFile.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }

    // find image from s3
    public String getThumbnailPath(String path) {
        return amazonS3Client.getUrl(bucket, path).toString();
    }

    //remove s3 object
    public void deleteFile(String fileName){
        DeleteObjectRequest request = new DeleteObjectRequest(bucket, fileName);
        amazonS3Client.deleteObject(request);
    }

}
