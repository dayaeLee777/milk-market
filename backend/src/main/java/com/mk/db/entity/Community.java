package com.mk.db.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Builder
@Entity
@NoArgsConstructor
public class Community extends BaseEntity  {
	
	private String title;
	
	private String content;
	
	private int hit;
	
	private LocalDateTime regTime;

	@Builder
	public Community(String title, String content, int hit, LocalDateTime regTime) {
		super();
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.regTime = regTime;
	}
	
	public void plusCommunityHit() {
		this.hit += 1;
	}
	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "user_id")
//	private User user;
	
}
