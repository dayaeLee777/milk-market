package com.mk.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mk.db.entity.Comment;
import com.mk.db.entity.Community;

public interface CommentRepository extends JpaRepository<Comment, String> {
	
	List<Comment> findByCommunityAndDelYnOrderByRegTime(Community community, boolean delYn);
	
}
