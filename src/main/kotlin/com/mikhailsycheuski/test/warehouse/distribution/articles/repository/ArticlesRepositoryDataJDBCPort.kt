package com.mikhailsycheuski.test.warehouse.distribution.articles.repository

import com.mikhailsycheuski.test.warehouse.distribution.articles.repository.ArticlesRepositoryDataJDBCPort.ArticleRepresentation
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Version
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.PagingAndSortingRepository

interface ArticlesRepositoryDataJDBCPort :
  PagingAndSortingRepository<ArticleRepresentation, Long> {

  fun findByName(name: String): ArticleRepresentation?

  @Query(
    """
      SELECT 
        t_articles.id, 
        t_articles.name, 
        t_articles.stock, 
        t_articles.created, 
        t_articles.updated,
        t_articles.version
      FROM t_articles 
      WHERE id IN (:ids)  
    """
  )
  fun findAllByIds(ids: Collection<Long>): List<ArticleRepresentation>

  @Modifying
  @Query(
    """
      UPDATE t_articles 
      SET 
        stock = stock + :amount, 
        version = version + 1 
      WHERE id = :articleId
      
    """
  )
  fun increaseStockBy(articleId: Long, amount: Long)

  @Modifying
  @Query(
    """
      UPDATE t_articles 
      SET 
        stock = stock - :amount, 
        version = version + 1 
      WHERE id = :articleId
      """
  )
  fun reduceStockBy(articleId: Long, amount: Long)

  @Table("t_articles")
  data class ArticleRepresentation(
    @Id val id: Long? = null,
    val name: String,
    val stock: Long,
    @Version val version: Long? = null
  )
}