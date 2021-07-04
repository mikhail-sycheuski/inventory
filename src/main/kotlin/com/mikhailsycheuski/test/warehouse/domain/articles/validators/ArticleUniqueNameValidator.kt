package com.mikhailsycheuski.test.warehouse.domain.articles.validators

import com.mikhailsycheuski.test.warehouse.domain.articles.ArticlesRepository
import com.mikhailsycheuski.test.warehouse.domain.articles.model.Article
import com.mikhailsycheuski.test.warehouse.domain.exception.DomainObjectValidationException
import com.mikhailsycheuski.test.warehouse.domain.validation.DomainObjectValidator
import org.springframework.stereotype.Component


@Component("articleUniqueNameValidator")
class ArticleUniqueNameValidator(
  private val articlesRepository: ArticlesRepository
) : DomainObjectValidator<Article> {

  override fun validate(domainObject: Article) {
    if (articlesRepository.existsByName(domainObject.name)) {
      throw DomainObjectValidationException("article with name['$domainObject.name'] already exists")
    }
  }
}