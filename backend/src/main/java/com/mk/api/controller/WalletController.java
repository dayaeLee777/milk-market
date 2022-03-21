package com.mk.api.controller;


import com.mk.api.dto.request.LoginReq;
import com.mk.api.dto.request.WalletReq;
import com.mk.api.dto.response.MessageRes;
import com.mk.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<MessageRes> login(@RequestBody WalletReq walletReq) {
        MessageRes message = new MessageRes();

        return null;
    }
}
