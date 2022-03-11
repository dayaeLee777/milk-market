package com.mk.db.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@NoArgsConstructor
public class Community extends BaseEntity  {
	
	private String title;
	
	private String content;
	
	private int hit;
	
	private LocalDateTime regTime;

	@Column(name="del_yn", columnDefinition="BOOLEAN DEFAULT false")
	private boolean delYn;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	public void plusCommunityHit() {
		this.hit += 1;
	}
	
	public void modifyCommunity(String title, String content) {
		this.title = title;
		this.content = content;
	}
	
	public void deleteCommunity() {
		this.delYn = true;
	}

	public Community(String title, String content, int hit, LocalDateTime regTime, boolean delYn, User user) {
		super();
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.regTime = regTime;
		this.delYn = delYn;
		this.user = user;
	}
	
}
