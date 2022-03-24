package com.mk.api.controller;


import com.mk.api.dto.request.EmailCheckReq;
import com.mk.api.dto.request.EmailReq;
import com.mk.api.dto.request.UpdatePasswordReq;
import com.mk.api.error.exception.AlreadyExistEmailException;
import com.mk.api.service.EmailService;
import com.mk.api.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;



@Slf4j
@Controller
@CrossOrigin("*")
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    UserService userService;
    @Autowired
    EmailService emailService;



    @PostMapping("/key") // AJAX와 URL을 매핑시켜줌
    @ResponseBody  //AJAX후 값을 리턴하기위해 작성
    public ResponseEntity<Map<String,String>> createKey(@RequestBody EmailReq emailReq) {
        log.info("emailReq : "+emailReq.getEmail());
        Map<String, String> map = new HashMap<>();

        try{
            if(userService.getUserByEmail(emailReq.getEmail())!=null){
                throw new AlreadyExistEmailException();
            }
            emailService.createMail(emailReq);
            map.put("msg","이메일에 키를 보냈습니다.");
            return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
        }catch (AlreadyExistEmailException e){
            e.printStackTrace();
            map.put("msg","이디 등록된 이메일이 있습니다. 확인 후 이용해주세요");
        } catch (Exception e){
            e.printStackTrace();
            map.put("msg", "잘못된 이메일 형식이거나 이미 등록된 이메일이 있습니다.");
        }
        return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/key-check") // AJAX와 URL을 매핑시켜줌
    @ResponseBody  //AJAX후 값을 리턴하기위해 작성
    public ResponseEntity<Map<String,String>> getEmailAndKey(@RequestBody EmailCheckReq emailCheckReq) {
        log.info("emailReq : "+emailCheckReq.getEmail());
        Map<String, String> map = new HashMap<>();
        try{
            if(emailService.getEmailandKey(emailCheckReq).isPresent()){
                map.put("msg","인증된 이메일입니다.");
                return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        map.put("msg", "키값이 잘못됐습니다. 확인 후 다시 시도하시길 바랍니다.");
        return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
    }

    @RequestMapping("/pwdcheck/{email}") // AJAX와 URL을 매핑시켜줌
    @ResponseBody  //AJAX후 값을 리턴하기위해 작성
    public ResponseEntity<Map<String,String>> updatePwdByMail(@PathVariable("email") String email) {
        UpdatePasswordReq updatePasswordReq = new UpdatePasswordReq();
        log.info("email: "+email);
        Map<String,String> map = new HashMap<>();


        try {
            String password = emailService.createRandomKey();
        	updatePasswordReq.setNewPassword(password);
            if(userService.updatePassword(updatePasswordReq, email)){
                map.put("message", "임시 비밀번호 전송 성공");
                emailService.updatePasswordMail(email,password);
                return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
            }
        }catch(Exception e){
            e.printStackTrace();
            map.put("message", "임시 비밀번호 전송 실패");
        }
        return new ResponseEntity<Map<String,String>>(map, HttpStatus.BAD_REQUEST);

    }


}
