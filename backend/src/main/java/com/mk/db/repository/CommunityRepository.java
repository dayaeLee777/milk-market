package com.mk.db.repository;

import java.util.List;

import com.mk.db.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mk.db.entity.Community;

public interface CommunityRepository extends JpaRepository<Community, String> {
	
	Page<Community> findByDelYn(boolean delYn, Pageable pageable);
	
	List<Community> findByDelYnOrderByRegTimeDesc(boolean delYn);

	List<Community> findByUser(User user);

}
