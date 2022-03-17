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
public class ItemImage extends BaseEntity {

	private String originFileName;
	
	private String newFileName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item;
	
	@Column(name="del_yn", columnDefinition="BOOLEAN DEFAULT false")
	private boolean delYn;

	public ItemImage(String originFileName, String newFileName, Item item, boolean delYn) {
		super();
		this.originFileName = originFileName;
		this.newFileName = newFileName;
		this.item = item;
		this.delYn = delYn;
	}

}
