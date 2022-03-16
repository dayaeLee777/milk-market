package com.mk.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mk.db.entity.Item;
import com.mk.db.entity.ItemImage;

public interface ItemImageRepository extends JpaRepository<ItemImage, String> {
	
	List<ItemImage> findByItem(Item item);
}
