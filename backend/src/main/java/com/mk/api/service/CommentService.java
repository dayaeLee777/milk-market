package com.mk.api.service;

import java.util.List;

import com.mk.api.dto.request.CommentModifyRequestDto;
import com.mk.api.dto.request.CommentRegisterRequestDto;
import com.mk.api.dto.response.CommentGetResponseDto;
import com.mk.db.entity.Comment;

public interface CommentService {
	
	Comment registerComment(CommentRegisterRequestDto commentRegisterRequestDto);
	
	CommentGetResponseDto getComment(String commentId);
	
	List<CommentGetResponseDto> getCommentList(String communityId);
	
	Comment modifyComment(CommentModifyRequestDto commentModifyRequestDto);
	
	Comment deleteComment(String commentId);
	
}
