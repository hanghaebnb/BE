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

    @PostMapping("/email/comfirm")
    public ResponseEntity<?> emailConfirm(@RequestBody Map<String, String> email) throws Exception {
        emailService.sendSimpleMessage(email.get("email"));
        return ResponseEntity.ok(new ResponseMsgDto(StatusMsgCode.EMAIL_CONFIRM));
    }

    @PostMapping("/email/verifycode")
    public boolean verifyCode(@RequestParam String issuedCode, String email) {
        System.out.println(issuedCode);
        System.out.println(email);
        return emailService.verifyCode(issuedCode, email);
    }

@PostMapping("/email/signup")
    public ResponseEntity<ResponseMsgDto> emailSignup(@RequestBody @Valid SignupRequestDto dto) {
    emailService.emailSignup(dto);
    return ResponseEntity.ok(new ResponseMsgDto(StatusMsgCode.SIGN_UP));
    }
}
