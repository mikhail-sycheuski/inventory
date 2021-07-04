package com.mikhailsycheuski.test.warehouse.distribution.articles.service

import com.mikhailsycheuski.test.warehouse.domain.articles.model.Article
import com.mikhailsycheuski.test.warehouse.domain.articles.model.ArticleUpdateRequest


interface ArticlesService {

  fun findArticleById(articleId: Long): Article?
  fun findArticleByName(articleName: String): Article?
  // TODO: change to paginated request/response
  fun findAllArticles(): List<Article>
  fun addArticle(article: Article): Long
  fun updateArticle(articleUpdateRequest: ArticleUpdateRequest)
}