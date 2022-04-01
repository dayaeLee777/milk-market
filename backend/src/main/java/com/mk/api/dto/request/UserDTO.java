package com.mk.api.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	private String id;
	private String password;
	private String nickname;
	private String email;	
	private String bcode;
	private String bname;
	private String sigungu;
	private String profileImage;
	private String profileDescription;
	private List<String> roles;
	
	public UserDTO(SignUpReq data) {
		setPassword(data.getPassword());
		setNickname(data.getNickname());
		setEmail(data.getEmail());
		if(data.getProfileImage()==null)
			setProfileImage("");
		else
			setProfileImage(data.getProfileImage());
		setBcode(data.getBcode());
		setBname(data.getBname());
		setSigungu(data.getSigungu());
		setProfileDescription("");
		setRoles(new ArrayList<String>());
		getRoles().add("ROLE_USER");
	}

}
