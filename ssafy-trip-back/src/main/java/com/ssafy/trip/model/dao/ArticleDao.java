package com.ssafy.trip.model.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.model.dto.Article;

@Mapper
public interface ArticleDao {
	
	public int insert(Article article);
	
	public int delete(int no);
	
	public int update(Article article);
	
	public Article selectOne(int no);
	
	public List<Article> selectAll();
	
	public List<Article> selectRecent();
	
}
