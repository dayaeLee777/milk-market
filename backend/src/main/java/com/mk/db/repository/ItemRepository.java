package com.mk.db.repository;

import com.mk.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mk.db.entity.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, String> {

    List<Item> findByUser(User user);
	
}
