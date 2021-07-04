package com.mikhailsycheuski.test.warehouse.domain.articles.usecases

import com.mikhailsycheuski.test.warehouse.domain.articles.ArticlesRepository
import com.mikhailsycheuski.test.warehouse.domain.articles.model.Article
import com.mikhailsycheuski.test.warehouse.domain.validation.DomainObjectValidator
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class AddNewArticleUseCase(
  private val articlesRepository: ArticlesRepository,
  @Qualifier("articleUniqueNameValidator")
  private val articleUniqueNameValidator: DomainObjectValidator<Article>
) {

  @Transactional
  fun execute(article: Article): Long =
    articleUniqueNameValidator
      .validate(article)
      .let { articlesRepository.save(article) }
}