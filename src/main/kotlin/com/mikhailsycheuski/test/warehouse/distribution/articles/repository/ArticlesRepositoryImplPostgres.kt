package com.mikhailsycheuski.test.warehouse.distribution.articles.repository

import com.mikhailsycheuski.test.warehouse.core.extensions.unwrap
import com.mikhailsycheuski.test.warehouse.distribution.articles.repository.ArticlesRepositoryDataJDBCPort.ArticleRepresentation
import com.mikhailsycheuski.test.warehouse.domain.articles.model.Article
import com.mikhailsycheuski.test.warehouse.domain.articles.ArticlesRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class ArticlesRepositoryImplPostgres(
  private val articlesRepositoryDataJDBCPort: ArticlesRepositoryDataJDBCPort
) : ArticlesRepository {

  @Transactional(readOnly = true)
  override fun findById(articleId: Long): Article? =
    articlesRepositoryDataJDBCPort
      .findById(articleId)
      .unwrap()
      ?.asDomain()

  @Transactional(readOnly = true)
  override fun findAll(): List<Article> =
    articlesRepositoryDataJDBCPort.findAll().map { it.asDomain() }

  @Transactional(readOnly = true)
  override fun findAllByIds(articleIds: Collection<Long>): List<Article> =
    articlesRepositoryDataJDBCPort
      .findAllByIds(articleIds)
      .map { it.asDomain() }

  @Transactional(readOnly = true)
  override fun findByName(articleName: String): Article? =
    articlesRepositoryDataJDBCPort
      .findByName(articleName)
      ?.asDomain()

  @Transactional
  override fun save(article: Article) {
    articlesRepositoryDataJDBCPort.save(article.asRepresentation())
  }

  @Transactional
  override fun update(article: Article): Article =
    article
      .asRepresentation()
      .let { articlesRepositoryDataJDBCPort.save(it) }
      .asDomain()

  @Transactional
  override fun increaseStockBy(articleId: Long, amount: Long) {
    articlesRepositoryDataJDBCPort.increaseStockBy(articleId, amount)
  }

  @Transactional
  override fun reduceStockBy(articleId: Long, amount: Long) {
    articlesRepositoryDataJDBCPort.reduceStockBy(articleId, amount)
  }

  private fun Article.asRepresentation() =
    ArticleRepresentation(
      id,
      name,
      stock
    )

  private fun ArticleRepresentation.asDomain() =
    Article(
      id,
      name,
      stock
    )
}