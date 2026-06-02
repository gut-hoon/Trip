package com.ssafy.trip.restcontroller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.model.dto.Comment;
import com.ssafy.trip.model.service.CommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController implements RestControllerHelper {
	
	private final CommentService cService;
	
	@PostMapping("/regist")
	private ResponseEntity<?> registComment(@RequestBody Comment comment){
		try {
			comment.setRegDate(LocalDateTime.now());
			int result = cService.registComment(comment);
			return handleSuccess(result, HttpStatus.CREATED);
		}catch (Exception e) {
			e.printStackTrace();
			return handleFail(e);
		}
	}
	
	   @DeleteMapping("/delete/{no}")
	    public ResponseEntity<?> delete(@PathVariable int no) {
	        try {
	            int result = cService.deleteComment(no);
	            return handleSuccess(result, HttpStatus.OK);
	        } catch (Exception e) {
	        	e.printStackTrace();
	            return ResponseEntity.status(500).body("댓글 삭제 실패");
	        }
	    }
	
	    @PutMapping("/update")
	    public ResponseEntity<?> update(@RequestBody Comment comment) {
	        try {
	        	comment.setRegDate(LocalDateTime.now());
	        	int result = cService.updateComment(comment);
	            return handleSuccess(result, HttpStatus.OK);
	        } catch (Exception e) {
	        	e.printStackTrace();
	            return ResponseEntity.status(500).body("댓글 수정 실패");
	        }
	    }
	    @GetMapping("/article/{articleNo}")
	    public ResponseEntity<?> getComments(@PathVariable int articleNo) {
	        try {
	            List<Comment> comments = cService.selectByArticleNo(articleNo);
	            return handleSuccess(comments);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return handleFail(e);
	        }
	    }
}
