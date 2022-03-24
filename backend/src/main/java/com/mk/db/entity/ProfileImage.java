package com.mk.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Builder
@Entity
@NoArgsConstructor
public class ProfileImage extends BaseEntity {

	private String originFileName;
	
	private String newFileName;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column(name="del_yn", columnDefinition="BOOLEAN DEFAULT false")
	private boolean delYn;

	public ProfileImage(String originFileName, String newFileName, User user, boolean delYn) {
		super();
		this.originFileName = originFileName;
		this.newFileName = newFileName;
		this.user = user;
		this.delYn = delYn;
	}
	
	public void modifyProfileImage(String originFileName, String newFileName) {
		
		this.originFileName = originFileName;
		this.newFileName = newFileName;
		
	}
}
