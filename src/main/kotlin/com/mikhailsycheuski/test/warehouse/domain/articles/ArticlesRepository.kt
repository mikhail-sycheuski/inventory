package com.mikhailsycheuski.test.warehouse.domain.articles

import com.mikhailsycheuski.test.warehouse.domain.articles.model.Article


interface ArticlesRepository {

  fun findById(articleId: Long): Article?
  fun existsById(articleId: Long): Boolean = findById(articleId)?.let { true } ?: false
  fun findAllByIds(articleIds: Collection<Long>): List<Article>
  fun findByName(articleName: String): Article?
  fun existsByName(articleName: String): Boolean = findByName(articleName)?.let { true } ?: false
  fun save(article: Article)
  fun update(article: Article): Article
  fun increaseStockBy(articleId: Long, amount: Long)
  fun reduceStockBy(articleId: Long, amount: Long)
}