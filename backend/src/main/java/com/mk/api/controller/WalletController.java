package com.mk.api.controller;


import com.mk.api.dto.request.LoginReq;
import com.mk.api.dto.request.WalletReq;
import com.mk.api.dto.response.MessageRes;
import com.mk.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.Message;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/wallets")
public class WalletController {
    private UserService userService;

    @Autowired
    public WalletController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseEntity<MessageRes> createWallet(@RequestBody WalletReq walletReq) {
        MessageRes message = new MessageRes();
        if (userService.createWallet(walletReq)) {
            message.setMessage("지갑 생성 성공");
            message.setData(walletReq.getAddress());
            return new ResponseEntity<MessageRes>(message, HttpStatus.CREATED);
        }
        message.setMessage("지값 생성 실패");
        return new ResponseEntity<MessageRes>(message, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/check")
    public ResponseEntity<Map<String, String>> checkPrivateKey(@RequestBody WalletReq walletReq) {
        HashMap<String, String> map = new HashMap<>();
        if (userService.checkPrivateKey(walletReq)) {
            map.put("message", "확인 성공");
            map.put("is_login", "true");
            return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
        }
        map.put("message", "privateKey 불일치");
        return new ResponseEntity<Map<String, String>>(map, HttpStatus.BAD_REQUEST);
    }
}
