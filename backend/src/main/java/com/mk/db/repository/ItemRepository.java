package com.mk.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mk.db.entity.Item;

public interface ItemRepository extends JpaRepository<Item, String> {
	
	
}
