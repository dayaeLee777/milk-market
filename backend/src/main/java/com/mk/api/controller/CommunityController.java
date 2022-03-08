package com.mk.api.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mk.api.service.CommunityService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@Api(value = "커뮤니티 API", tags = { "Community" })
@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class CommunityController {
	
	private final CommunityService communityService;
	
}
