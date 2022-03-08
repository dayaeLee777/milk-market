package com.mk.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mk.db.entity.Community;

public interface CommunityRepository extends JpaRepository<Community, String> {

}
