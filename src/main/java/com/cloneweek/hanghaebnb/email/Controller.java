package com.cloneweek.hanghaebnb.email;

import com.cloneweek.hanghaebnb.common.exception.StatusMsgCode;
import com.cloneweek.hanghaebnb.dto.ResponseMsgDto;
import com.cloneweek.hanghaebnb.dto.SignupRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class Controller {
    private final EmailService emailService;

    // 입력한 이메일로 인증코드 발송
    @PostMapping("/email/comfirm")
    public ResponseEntity<?> emailConfirm(@RequestBody Map<String, String> email) throws Exception {
        emailService.sendSimpleMessage(email.get("email"));
        return ResponseEntity.ok(new ResponseMsgDto(StatusMsgCode.EMAIL_CONFIRM));
    }

    // 인증코드 발송받은 이메일과 인증번호 입력
    @PostMapping("/email/verifycode")
    public boolean verifyCode(@RequestParam String issuedCode, String email) {
        return emailService.verifyCode(issuedCode, email);
    }

    // 회원가입 로직
    @PostMapping("/email/signup")
    public ResponseEntity<ResponseMsgDto> emailSignup(@RequestBody @Valid SignupRequestDto dto) {
        emailService.emailSignup(dto);
        return ResponseEntity.ok(new ResponseMsgDto(StatusMsgCode.SIGN_UP));
    }
}
