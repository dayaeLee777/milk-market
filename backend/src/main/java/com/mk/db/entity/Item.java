package com.mk.db.entity;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.mk.api.dto.request.ItemModifyRequestDto;
import com.mk.db.code.Code;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@NoArgsConstructor
public class Item extends BaseEntity {

	@Enumerated(EnumType.STRING)
	private Code division;
	
	private String itemName;

	@Enumerated(EnumType.STRING)
	private Code category;
	
	private int price;
	
	private String description;
	
	private LocalDateTime regDate;

	private int position;
	
	private LocalDateTime rentStartDate;

	private LocalDateTime rentEndDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column(name="del_yn", columnDefinition="BOOLEAN DEFAULT false")
	private boolean delYn;

	public Item(Code division, String itemName, Code category, int price, String description, LocalDateTime regDate,
			int position, LocalDateTime rentStartDate, LocalDateTime rentEndDate, User user, boolean delYn) {
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
	
	public void setRentDate(LocalDateTime rentStartDate, LocalDateTime rentEndDate) {
		this.rentStartDate = rentStartDate;
		this.rentEndDate = rentEndDate;
	}	
	
	public void modifyItem(ItemModifyRequestDto itemModifyRequestDto) {
		this.division = itemModifyRequestDto.getDivision();
		this.itemName = itemModifyRequestDto.getItemName();
		this.category = itemModifyRequestDto.getCategory();
		this.price = itemModifyRequestDto.getPrice();
		this.description = itemModifyRequestDto.getDescription();
		
		if(itemModifyRequestDto.getDivision() == Code.A01) {
			this.rentStartDate = itemModifyRequestDto.getRentStartDate();
			this.rentEndDate = itemModifyRequestDto.getRentEndDate();			
		} else {
			this.rentStartDate = null;
			this.rentEndDate = null;			
		}
	}

	public void deleteItem() {
		this.delYn = true;
	}

	
}
