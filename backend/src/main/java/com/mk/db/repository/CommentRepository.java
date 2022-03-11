package com.mk.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mk.db.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, String> {

}
