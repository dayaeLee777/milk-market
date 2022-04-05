package com.mk.db.entity;

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
public class Interest extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item;
	
	@Column(name="del_yn", columnDefinition="BOOLEAN DEFAULT false")
	private boolean delYn;

	@Column(name="interest_like")
	private boolean interestLike;


	public Interest(User user, Item item, boolean delYn, boolean interestLike) {
		this.user = user;
		this.item = item;
		this.delYn = delYn;
		this.interestLike = interestLike;
	}

	public void toogleInterest() {
		this.interestLike = !this.interestLike;
	}

	public void deleteInterest(){
		this.delYn = true;
	}


}
