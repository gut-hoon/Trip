package com.ssafy.trip.restcontroller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.model.dto.Article;
import com.ssafy.trip.model.service.ArticleService;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/article")
@RequiredArgsConstructor
@Tag(name = "ArticleRestController", description = "게시글에 관한 기능 제공")
public class ArticleRestController implements RestControllerHelper{
	
	private final ArticleService aService;
	
	@Operation(summary = "게시글 등록", description = "새로운 게시글을 등록한다.")
	@ApiResponses({@ApiResponse(responseCode = "201", description = "게시글 등록 성공"),
		@ApiResponse(responseCode = "500", description = "게시글 등록 실패")})
	@PostMapping("/insert")
	private ResponseEntity<?> registArticle(@RequestBody Article article){
		try {
			System.out.println(article.toString());
			int result = aService.registArticle(article);
			return handleSuccess(result, HttpStatus.CREATED);
		}catch (Exception e) {
			e.printStackTrace();
			return handleFail(e);
		}
	}
	
	
	
	@Operation(summary = "게시글 수정", description = "게시글을 수정.")
	@ApiResponses({@ApiResponse(responseCode = "200", description = "게시글 수정 성공"), 
		@ApiResponse(responseCode = "500", description = "게시글 수정 실패")})
	@PutMapping("/{id}")
	private ResponseEntity<?> updateArticle(@PathVariable int id, @RequestBody Article updatedPost){
		try {
			updatedPost.setNo(id);
			int result = aService.updateArticle(updatedPost);
			System.out.println(updatedPost.toString());
			return handleSuccess(result, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return handleFail(e);
		}
	}
	
	@Operation(summary = "게시글 삭제", description = "게시글을 삭제한다.")
	@ApiResponses({@ApiResponse(responseCode = "200", description = "게시글 삭제 성공"), 
		@ApiResponse(responseCode = "500", description = "게시글 삭제 실패")})
	@DeleteMapping("/{no}/delete")
	private ResponseEntity<?> deleteArticle(@PathVariable int no){
		try {
			int result = aService.removeArticle(no);
			return handleSuccess(result, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return handleFail(e);
		}
	}
	
	@Operation(summary = "게시글 조회", description = "특정 게시글을 불러온다")
	@ApiResponses({@ApiResponse(responseCode = "200", description = "게시글 조회 성공"), 
		@ApiResponse(responseCode = "500", description = "게시글 조회 실패")})
	@GetMapping("/{no}")
	private ResponseEntity<?> selectOne(@PathVariable int no){
		try {
			Article result = aService.articleDetail(no);
			return handleSuccess(result, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return handleFail(e);
		}
	}
	
	@Operation(summary = "게시글 전체 조회", description = "모든 게시글을 불러온다")
	@ApiResponses({@ApiResponse(responseCode = "200", description = "게시글 전체조회 성공"), 
		@ApiResponse(responseCode = "500", description = "게시글 전체조회 실패")})
	@GetMapping("/select")
	private ResponseEntity<?> selectAll(){
		try {
			List<Article> articles = aService.articleList();
			return new ResponseEntity<>(articles, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/recentPost")
	private ResponseEntity<?> recentPost(){
		try {
			List<Article> articles = aService.getRecentPost();
			return new ResponseEntity<>(articles, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
