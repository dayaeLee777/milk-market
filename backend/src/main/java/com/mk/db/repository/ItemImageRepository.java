package com.mk.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mk.db.entity.ItemImage;

public interface ItemImageRepository extends JpaRepository<ItemImage, String> {
	
	
}
