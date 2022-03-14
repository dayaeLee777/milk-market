package com.mk.db.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.mk.db.code.Code;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@NoArgsConstructor
public class Item extends BaseEntity {
	
	private Code division;
	
	private String itemName;
	
	private Code category;
	
	private int price;
	
	private String description;
	
	private LocalDate regDate;

	private String position;
	
	private LocalDateTime rentStartDate;

	private LocalDateTime rentEndDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column(name="del_yn", columnDefinition="BOOLEAN DEFAULT false")
	private boolean delYn;

	public Item(Code division, String itemName, Code category, int price, String description, LocalDate regDate,
			String position, LocalDateTime rentStartDate, LocalDateTime rentEndDate, User user, boolean delYn) {
		super();
		this.division = division;
		this.itemName = itemName;
		this.category = category;
		this.price = price;
		this.description = description;
		this.regDate = regDate;
		this.position = position;
		this.rentStartDate = rentStartDate;
		this.rentEndDate = rentEndDate;
		this.user = user;
		this.delYn = delYn;
	}
	
}
