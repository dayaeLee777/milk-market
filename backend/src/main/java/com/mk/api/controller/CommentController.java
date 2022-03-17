package com.mk.api.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mk.api.dto.request.CommentModifyRequestDto;
import com.mk.api.dto.request.CommentRegisterRequestDto;
import com.mk.api.dto.response.BaseResponseDto;
import com.mk.api.dto.response.CommentGetListResponseDto;
import com.mk.api.dto.response.CommentGetResponseDto;
import com.mk.api.service.CommentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "댓글 API", tags = { "Comment" })
@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {
	
	private final CommentService commentService;
	
	@PostMapping
	@ApiOperation(value = "댓글 등록하기", notes="<strong>회원이 작성한 댓글을 등록한다.</strong><br/>")
	@ApiResponses({
		@ApiResponse(code=201, message="댓글이 정상적으로 등록되었습니다."),
		@ApiResponse(code=400, message="댓글 등록을 실패했습니다.")
	})
	public ResponseEntity<? extends BaseResponseDto> regist(
		@ApiIgnore @RequestHeader("Authorization") String accessToken,
		@RequestBody @ApiParam(value = "등록할 댓글", required = true) CommentRegisterRequestDto commentRegisterRequestDto) {
		if(commentService.registerComment(accessToken, commentRegisterRequestDto) != null)
			return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponseDto.of(HttpStatus.CREATED.value(), "Success"));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponseDto.of(HttpStatus.BAD_REQUEST.value(), "Fail"));
	}
	
	@GetMapping("/{commentId}")
	@ApiOperation(value = "댓글 불러오기", notes="<strong>commentId 에 해당하는 댓글를 불러온다.</strong>")
	@ApiResponses({
		@ApiResponse(code=200, message="댓글을 정상적으로 조회하였습니다."),
		@ApiResponse(code=204, message="댓글 조회를 실패했습니다.")
	})
	public ResponseEntity<? extends BaseResponseDto> getComment(
			@PathVariable("commentId") @RequestBody @ApiParam(value = "조회할 댓글 ID", required = true) String commentId){
		CommentGetResponseDto commentGetResponseDto = commentService.getComment(commentId);
		if(commentGetResponseDto != null)
			return ResponseEntity.status(HttpStatus.OK).body(commentGetResponseDto);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(BaseResponseDto.of(HttpStatus.NO_CONTENT.value(), "Fail"));
	}
	
	@GetMapping("/list/{communityId}")
	@ApiOperation(value = "댓글목록 불러오기", notes="<strong>communityId에 해당하는 댓글 목록을 불러온다.</strong>")
	@ApiResponses({
		@ApiResponse(code=200, message="댓글을 정상적으로 조회하였습니다."),
		@ApiResponse(code=204, message="댓글 조회를 실패했습니다.")
	})
	public ResponseEntity<? extends BaseResponseDto> getCommentList(
			@PathVariable("communityId") @RequestBody @ApiParam(value = "조회할 커뮤니티 ID", required = true) String communityId){
		CommentGetListResponseDto commentGetListResponseDto = commentService.getCommentList(communityId);
		if(commentGetListResponseDto != null)
			return ResponseEntity.status(HttpStatus.OK).body(commentGetListResponseDto);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(BaseResponseDto.of(HttpStatus.NO_CONTENT.value(), "Fail"));
	}
	
	@PutMapping
	@ApiOperation(value = "댓글 수정하기", notes="<strong>작성한 댓글을 수정한다.</strong>")
	@ApiResponses({
		@ApiResponse(code=201, message="댓글이 정상적으로 수정되었습니다."),
		@ApiResponse(code=204, message="댓글 수정을 실패했습니다.")
	})
	public ResponseEntity<? extends BaseResponseDto> modify(
			@RequestBody @ApiParam(value = "등록할 댓글", required = true) CommentModifyRequestDto commentModifyRequestDto){
		if(commentService.modifyComment(commentModifyRequestDto) != null)
			return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponseDto.of(HttpStatus.CREATED.value(), "Success"));
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(BaseResponseDto.of(HttpStatus.NO_CONTENT.value(), "Fail"));
	}
	
	@PutMapping("/delete/{commentId}")
	@ApiOperation(value = "댓글 삭제하기", notes="<strong>작성한 댓글을 삭제한다.</strong>")
	@ApiResponses({
		@ApiResponse(code=202, message="댓글이 정상적으로 삭제되었습니다."),
		@ApiResponse(code=204, message="댓글 삭제를 실패했습니다.")
	})
	public ResponseEntity<? extends BaseResponseDto> delete(
			@PathVariable("commentId") @RequestBody @ApiParam(value = "삭제할 댓글 ID ", required = true) String commentId){
		if(commentService.deleteComment(commentId) != null)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(BaseResponseDto.of(HttpStatus.ACCEPTED.value(), "Success"));
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(BaseResponseDto.of(HttpStatus.NO_CONTENT.value(), "Fail"));
	}
	
}
