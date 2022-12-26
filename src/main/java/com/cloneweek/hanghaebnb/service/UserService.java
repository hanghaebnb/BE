package com.cloneweek.hanghaebnb.service;

import com.cloneweek.hanghaebnb.common.exception.CustomException;
import com.cloneweek.hanghaebnb.common.jwt.JwtUtil;
import com.cloneweek.hanghaebnb.dto.LoginRequestDto;
import com.cloneweek.hanghaebnb.dto.ResponseBoolDto;
import com.cloneweek.hanghaebnb.dto.ResponseMsgDto;
import com.cloneweek.hanghaebnb.dto.SignupRequestDto;
import com.cloneweek.hanghaebnb.entity.User;
import com.cloneweek.hanghaebnb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletResponse;
import static com.cloneweek.hanghaebnb.common.exception.StatusMsgCode.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // 회원가입
    public void signup(SignupRequestDto dto) {
        String email = dto.getEmail();
        String password = passwordEncoder.encode(dto.getPassword());
        String nickname = dto.getNickname();

        if(userRepository.findByEmail(email).isPresent()) {
            throw new CustomException(EXIST_USER);
        }

        if(userRepository.findByNickname(nickname).isPresent()) {
            throw new CustomException(EXIST_NICK);
        }

        User user = new User(email, password, nickname);
        userRepository.save(user);
    }

    // 로그인
    public void login(LoginRequestDto dto, HttpServletResponse response) {
        String email = dto.getEmail();
        String password = dto.getPassword();

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(USER_NOT_FOUND)
        );

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(INVALID_PASSWORD);
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getEmail()));
    }

    // 이메일 중복 확인
    public ResponseMsgDto emailCheck(SignupRequestDto dto) {
        Boolean flag = userRepository.existsByEmail(dto.getEmail());
        return new ResponseBoolDto(flag ? EXIST_USER : EMAIL, flag);
    }

    // 닉네임 중복 확인
    public ResponseMsgDto nickCheck(SignupRequestDto dto) {
        Boolean flag = userRepository.existsByNickname(dto.getNickname());
        return new ResponseBoolDto(flag ? EXIST_NICK : NICKNAME, flag);
    }
}

