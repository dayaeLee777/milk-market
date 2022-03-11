package com.mk.api.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.api.dto.request.CommentModifyRequestDto;
import com.mk.api.dto.request.CommentRegisterRequestDto;
import com.mk.api.dto.response.CommentGetResponseDto;
import com.mk.db.entity.Comment;
import com.mk.db.entity.Community;
import com.mk.db.repository.CommentRepository;
import com.mk.db.repository.CommunityRepository;

import lombok.RequiredArgsConstructor;

@Service("commentService")
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

	private final CommunityRepository communityRepository;
	
	private final CommentRepository commentRepository;
	
	@Transactional
	@Override
	public Comment registerComment(CommentRegisterRequestDto commentRegisterRequestDto) {
		
		LocalDateTime currentDateTime = LocalDateTime.now();
		Community community = communityRepository.findById(commentRegisterRequestDto.getCommunityId()).orElse(null);
		
		if(community == null)
			return null;
		
		Comment comment = Comment.builder()
				.content(commentRegisterRequestDto.getContent())
				.community(community)
				.regTime(currentDateTime)
//				.user()
				.build();
		
		return commentRepository.save(comment);
	}

	@Transactional
	@Override
	public Comment modifyComment(CommentModifyRequestDto commentModifyRequestDto) {
		
		Comment comment = commentRepository.findById(commentModifyRequestDto.getCommentId()).orElse(null);
		
		if(comment == null)
			return null;
		
		comment.modifyComment(commentModifyRequestDto.getContent());
		return commentRepository.save(comment);
		
	}

	@Transactional
	@Override
	public Comment deleteComment(String commentId) {
		
		Comment comment = commentRepository.findById(commentId).orElse(null);
		
		if(comment == null)
			return null;
		
		comment.deleteComment();
		return commentRepository.save(comment);
		
	}

	@Override
	public CommentGetResponseDto getComment(String commentId) {
		
		Comment comment = commentRepository.findById(commentId).orElse(null);

		if(comment == null)
			return null;
		
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd KK:mm:ss");
		
		CommentGetResponseDto commentGetResponseDto = CommentGetResponseDto.builder()
				.commentId(commentId)
//				.userId(userId)
//				.userNickname(userNickname)
				.content(comment.getContent())
				.regTime(comment.getRegTime().format(dateTimeFormatter))
				.build();
		
		return commentGetResponseDto;
	}

}
