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
	private int id;
	private String email;
	private String nickname;
	private String profileImage;
	private String profileDescription;
	
	public GetUserByProfileRes(UserDTO data) {
		setId(data.getId());
		setEmail(data.getEmail());
		setNickname(data.getNickname());
		setProfileImage(data.getProfileImage());
		setProfileDescription(data.getProfileDescription());
	}
}
