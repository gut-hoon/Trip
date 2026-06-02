package com.ssafy.trip.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.trip.model.dao.CommentDao;
import com.ssafy.trip.model.dto.Comment;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
	
	private final CommentDao cDao;
	
	public int registComment(Comment comment) throws Exception {
		int result = cDao.insert(comment);
		if(result == 0) {
			System.out.println("댓글 등록에 실패했습니다.");
			throw new Exception();
		}else {
			return result;
		}
	}
	
	public int deleteComment(int no) throws Exception {
		int result = cDao.delete(no);
		if(result == 0) {
			System.out.println("댓글 삭제에 실패했습니다.");
			throw new Exception();
		}else {
			return result;
		}
	}
	
	public int updateComment(Comment comment) throws Exception {
		int result = cDao.update(comment);
		if(result == 0) {
			System.out.println("댓글 수정에 실패했습니다.");
			throw new Exception();
		}else {
			return result;
		}
	}
	
	public List<Comment> selectByArticleNo(int articleNo) throws Exception {
	    List<Comment> list = cDao.selectByArticleNo(articleNo);
	    if (list == null) {
	        throw new Exception("댓글 조회에 실패했습니다.");
	    }
	    return list;
	}
}
