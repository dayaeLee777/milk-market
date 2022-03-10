package com.mk.api.service;

import com.mk.api.dto.request.CommentRegisterRequestDto;
import com.mk.db.entity.Comment;

public interface CommentService {
	
	Comment registerComment(CommentRegisterRequestDto commentRegisterRequestDto);
}
