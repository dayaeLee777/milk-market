package com.mk.api.controller;

import com.mk.api.dto.request.LoginReq;
import com.mk.api.dto.request.SignUpReq;
import com.mk.api.dto.request.UserDTO;
import com.mk.api.dto.response.GetUserByProfileRes;
import com.mk.api.dto.response.MessageRes;
import com.mk.api.service.UserService;
import com.mk.db.entity.User;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {
	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/signup")
	public ResponseEntity<MessageRes> signUp(@Valid @RequestBody SignUpReq signUpReq)  {
		MessageRes messageRes = new MessageRes();
		UserDTO userDto = new UserDTO(signUpReq);

		if (userService.createUser(userDto)) {
			messageRes.setMessage("유저생성 성공");
			messageRes.setData("user email : " + userDto.getEmail());
			return new ResponseEntity<MessageRes>(messageRes, HttpStatus.CREATED);
		}

		messageRes.setMessage("유저생성 실패");
		return new ResponseEntity<MessageRes>(messageRes, HttpStatus.BAD_REQUEST);
	}

	//아이디와 비밀번호를 입력받고, JWT 토큰 및 유저 정보를 반환
	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody LoginReq loginReq){
		HashMap<String, String> map = new HashMap<String, String>();
		
		try {
			String token = userService.login(loginReq);
			if(!token.equals("")) {
				map.put("message", "로그인 성공");
	            map.put("token",token);
				return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
			}
			map.put("message", "로그인 실패");
		}catch(Exception e){
			e.printStackTrace();
			map.put("message", "로그인 실패");
			return new ResponseEntity<Map<String,String>>(map, HttpStatus.BAD_REQUEST);
		}
		map.put("message", "로그인 실패");
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/{id}")
	public ResponseEntity<GetUserByProfileRes> getUser (@PathVariable("id") String id) {
		UserDTO user = userService.getUserById(id);
		return new ResponseEntity<GetUserByProfileRes>(new GetUserByProfileRes(user), HttpStatus.OK);
	}
	

	
//	boolean checkUser(String id, User user) {
//		if (user == null) return false;
//		return Integer.parseInt(id) == user.getId();
//	}
	
}
