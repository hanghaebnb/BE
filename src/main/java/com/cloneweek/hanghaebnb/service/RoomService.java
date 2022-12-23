package com.cloneweek.hanghaebnb.service;

import com.cloneweek.hanghaebnb.common.exception.CustomException;
import com.cloneweek.hanghaebnb.common.exception.StatusMsgCode;
import com.cloneweek.hanghaebnb.dto.ResponseMsgDto;
import com.cloneweek.hanghaebnb.dto.RoomRequestDto;
import com.cloneweek.hanghaebnb.dto.RoomResponseDto;
import com.cloneweek.hanghaebnb.entity.Room;
import com.cloneweek.hanghaebnb.entity.User;
import com.cloneweek.hanghaebnb.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomResponseDto createRoom(RoomRequestDto requestDto, User user){
        Room room = new Room(requestDto, user);
        roomRepository.save(room);
        return new RoomResponseDto(room, user.getNickname());
    }

    public List<RoomResponseDto> getRooms() {

        List<Room> roomList = roomRepository.findAllByOrderByModifiedAtAsc();
        List<RoomResponseDto> roomResponseDto = new ArrayList<>();
        for(Room room : roomList){
            RoomResponseDto roomDto = new RoomResponseDto(room, room.getUser().getNickname());
            roomResponseDto.add(roomDto);
        }
        return roomResponseDto;
    }
    @Transactional
    public RoomResponseDto update(Long roomId, RoomRequestDto requestDto, User user){
        Room room = roomRepository.findById(roomId).orElseThrow(
                ()-> new CustomException(StatusMsgCode.ROOM_NOT_FOUND)
        );
        if(room.getUser().getNickname().equals(user.getNickname())) {
            room.update(requestDto);
        }else{
            throw new CustomException(StatusMsgCode.INVALID_USER);
        }

        return new RoomResponseDto(room, user.getNickname());
    }

    @Transactional
    public ResponseMsgDto delete(Long roomId, User user) {
        Room room = roomRepository.findById(roomId).orElseThrow(
                ()-> new CustomException(StatusMsgCode.ROOM_NOT_FOUND)
        );
        if(room.getUser().getNickname().equals(user.getNickname())) {
            roomRepository.deleteById(roomId);
        }else{
            throw new CustomException(StatusMsgCode.INVALID_USER);
        }
        return new ResponseMsgDto(HttpStatus.OK.value(), "삭제성공");

    }
}
