package com.cloneweek.hanghaebnb.controller;

import com.cloneweek.hanghaebnb.dto.LoginRequestDto;
import com.cloneweek.hanghaebnb.dto.ResponseMsgDto;
import com.cloneweek.hanghaebnb.dto.SignupRequestDto;
import com.cloneweek.hanghaebnb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<ResponseMsgDto> signup(@RequestBody @Valid SignupRequestDto dto) {
        userService.signup(dto);
        return ResponseEntity.ok(new ResponseMsgDto(HttpStatus.OK.value(), "회원가입 성공!"));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<ResponseMsgDto> login(@RequestBody LoginRequestDto dto, HttpServletResponse response) {
        userService.login(dto, response);
        return ResponseEntity.ok(new ResponseMsgDto(HttpStatus.OK.value(), "로그인 성공!"));
    }

    // 이메일 중복 확인
    @PostMapping("/email-check")
    public ResponseEntity<ResponseMsgDto> idCheck(@RequestBody @Valid SignupRequestDto dto) {
        userService.emailCheck(dto);
        return ResponseEntity.ok(new ResponseMsgDto(HttpStatus.OK.value(), "사용 가능한 이메일입니다."));
    }

    // 닉네임 중복 확인
    @PostMapping("/nick-check")
    public ResponseEntity<ResponseMsgDto> nickCheck(@RequestBody SignupRequestDto dto) {
        userService.nickCheck(dto);
        return ResponseEntity.ok(new ResponseMsgDto(HttpStatus.OK.value(), "사용 가능한 닉네임입니다."));
    }

}

