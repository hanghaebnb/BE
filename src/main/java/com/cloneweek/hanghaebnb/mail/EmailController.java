package com.cloneweek.hanghaebnb.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/emailLogin")
    public ResponseEntity<?> emailConfirm(@RequestBody String email) throws Exception {

        String confirm = emailService.sendSimpleMessage(email);

        return ResponseEntity.status(200).body(confirm);
    }
}