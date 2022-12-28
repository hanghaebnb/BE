package com.cloneweek.hanghaebnb.service;

import com.cloneweek.hanghaebnb.common.exception.CustomException;
import com.cloneweek.hanghaebnb.common.exception.StatusMsgCode;
import com.cloneweek.hanghaebnb.common.s3.AmazonS3Service;
import com.cloneweek.hanghaebnb.dto.*;
import com.cloneweek.hanghaebnb.entity.ImageFile;
import com.cloneweek.hanghaebnb.entity.Room;
import com.cloneweek.hanghaebnb.entity.RoomLike;
import com.cloneweek.hanghaebnb.entity.User;
import com.cloneweek.hanghaebnb.repository.ImageFileRepository;
import com.cloneweek.hanghaebnb.repository.RoomLikeRepository;
import com.cloneweek.hanghaebnb.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomLikeRepository roomLikeRepository;
    private final ImageFileRepository imageFileRepository;
    private final AmazonS3Service s3Service;

    //숙소 정보 작성
    public ResponseMsgDto createRoom(RoomRequestDto requestDto, User user, List<MultipartFile> multipartFilelist) throws IOException {
        Room room = new Room(requestDto, user);
        roomRepository.save(room);

        if (multipartFilelist != null) {
            s3Service.upload(multipartFilelist, "static", room, user);
        }
        return new ResponseMsgDto(StatusMsgCode.DONE_POST);
    }

    //숙소 정보 전체 조회
    @Transactional(readOnly = true) //회원 전체 조회
    public List<RoomResponseDto> getRooms(User user, Pageable pageable, int minPrice, int maxPrice, String type) {

        // pageable은 필수, type, price(기본값 0)별 필터링
        Page<Room> roomList = roomRepository.findAll(pageable);      // RequestParam page, size만 있을 때
        if (type != null && minPrice == 0 && maxPrice == 0) {         // RequestParam type만 있을 때
            roomList = roomRepository.findByType(type, pageable);
        } else if (type == null && minPrice != 0 && maxPrice != 0) {  // RequestParam price만 있을 때
            roomList = roomRepository.findByPriceBetween(minPrice, maxPrice, pageable);
        } else if (type != null && minPrice != 0 && maxPrice != 0) {  // RequestParam type, price 둘 다 있을 때
            roomList = roomRepository.findByPriceBetweenAndType(minPrice, maxPrice, type, pageable);
        }
        List<RoomResponseDto> roomResponseDto = new ArrayList<>();
        for (Room room : roomList) {
            List<String> imageFileList = new ArrayList<>();
            for (ImageFile imageFile : room.getImageFileList()) {
                imageFileList.add(imageFile.getPath());
            }
            roomResponseDto.add(new RoomResponseDto(
                    room,
                    (checkLike(room.getId(), user)),
                    imageFileList));
        }
        return roomResponseDto;
    }

    @Transactional(readOnly = true) //비회원 전체 조회
    public List<UnClientResponseDto> getnoclientRooms(Pageable pageable) {
        Page<Room> roomList = roomRepository.findAll(pageable);

        List<UnClientResponseDto> unClientResponseDto = new ArrayList<>();
        for (Room room : roomList) {
//            List<ImageFileResponseDto> imageFileResponseDtoList = new ArrayList<>();
//            for (ImageFile imageFile : room.getImageFileList()) {
//                imageFileResponseDtoList.add(new ImageFileResponseDto(imageFile));
//            }
            List<String> imageFileList = new ArrayList<>(); // 12.27 14:34 변경사항
            for (ImageFile imageFile : room.getImageFileList()) {
                imageFileList.add(imageFile.getPath());
            }
            unClientResponseDto.add(new UnClientResponseDto(room, imageFileList));
        }
        return unClientResponseDto;
    }

    //숙소 키워드 검색
    @Transactional(readOnly = true)
    public List<UnClientResponseDto> search(String keyword, Pageable pageable) {
        Page<Room> roomList = roomRepository.findByTitleContaining(keyword, pageable);

        List<UnClientResponseDto> roomResponseDtos = new ArrayList<>();
        for (Room room : roomList) {
            List<String> imageFileList = new ArrayList<>();
            for (ImageFile imageFile : room.getImageFileList()) {
                imageFileList.add(imageFile.getPath());
            }
            roomResponseDtos.add(new UnClientResponseDto(room, imageFileList));
        }

        return roomResponseDtos;
    }

    //숙소 정보 수정
