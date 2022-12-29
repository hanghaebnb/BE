package com.cloneweek.hanghaebnb.controller;

import com.cloneweek.hanghaebnb.util.jwt.JwtUtil;
import com.cloneweek.hanghaebnb.util.exception.StatusMsgCode;
import com.cloneweek.hanghaebnb.dto.RequestDto.DupliCheckDto;
import com.cloneweek.hanghaebnb.dto.RequestDto.LoginRequestDto;
import com.cloneweek.hanghaebnb.dto.ResponseDto.ResponseMsgDto;
import com.cloneweek.hanghaebnb.dto.RequestDto.SignupRequestDto;
import com.cloneweek.hanghaebnb.service.KakaoService;
import com.cloneweek.hanghaebnb.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
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
    private final KakaoService kakaoService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<ResponseMsgDto> signup(@RequestBody @Valid SignupRequestDto dto) {
        userService.signup(dto);
        return ResponseEntity.ok(new ResponseMsgDto(StatusMsgCode.SIGN_UP));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<ResponseMsgDto> login(@RequestBody LoginRequestDto dto, HttpServletResponse response) {
        userService.login(dto, response);
        return ResponseEntity.ok(new ResponseMsgDto(StatusMsgCode.LOG_IN));
    }

    // 이메일 중복 확인
    @PostMapping("/email-check")
    public ResponseEntity<ResponseMsgDto> emailCheck(@RequestBody @Valid DupliCheckDto dto) {
        return ResponseEntity.ok(userService.emailCheck(dto));
    }

    // 닉네임 중복 확인
    @PostMapping("/nick-check")
    public ResponseEntity<ResponseMsgDto> nickCheck(@RequestBody @Valid DupliCheckDto dto) {
        return ResponseEntity.ok(userService.nickCheck(dto));
    }

    @GetMapping("/kakao/callback")
    public ResponseEntity<ResponseMsgDto> kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        // code: 카카오 서버로부터 받은 인가 코드
        String createToken = kakaoService.kakaoLogin(code, response);

        // Cookie 생성 및 직접 브라우저에 Set
        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, createToken.substring(7));      //앞부분이 키값, 뒷부분이 value값
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.ok(new ResponseMsgDto(StatusMsgCode.LOG_IN));
    }
}

