package com.mk.api.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.api.dto.request.CommentModifyRequestDto;
import com.mk.api.dto.request.CommentRegisterRequestDto;
import com.mk.api.dto.response.CommentGetListResponseDto;
import com.mk.api.dto.response.CommentGetResponseDto;
import com.mk.api.dto.response.CommunityGetListResponseDto;
import com.mk.db.entity.Comment;
import com.mk.db.entity.Community;
import com.mk.db.entity.User;
import com.mk.db.repository.CommentRepository;
import com.mk.db.repository.CommunityRepository;

import lombok.RequiredArgsConstructor;

@Service("commentService")
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

	private final CommunityRepository communityRepository;
	
	private final CommentRepository commentRepository;
	
	private final JwtTokenService jwtTokenService;
	
	@Transactional
	@Override
	public Comment registerComment(String accessToken, CommentRegisterRequestDto commentRegisterRequestDto) {

		User user = jwtTokenService.convertTokenToUser(accessToken);
		LocalDateTime currentDateTime = LocalDateTime.now();
		Community community = communityRepository.findById(commentRegisterRequestDto.getCommunityId()).orElse(null);
		
		if(community == null)
			return null;
		
		Comment comment = Comment.builder()
				.content(commentRegisterRequestDto.getContent())
				.community(community)
				.regTime(currentDateTime)
				.user(user)
				.build();
		
		return commentRepository.save(comment);
	}
	
	@Override
	public CommentGetResponseDto getComment(String commentId) {
		
		Comment comment = commentRepository.findById(commentId).orElse(null);

		if(comment == null)
			return null;
		
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		CommentGetResponseDto commentGetResponseDto = CommentGetResponseDto.builder()
				.commentId(commentId)
				.userId(comment.getUser().getId())
				.userNickname(comment.getUser().getNickname())
				.content(comment.getContent())
				.regTime(comment.getRegTime().format(dateTimeFormatter))
				.profileImage(comment.getUser().getProfileImage())
				.build();
		
		return commentGetResponseDto;
	}

	@Override
	public CommentGetListResponseDto getCommentList(String communityId) {
		
		Community community = communityRepository.findById(communityId).orElse(null);
		
		if(community == null)
			return null;
		
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		List<CommentGetResponseDto> commentList = new ArrayList<CommentGetResponseDto>();
		
		commentRepository.findByCommunityAndDelYnOrderByRegTime(community, false).forEach(comment -> {
			CommentGetResponseDto commentGetResponseDto = CommentGetResponseDto.builder()
					.commentId(comment.getId())
					.userId(comment.getUser().getId())
					.userNickname(comment.getUser().getNickname())
					.content(comment.getContent())
					.regTime(comment.getRegTime().format(dateTimeFormatter))
					.profileImage(comment.getUser().getProfileImage())
					.build();
			
			commentList.add(commentGetResponseDto);
		});
		CommentGetListResponseDto commentGetListResponseDto = CommentGetListResponseDto.builder()
				.commentGetListResponseDto(commentList)
				.build();
		
		return commentGetListResponseDto;
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

}
