package com.mikhailsycheuski.test.warehouse.domain.articles.usecases

import com.mikhailsycheuski.test.warehouse.domain.articles.ArticlesRepository
import com.mikhailsycheuski.test.warehouse.domain.articles.model.ArticleUpdateRequest
import com.mikhailsycheuski.test.warehouse.domain.exception.DomainObjectNotFountException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class UpdateArticleUseCase(
  private val articlesRepository: ArticlesRepository
) {

  @Transactional
  fun execute(articleUpdateRequest: ArticleUpdateRequest) {
    articlesRepository
      .findById(articleUpdateRequest.id)
      ?.let { articlesRepository.increaseStockBy(it.id!!, articleUpdateRequest.stock) }
      ?: throw DomainObjectNotFountException("Article with id[${articleUpdateRequest.id}] doesn't exist")
  }
}