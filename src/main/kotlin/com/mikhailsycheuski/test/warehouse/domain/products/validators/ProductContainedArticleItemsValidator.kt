package com.mikhailsycheuski.test.warehouse.domain.products.validators

import com.mikhailsycheuski.test.warehouse.domain.articles.ArticlesRepository
import com.mikhailsycheuski.test.warehouse.domain.exception.DomainObjectValidationException
import com.mikhailsycheuski.test.warehouse.domain.products.model.Product
import com.mikhailsycheuski.test.warehouse.domain.validation.DomainObjectValidator
import com.mikhailsycheuski.test.warehouse.domain.validation.DomainObjectValidator.ValidationResult
import org.springframework.stereotype.Component


@Component("productContainedArticleItemsValidator")
class ProductContainedArticleItemsValidator(
  private val articlesRepository: ArticlesRepository
) : DomainObjectValidator<Product> {

  override fun validate(domainObject: Product) {
    domainObject
      .containedArticleItems
      .asSequence()
      .map { validateArticle(it.articleId) }
      .filter { it.isFailure() }
      .map { it.errorMessage }
      .toList()
      .takeIf { it.isNotEmpty() }
      ?.let { throw DomainObjectValidationException("Product contained articles are invalid: [${it.joinToString()}]") }
  }

  private fun validateArticle(articleId: Long): ValidationResult =
    if (articlesRepository.existsById(articleId))
      ValidationResult.success()
    else
      ValidationResult.failure("Article with id[${articleId}] doesn't exist")
}