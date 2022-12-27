package com.cloneweek.hanghaebnb.email;

import com.cloneweek.hanghaebnb.common.exception.CustomException;
import com.cloneweek.hanghaebnb.dto.SignupRequestDto;
import com.cloneweek.hanghaebnb.entity.User;
import com.cloneweek.hanghaebnb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Random;

import static com.cloneweek.hanghaebnb.common.exception.StatusMsgCode.EXIST_NICK;
import static com.cloneweek.hanghaebnb.common.exception.StatusMsgCode.EXIST_USER;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender emailSender;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CodeRepository codeRepository;


    public String sendSimpleMessage(String email) throws Exception {
        if(userRepository.findByEmail(email).isPresent()) {
            throw new CustomException(EXIST_USER);
        }
        String randomCode = "";
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤

            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    //  a~z  (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    //  A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }
        randomCode = key.toString();

        // TODO Auto-generated method stub
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, email);//보내는 대상
        message.setSubject("hanghaebnb 회원가입 이메일 인증");//제목

        String msgg="";
        msgg+= "<div style='margin:100px;'>";
        msgg+= "<h1> 안녕하세요 hanghaebnb입니다. </h1>";
        msgg+= "<br>";
        msgg+= "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
        msgg+= "<br>";
        msgg+= "<p>감사합니다!<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "CODE : <strong>";
        msgg+= randomCode +"</strong><div><br/> ";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("hanghaebnb@gmail.com","test"));//보내는 사람

        System.out.println("보내는 대상 : "+ email);
        System.out.println("인증 번호 : "+ randomCode);

        try{
            emailSender.send(message);
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }

        Code code = new Code(randomCode,email);
        codeRepository.save(code);
        return randomCode;
    }

    public void emailSignup(SignupRequestDto dto) {
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

    public boolean verifyCode(String issuedCode, String email) {

        List<Code> codeList = codeRepository.findByEmail(email);
        long id = 0L;
        for (Code code : codeList) {
            if(code.getId()>id) {
                id=code.getId();
            }
        }
        Code code = codeRepository.findByEmailAndId(email, id);

        Boolean result = false;

        System.out.println(code.getRandomCode());
        if(code.getRandomCode().equals(issuedCode)) {

            result =true;
        }
        return result;
    }
}
