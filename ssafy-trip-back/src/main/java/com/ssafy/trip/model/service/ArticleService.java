package com.ssafy.trip.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.trip.model.dao.ArticleDao;
import com.ssafy.trip.model.dto.Article;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleService {
	
	private final ArticleDao aDao;
	
	public int registArticle(Article article) {
		return aDao.insert(article);
	}
	
	public int removeArticle(int no) {
		return aDao.delete(no);
	}
	
	public int updateArticle(Article article) {
		return aDao.update(article);
	}
	
	public Article articleDetail(int no) {
		return aDao.selectOne(no);
	}
	
	public List<Article> articleList(){
		return aDao.selectAll();
	}

	public List<Article> getRecentPost(){
		return aDao.selectRecent();
	}

}
