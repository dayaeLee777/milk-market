package com.mk.api.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.mk.api.dto.request.CommentRegisterRequestDto;
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

}
