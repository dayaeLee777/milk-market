package com.mk.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.google.api.Http;
import com.mk.api.dto.request.LocationReq;
import io.swagger.v3.oas.annotations.headers.Header;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mk.api.dto.request.LoginReq;
import com.mk.api.dto.request.SignUpReq;
import com.mk.api.dto.request.UserDTO;
import com.mk.api.dto.response.BaseResponseDto;
import com.mk.api.dto.response.GetUserByProfileRes;
import com.mk.api.dto.response.MessageRes;
import com.mk.api.service.ProfileImageService;
import com.mk.api.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/api/users")
public class UserController {
	
	private final UserService userService;

	private final ProfileImageService profileImageService;

	@PostMapping("/signup")
	public ResponseEntity<MessageRes> signUp(@Valid @RequestBody SignUpReq signUpReq)  {
		MessageRes messageRes = new MessageRes();
		UserDTO userDto = new UserDTO(signUpReq);

		if (userService.createUser(userDto)) {
			messageRes.setMessage("???????????? ??????");
			messageRes.setData("user email : " + userDto.getEmail());
			return new ResponseEntity<MessageRes>(messageRes, HttpStatus.CREATED);
		}

		messageRes.setMessage("???????????? ??????");
		return new ResponseEntity<MessageRes>(messageRes, HttpStatus.BAD_REQUEST);
	}

	//???????????? ??????????????? ????????????, JWT ?????? ??? ?????? ????????? ??????
	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody LoginReq loginReq){
		HashMap<String, String> map = new HashMap<String, String>();
		
		try {
			String token = userService.login(loginReq);
			String walletAddress = userService.getWalletByEmail(loginReq.getEmail());
			String nickname = userService.getNickname(loginReq.getEmail());
			if(!token.equals("")) {
				map.put("message", "????????? ??????");
				map.put("email", loginReq.getEmail())	;
	            map.put("token",token);
				map.put("address", walletAddress);
				map.put("nickname", nickname);
				return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
			}
			map.put("message", "????????? ??????");
		}catch(Exception e){
			e.printStackTrace();
			map.put("message", "????????? ??????");
			return new ResponseEntity<Map<String,String>>(map, HttpStatus.BAD_REQUEST);
		}
		map.put("message", "????????? ??????");
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/")
	public ResponseEntity<GetUserByProfileRes> getUser (@ApiIgnore @RequestHeader("Authorization") String accessToken) {

		UserDTO user = userService.getUserById(accessToken);
		return new ResponseEntity<GetUserByProfileRes>(new GetUserByProfileRes(user), HttpStatus.OK);
	}


	@PutMapping("/delete/{id}")
	public ResponseEntity<? extends BaseResponseDto> deleteUser (@PathVariable("id") String id){
		if(userService.delete(id))
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(BaseResponseDto.of(HttpStatus.ACCEPTED.value(), "Success"));
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(BaseResponseDto.of(HttpStatus.NO_CONTENT.value(), "Fail"));
	}

//	@PutMapping("/createWallet/{id}")
//	public ReponseEntity<>
//	public ResponseEntity<? extends BaseResponseDto> delete(
//			@PathVariable("communityId") @RequestBody @ApiParam(value = "????????? ????????????ID ", required = true) String communityId){
//		if(communityService.deleteCommunity(communityId) != null)
//			return ResponseEntity.status(HttpStatus.ACCEPTED).body(BaseResponseDto.of(HttpStatus.ACCEPTED.value(), "Success"));
//		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(BaseResponseDto.of(HttpStatus.NO_CONTENT.value(), "Fail"));
//	}
//	boolean checkUser(String id, User user) {
//		if (user == null) return false;
//		return Integer.parseInt(id) == user.getId();
//	}



	
	@PostMapping("/profileImage")
	@ApiOperation(value = "????????? ????????? ?????????")
	@ApiResponses({
		@ApiResponse(code=201, message="????????? ??????????????? ?????????????????????."),
		@ApiResponse(code=409, message="???????????? ??????????????????.")
	})
	public ResponseEntity<? extends BaseResponseDto> uploadProfileImage(
		@ApiIgnore @RequestHeader("Authorization") String accessToken, 
			@ApiParam(value="????????? ?????????", required = true) @RequestPart List<MultipartFile> multipartFile) {
		profileImageService.uploadProfileImg(accessToken, multipartFile);
		return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponseDto.of(HttpStatus.CREATED.value(), "Success"));
	}

	@PutMapping("/address/update")
	@ApiOperation(value = "????????? ?????? ????????????")
	@ApiResponses({
			@ApiResponse(code=201, message="??????????????? ????????? ?????? ???????????????."),
			@ApiResponse(code=409, message="??????????????? ?????? ????????????.")
	})
	public ResponseEntity<? extends BaseResponseDto> updateLocation(
			@ApiIgnore @RequestHeader("Authorization") String accessToken,
			@RequestBody LocationReq locationReq){

		if (userService.updateLocation(accessToken, locationReq)) {
			return ResponseEntity.status(HttpStatus.OK).body(BaseResponseDto.of(HttpStatus.OK.value(), "Success"));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponseDto.of(HttpStatus.BAD_REQUEST.value(), "Fail"));
	}
}
