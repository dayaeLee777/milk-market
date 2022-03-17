package com.mk.api.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	private String id;
	private String password;
	private String nickname;
	private String email;
	private int gender;
	private Date age;
	private int hostPoint;
	private int guestPoint;
	private String profileImage;
	private String profileDescription;
	private List<String> roles;
	
	public UserDTO(SignUpReq data) {
		setPassword(data.getPassword());
		setNickname(data.getNickname());
		setEmail(data.getEmail());
		setGender(0);
		setAge(new Date());
		setHostPoint(0);
		setGuestPoint(0);
		if(data.getProfileImage()==null)
			setProfileImage("");
		else
			setProfileImage(data.getProfileImage());
		setProfileDescription("");
		setRoles(new ArrayList<String>());
		getRoles().add("ROLE_USER");
	}

	@Override
	public String toString() {
		return "UserDTO{" +
				"id=" + id +
				", password='" + password + '\'' +
				", nickname='" + nickname + '\'' +
				", email='" + email + '\'' +
				", gender=" + gender +
				", age=" + age +
				", hostPoint=" + hostPoint +
				", guestPoint=" + guestPoint +
				", profileImage='" + profileImage + '\'' +
				", profileDescription='" + profileDescription + '\'' +
				", roles=" + roles +
				'}';
	}
}
