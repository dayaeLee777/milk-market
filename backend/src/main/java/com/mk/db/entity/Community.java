package com.mk.db.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Community extends BaseEntity  {
	
	private String title;
	
	private String content;
	
	@Column(name="hit", columnDefinition="INT DEFAULT 0")
	private int hit;
	
	private LocalDateTime regTime;


	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "user_id")
//	private User user;
	
}
