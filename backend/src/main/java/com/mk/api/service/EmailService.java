package com.mk.api.service;

import com.mk.api.dto.request.EmailCheckReq;
import com.mk.api.dto.request.EmailReq;
import com.mk.db.entity.Email;
import com.mk.db.repository.EmailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Component
public class EmailService {

    @Autowired
    MailSender mailSender;
    @Autowired
    UserService userService;
    @Autowired
    EmailRepository repository;

    @Transactional
    public void createMail(EmailReq emailReq) {
        Optional<Email> checkEmail = repository.findByEmail(emailReq.getEmail());

        String key="";  //인증번호
        Map<String,String> check = new HashMap<>();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailReq.getEmail()); //스크립트에서 보낸 메일을 받을 사용자 이메일 주소
        //입력 키를 위한 코드
        key = createRandomKey();

        message.setSubject("인증번호 입력을 위한 메일 전송");
        message.setText("인증 번호 : "+key);

        Email emailDto = Email.builder().email(emailReq.getEmail()).key(key).build();


        if(!checkEmail.isPresent()){
            repository.save(emailDto);
        }else{
            Email entityEmail = checkEmail.get();
            entityEmail.setKey(key);
            repository.save(entityEmail);
        }
        mailSender.send(message);
        log.info("DB저장");
    }

   public String createRandomKey() {
        String key="";
        Random random=new Random();  //난수 생성을 위한 랜덤 클래스
        for(int i =0; i<3;i++) {
            int index=random.nextInt(25)+65; //A~Z까지 랜덤 알파벳 생성
            key+=(char)index;
        }
        int numIndex=random.nextInt(9999)+1000; //4자리 랜덤 정수를 생성
        key+=numIndex;
        return key;
    }


    public Optional<Email> getEmailandKey(EmailCheckReq emailCheckReq){
        return repository.findByEmailAndKey(emailCheckReq.getEmail(), emailCheckReq.getKey());
    }

    public void updatePasswordMail(String email, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("임시 비밀번호 전송을 위한 메일");
        message.setText("임시 비밀번호 : "+password);
        mailSender.send(message);
        log.info("email : "+ email +"로 임시 비밀번호를 전송합니다");
    }

//    public void createMail(Conference conference, User user) {
//        String mail = user.getEmail();
//        Map<String,String> check = new HashMap<>();
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(mail); //스크립트에서 보낸 메일을 받을 사용자 이메일 주소
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        log.info("date : " + sdf.format(conference.getCallStartTime()));
//        message.setSubject("안녕하세요" + user.getNickname() +"님. 북마크한 회의 시간을 알려드립니다.");
//        message.setText("현재 북마크한 "+conference.getTitle() +"모임이 "+sdf.format(conference.getCallStartTime())+"에 시작할 예정입니다.\n"
//        +"늦지 않게 참석바랍니다.");
//        mailSender.send(message);
//    }
}
