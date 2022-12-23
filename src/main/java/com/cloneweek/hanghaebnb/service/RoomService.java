package com.cloneweek.hanghaebnb.service;

import com.cloneweek.hanghaebnb.common.exception.CustomException;
import com.cloneweek.hanghaebnb.common.exception.StatusMsgCode;
import com.cloneweek.hanghaebnb.common.security.UserDetailsImpl;
import com.cloneweek.hanghaebnb.dto.ResponseMsgDto;
import com.cloneweek.hanghaebnb.dto.RoomRequestDto;
import com.cloneweek.hanghaebnb.dto.RoomResponseDto;
import com.cloneweek.hanghaebnb.entity.Room;
import com.cloneweek.hanghaebnb.entity.RoomLike;
import com.cloneweek.hanghaebnb.entity.User;
import com.cloneweek.hanghaebnb.repository.RoomLikeRepository;
import com.cloneweek.hanghaebnb.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomLikeRepository roomLikeRepository;

    //숙소 정보 작성
    public RoomResponseDto createRoom(RoomRequestDto requestDto, User user) {
        Room room = new Room(requestDto, user);
        roomRepository.save(room);
        return new RoomResponseDto(room, user.getNickname());
    }

    //숙소 정보 전체 조회
    @Transactional(readOnly = true)
    public List<RoomResponseDto> getRooms(User user) {

        List<Room> roomList = roomRepository.findAllByOrderByModifiedAtAsc();
        List<RoomResponseDto> roomResponseDto = new ArrayList<>();
        for (Room room : roomList) {
            roomResponseDto.add(new RoomResponseDto(
                    room,
                    user.getNickname(),
                    (checkLike(room.getId(),user))));
        }
        return roomResponseDto;
    }

    //숙소 정보 수정
    @Transactional
    public RoomResponseDto update(Long roomId, RoomRequestDto requestDto, User user) {
        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new CustomException(StatusMsgCode.ROOM_NOT_FOUND)
        );
        if (room.getUser().getNickname().equals(user.getNickname())) {
            room.update(requestDto);
        } else {
            throw new CustomException(StatusMsgCode.INVALID_USER);
        }

        return new RoomResponseDto(room, user.getNickname());
    }

    //숙소 정보 삭제
    @Transactional
    public ResponseMsgDto delete(Long roomId, User user) {
        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new CustomException(StatusMsgCode.ROOM_NOT_FOUND)
        );
        if (room.getUser().getNickname().equals(user.getNickname())) {
            roomRepository.deleteById(roomId);
        } else {
            throw new CustomException(StatusMsgCode.INVALID_USER);
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
        return new ResponseMsgDto(StatusMsgCode.LIKE);
    }
}
