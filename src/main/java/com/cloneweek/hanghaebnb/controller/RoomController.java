package com.cloneweek.hanghaebnb.controller;

import com.cloneweek.hanghaebnb.common.security.UserDetailsImpl;
import com.cloneweek.hanghaebnb.dto.ResponseMsgDto;
import com.cloneweek.hanghaebnb.dto.RoomRequestDto;
import com.cloneweek.hanghaebnb.dto.RoomResponseDto;
import com.cloneweek.hanghaebnb.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RoomController {
    private final RoomService roomService;

    //숙소 등록
    @PostMapping(value = "/rooms",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<RoomResponseDto> createRoom(@RequestPart RoomRequestDto requestDto,
                                                      @RequestPart List<MultipartFile> multipartFilelist,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return ResponseEntity.ok(roomService.createRoom(requestDto, userDetails.getUser(),multipartFilelist));
    }

    //숙소 전체 조회
    @GetMapping("/rooms")
    public ResponseEntity<List<RoomResponseDto>> getRooms(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(roomService.getRooms(userDetails.getUser()));
    }

    //숙수 정보 수정
    @PatchMapping("/rooms/{roomId}")
    public ResponseEntity<RoomResponseDto> updateRoom(@PathVariable Long roomId, @RequestBody RoomRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(roomService.update(roomId, requestDto, userDetails.getUser()));
    }

    //숙소 삭제
    @DeleteMapping("/rooms/{roomId}")
    public ResponseEntity<ResponseMsgDto> deleteRoom(@PathVariable Long roomId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(roomService.delete(roomId, userDetails.getUser()));
    }
    // 숙소 좋아요
    @PostMapping("/rooms/{roomId}/like")
    public ResponseEntity<ResponseMsgDto> saveLike(
            @PathVariable Long roomId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(roomService.saveLike(roomId, userDetails.getUser()));
    }

    // 게시글 좋아요 취소
    @DeleteMapping("/rooms/{roomId}/like")
    public ResponseEntity<ResponseMsgDto> cancelLike(
            @PathVariable Long roomId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(roomService.cancelLike(roomId, userDetails.getUser()));
    }
}
