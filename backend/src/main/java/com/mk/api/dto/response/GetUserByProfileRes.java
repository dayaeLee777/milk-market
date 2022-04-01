package com.mk.api.dto.response;


import com.mk.api.dto.request.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetUserByProfileRes {
	private String id;
	private String email;
	private String nickname;
	private String bcode;
	private String bname;
	private String sigungu;
	private String profileImage;
	private String profileDescription;
	
	public GetUserByProfileRes(UserDTO data) {
		setId(data.getId());
		setEmail(data.getEmail());
		setNickname(data.getNickname());
		setBcode(data.getBcode());
		setBname(data.getBname());
		setSigungu(data.getSigungu());
		setProfileImage(data.getProfileImage());
		setProfileDescription(data.getProfileDescription());
	}
}
