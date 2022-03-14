package com.mk.api.service;

import com.mk.api.dto.request.CommentModifyRequestDto;
import com.mk.api.dto.request.CommentRegisterRequestDto;
import com.mk.api.dto.response.CommentGetListResponseDto;
import com.mk.api.dto.response.CommentGetResponseDto;
import com.mk.db.entity.Comment;

public interface CommentService {
	
	Comment registerComment(String accessToken, CommentRegisterRequestDto commentRegisterRequestDto);
	
	CommentGetResponseDto getComment(String commentId);
	
	CommentGetListResponseDto getCommentList(String communityId);
	
	Comment modifyComment(CommentModifyRequestDto commentModifyRequestDto);
	
	Comment deleteComment(String commentId);
	
}
