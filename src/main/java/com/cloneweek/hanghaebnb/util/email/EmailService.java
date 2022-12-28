package com.cloneweek.hanghaebnb.util.email;

import com.cloneweek.hanghaebnb.util.exception.CustomException;
import com.cloneweek.hanghaebnb.dto.RequestDto.SignupRequestDto;
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

import static com.cloneweek.hanghaebnb.util.exception.StatusMsgCode.EXIST_NICK;
import static com.cloneweek.hanghaebnb.util.exception.StatusMsgCode.EXIST_USER;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender emailSender;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CodeRepository codeRepository;


    // 랜덤하게 만든 인증코드 메일로 발송하는 메서드
    public String sendSimpleMessage(String email) throws Exception {
        if(userRepository.findByEmail(email).isPresent()) {
            throw new CustomException(EXIST_USER);
        }
        String randomCode = "";
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        // 인증코드 랜덤하게 만들기. 인증 요청될 때마다 새롭게만들어 메일로 보냄
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

        // 메세지 내용 작성
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("hanghaebnb 회원가입 이메일 인증");
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

        // 메세지 발송
        try{
            emailSender.send(message);
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }

        // 만들었던 랜덤코드 DB에 저장하기
        Code code = new Code(randomCode,email);
        codeRepository.save(code);
        return randomCode;
    }

    // 회원가입
    public void emailSignup(SignupRequestDto dto) {
        String email = dto.getEmail();
        String password = passwordEncoder.encode(dto.getPassword());
        String nickname = dto.getNickname();

        // DB에 이메일과 닉네임 중복 있는지 체크
        if(userRepository.findByEmail(email).isPresent()) {
            throw new CustomException(EXIST_USER);
        }
        if(userRepository.findByNickname(nickname).isPresent()) {
            throw new CustomException(EXIST_NICK);
        }

        // DB에 회원가입할 데이터 저장
        User user = new User(email, password, nickname);
        userRepository.save(user);
    }

    // 이메일로 받은 랜덤코드 대조
    public boolean verifyCode(String issuedCode, String email) {

        // 리파지토리에서 해당 이메일로 저장된 모든 랜덤코드 불러오기
        // 리파지토리에서 불러온 랜덤코드 중 가장 최신순으로 발행된 코드 찾기
        List<Code> codeList = codeRepository.findByEmail(email);
        long id = 0L;
        for (Code code : codeList) {
            if(code.getId()>id) {
                id=code.getId();
            }
        }

        // 해당 email로 보낸 가장 최신 랜덤코드 불러오기
        Code code = codeRepository.findByEmailAndId(email, id);

        // 해당 email로 보낸 가장 최신 랜덤코드와 입력한 코드 대조작업
        Boolean result = false;
        if(code.getRandomCode().equals(issuedCode)) {

            result =true;
        }
        return result;
    }
}
