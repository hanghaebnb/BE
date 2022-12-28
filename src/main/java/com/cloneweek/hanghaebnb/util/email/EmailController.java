package com.cloneweek.hanghaebnb.util.email;

import com.cloneweek.hanghaebnb.util.exception.StatusMsgCode;
import com.cloneweek.hanghaebnb.dto.ResponseDto.ResponseMsgDto;
import com.cloneweek.hanghaebnb.dto.RequestDto.SignupRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/email")
public class EmailController {
    private final EmailService emailService;

    // 입력한 이메일로 인증코드 발송
    @PostMapping("/confirm")
    public ResponseEntity<?> emailConfirm(@RequestBody Map<String, String> email) throws Exception {
        emailService.sendSimpleMessage(email.get("email"));
        return ResponseEntity.ok(new ResponseMsgDto(StatusMsgCode.EMAIL_CONFIRM));
    }

    // 인증코드 발송받은 이메일과 인증번호 입력
    @PostMapping("/verifycode")
    public boolean verifyCode(@RequestParam String issuedCode, String email) {
        return emailService.verifyCode(issuedCode, email);
    }

    // 회원가입 로직
    @PostMapping("/signup")
    public ResponseEntity<ResponseMsgDto> emailSignup(@RequestBody @Valid SignupRequestDto dto) {
        emailService.emailSignup(dto);
        return ResponseEntity.ok(new ResponseMsgDto(StatusMsgCode.SIGN_UP));
    }
}