//    @Transactional
//    public RoomResponseDto update(Long roomId, RoomRequestDto requestDto, User user, List<MultipartFile> multipartFilelist) throws IOException {
//
//        Room room = roomRepository.findById(roomId).orElseThrow(
//                () -> new CustomException(StatusMsgCode.ROOM_NOT_FOUND)
//        );
//        if (room.getUser().getNickname().equals(user.getNickname())) {
//            room.update(requestDto);
//        } else {
//            throw new CustomException(StatusMsgCode.INVALID_USER);
//        }
//
//        if (multipartFilelist != null) {
//
//            List<ImageFile> imageFileList = imageFileRepository.findAllByRoom(room);
//
//            for (ImageFile File : imageFileList) {
//                String path = File.getPath();
//                String filename = path.substring(56);
//                s3Service.deleteFile(filename);
//            }
//
//            imageFileRepository.deleteAll(imageFileList);
//
//            s3Service.upload(multipartFilelist, "static", room, user);
//        }
//        return new RoomResponseDto(room, user.getNickname());
//    }
    @Transactional
    public ResponseMsgDto update(Long roomId, RoomRequestDto requestDto, User user, List<MultipartFile> multipartFilelist) {
        Room room = roomRepository.findById(roomId).orElseThrow(                // 글 존재 여부 확인
                () -> new CustomException(StatusMsgCode.ROOM_NOT_FOUND)
        );

        if (!room.getUser().getNickname().equals(user.getNickname())) {         // 작성자와 수정자 검증
            throw new CustomException(StatusMsgCode.INVALID_USER);
        }
        try {
            room.update(requestDto);

            if (multipartFilelist != null) {
                List<ImageFile> imageFileList = imageFileRepository.findAllByRoom(room);
                for (ImageFile File : imageFileList) {
                    String path = File.getPath();
                    String filename = path.substring(56);
                    s3Service.deleteFile(filename);
                }
                imageFileRepository.deleteAll(imageFileList);

                s3Service.upload(multipartFilelist, "static", room, user);
            }
        } catch (IOException e) {
            throw new CustomException(StatusMsgCode.FILE_UPLOAD_FAILED);
        }

        return new ResponseMsgDto(StatusMsgCode.UPDATE);
    }

    //숙소 정보 삭제
//    @Transactional
//    public ResponseMsgDto delete(Long roomId, User user) {
//        Room room = roomRepository.findById(roomId).orElseThrow(
//                () -> new CustomException(StatusMsgCode.ROOM_NOT_FOUND)
//        );
//        if (room.getUser().getNickname().equals(user.getNickname())) {
//
//            roomLikeRepository.deleteAllByRoom(room); // 룸에 해당하는 좋아요 삭제
//
//            List<ImageFile> imageFileList = imageFileRepository.findAllByRoom(room);
//            for (ImageFile imageFile : imageFileList) {
//                String path = imageFile.getPath();
//                String filename = path.substring(58);
//                s3Service.deleteFile(filename);
//            }
//
//            imageFileRepository.deleteAllByRoom(room); // 룸에 해당하는 이미지 파일 삭제
//
//            roomRepository.deleteById(roomId); // 최종적 룸 삭제
//        } else {
//            throw new CustomException(StatusMsgCode.INVALID_USER);
//        }
//        return new ResponseMsgDto(StatusMsgCode.DELETE_POST);
//    }
    @Transactional
    public ResponseMsgDto delete(Long roomId, User user) {
        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new CustomException(StatusMsgCode.ROOM_NOT_FOUND)
        );
        if (!room.getUser().getNickname().equals(user.getNickname())) {
            throw new CustomException(StatusMsgCode.INVALID_USER);
        }
        try {
            roomLikeRepository.deleteAllByRoom(room); // 룸에 해당하는 좋아요 삭제

            List<ImageFile> imageFileList = imageFileRepository.findAllByRoom(room);
            for (ImageFile imageFile : imageFileList) {
                String path = imageFile.getPath();
                String filename = path.substring(56);
                s3Service.deleteFile(filename);
            }

            imageFileRepository.deleteAllByRoom(room); // 룸에 해당하는 이미지 파일 삭제

            roomRepository.deleteById(roomId); // 최종적 룸 삭제
        } catch (CustomException e) {
            throw new CustomException(StatusMsgCode.FILE_DELETE_FAILED);

        }
        return new ResponseMsgDto(StatusMsgCode.DELETE_POST);
    }

    //좋아요 여부 확인
    @Transactional(readOnly = true)
    public boolean checkLike(Long roomId, User user) {
        Optional<RoomLike> roomLike = roomLikeRepository.findByRoomIdAndUserId(roomId, user.getId());
        return roomLike.isPresent();
    }

    //좋아요 추가
    @Transactional
    public ResponseMsgDto saveLike(Long roomId, User user) {
        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new CustomException(StatusMsgCode.ROOM_NOT_FOUND)
        );

        if (checkLike(roomId, user)) {
            throw new CustomException(StatusMsgCode.ALREADY_CLICKED_LIKE);
        }
        roomLikeRepository.saveAndFlush(new RoomLike(room, user));
        return new ResponseMsgDto(StatusMsgCode.LIKE);
    }

    //좋아요 삭제
    @Transactional
    public ResponseMsgDto cancelLike(Long roomId, User user) {
        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new CustomException(StatusMsgCode.ROOM_NOT_FOUND)
        );
        if (!checkLike(roomId, user)) {
            throw new CustomException(StatusMsgCode.ALREADY_CANCEL_LIKE);
        }
        roomLikeRepository.deleteByRoomIdAndUserId(roomId, user.getId());
        return new ResponseMsgDto(StatusMsgCode.CANCEL_LIKE);
    }
}
