package com.cloneweek.hanghaebnb.controller;

import com.cloneweek.hanghaebnb.util.security.UserDetailsImpl;
import com.cloneweek.hanghaebnb.dto.ResponseDto.ResponseMsgDto;
import com.cloneweek.hanghaebnb.dto.RequestDto.RoomRequestDto;
import com.cloneweek.hanghaebnb.dto.ResponseDto.RoomResponseDto;
import com.cloneweek.hanghaebnb.dto.ResponseDto.UnClientResponseDto;
import com.cloneweek.hanghaebnb.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<ResponseMsgDto> createRoom(@RequestPart(value = "data") RoomRequestDto requestDto,
                                                      @RequestPart(value = "file")  List<MultipartFile> multipartFilelist,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return ResponseEntity.ok(roomService.createRoom(requestDto, userDetails.getUser(),multipartFilelist));
    }

    //숙소 전체 조회
    @GetMapping("/rooms") // size '/api/rooms?page=0&size=3'
    public ResponseEntity<List<RoomResponseDto>> getRooms(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                          @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                                                          @RequestParam(required = false, defaultValue = "-1") int minPrice,
                                                          @RequestParam(required = false, defaultValue = "-1") int maxPrice,
                                                          @RequestParam(required = false) String type) {
        return ResponseEntity.ok(roomService.getRooms(userDetails.getUser(), pageable, minPrice, maxPrice, type));
    }

    // 비회원 숙소 전체 조회
    @GetMapping("/rooms/main")
    public ResponseEntity<List<UnClientResponseDto>> getnoclientRooms(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                                                                      @RequestParam(required = false, defaultValue = "-1") int minPrice,
                                                                      @RequestParam(required = false, defaultValue = "-1") int maxPrice,
                                                                      @RequestParam(required = false) String type) {
        return ResponseEntity.ok(roomService.getnoclientRooms(pageable, minPrice, maxPrice, type));
    }

    //숙소 키워드 조회
    @GetMapping("/rooms/search") // '/api/rooms/search?keyword=제목&page=0&size=2'
    public ResponseEntity<List<UnClientResponseDto>> search(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                                                            String keyword){
        return ResponseEntity.ok(roomService.search(keyword, pageable));
    }

    //숙소 상세 조회
    @GetMapping("/rooms/{roomId}")
    public ResponseEntity<UnClientResponseDto> getRoom(@PathVariable Long roomId) {
        return ResponseEntity.ok(roomService.getRoom(roomId));
    }

    //숙소 정보 수정
    @CrossOrigin // CORS 해결책 중 하나
    @PatchMapping("/rooms/{roomId}")
    public ResponseEntity<ResponseMsgDto> updateRoom(@PathVariable Long roomId,
                                                      @RequestPart(value = "data") RoomRequestDto requestDto,
                                                      @RequestPart(value = "file")  List<MultipartFile> multipartFilelist,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return ResponseEntity.ok(roomService.update(roomId, requestDto, userDetails.getUser(),multipartFilelist));
    }

    //숙소 삭제
    @DeleteMapping("/rooms/{roomId}")
    public ResponseEntity<ResponseMsgDto> deleteRoom(@PathVariable Long roomId,
                                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(roomService.delete(roomId, userDetails.getUser()));
    }
    // 숙소 좋아요
    @PostMapping("/rooms/{roomId}/like")
    public ResponseEntity<ResponseMsgDto> saveLike(@PathVariable Long roomId,
                                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(roomService.saveLike(roomId, userDetails.getUser()));
    }

    // 숙소 좋아요 취소
    @DeleteMapping("/rooms/{roomId}/like")
    public ResponseEntity<ResponseMsgDto> cancelLike(@PathVariable Long roomId,
                                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(roomService.cancelLike(roomId, userDetails.getUser()));
    }
}
