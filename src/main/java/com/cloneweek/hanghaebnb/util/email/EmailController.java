package com.cloneweek.hanghaebnb.util.email;

import com.cloneweek.hanghaebnb.dto.RequestDto.DupliCheckDto;
import com.cloneweek.hanghaebnb.dto.ResponseDto.ResponseBoolDto;
import com.cloneweek.hanghaebnb.util.exception.StatusMsgCode;
import com.cloneweek.hanghaebnb.dto.ResponseDto.ResponseMsgDto;
import com.cloneweek.hanghaebnb.dto.RequestDto.SignupRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

import static com.cloneweek.hanghaebnb.util.exception.StatusMsgCode.EXIST_NICK;
import static com.cloneweek.hanghaebnb.util.exception.StatusMsgCode.NICKNAME;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/email")
public class EmailController {
    private final EmailService emailService;

    // 입력한 이메일로 인증코드 발송
    @PostMapping("/confirm")
    public ResponseEntity<?> emailConfirm(@RequestBody EmailConfirmDto emailConfirmDto) throws Exception {
        emailService.sendSimpleMessage(emailConfirmDto.getEmail());
        return ResponseEntity.ok(new ResponseMsgDto(StatusMsgCode.EMAIL_CONFIRM));
    }

    // 인증코드 발송받은 이메일과 인증번호 입력
    @PostMapping("/verifycode")
    public ResponseEntity<ResponseMsgDto> verifyCode(@RequestParam String issuedCode, String email) {
//        return emailService.verifyCode(issuedCode, email);
        return ResponseEntity.ok(emailService.verifyCode(issuedCode, email));
    }

    // 회원가입 로직
    @PostMapping("/signup")
    public ResponseEntity<ResponseMsgDto> emailSignup(@RequestBody @Valid SignupRequestDto dto) {
        emailService.emailSignup(dto);
        return ResponseEntity.ok(new ResponseMsgDto(StatusMsgCode.SIGN_UP));
    }
}
