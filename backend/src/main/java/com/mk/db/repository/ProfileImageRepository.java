package com.mk.db.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mk.db.entity.ProfileImage;
import com.mk.db.entity.User;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, String> {
	
	Optional<ProfileImage> findByUser(User user);
	
}
