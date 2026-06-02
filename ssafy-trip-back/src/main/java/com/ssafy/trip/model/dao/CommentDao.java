package com.ssafy.trip.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.model.dto.Comment;

@Mapper
public interface CommentDao {
	
	public int insert(Comment comment);
	
	public int delete(int no);
	
	public int update(Comment comment);
	public List<Comment> selectByArticleNo(int articleNo);
}
