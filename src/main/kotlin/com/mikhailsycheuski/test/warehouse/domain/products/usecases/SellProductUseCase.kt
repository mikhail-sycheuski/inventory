package com.mikhailsycheuski.test.warehouse.domain.products.usecases

import com.mikhailsycheuski.test.warehouse.domain.articles.ArticlesRepository
import com.mikhailsycheuski.test.warehouse.domain.articles.model.Article
import com.mikhailsycheuski.test.warehouse.domain.exception.DomainException
import com.mikhailsycheuski.test.warehouse.domain.exception.DomainObjectNotFountException
import com.mikhailsycheuski.test.warehouse.domain.products.ProductsRepository
import com.mikhailsycheuski.test.warehouse.domain.products.model.Product
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.dao.OptimisticLockingFailureException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional


// TODO: think about moving same logic
@Component
class SellProductUseCase(
  @Value("\${app.useCases.sellProductUseCase.retryAttemptsLimit}")
  private val retryAttemptsLimit: Int,
  private val productsRepository: ProductsRepository,
  private val articlesRepository: ArticlesRepository
) {

  @Transactional
  fun execute(productId: Long) {
    productsRepository
      .findById(productId)
      ?.let { processProductSell(it) }
      ?: throw DomainObjectNotFountException("Product with id[${productId}] doesn't exist")
  }

  private fun processProductSell(
    product: Product,
    retryAttempt: Int = 0
  ) {
    if (retryAttempt == retryAttemptsLimit) {
      throw DomainException("Failed to sell product[${product.name}]. Please try later")
    }

    val articles =
      product
        .containedArticleItems
        .map { it.articleId }
        .let { articlesRepository.findAllByIds(it) }
        .map { it.toModifiableInstance() }
        .associateBy { it.id!! }

    if (isEnoughOfArticleStockForProduct(product, articles)) {
      trySellProduct(product, articles, retryAttempt)
    }
  }

  private fun trySellProduct(
    product: Product, articles:
    Map<Long, Article.ModifiableInstance>,
    retryAttempt: Int = 0
  ) {
    product
      .containedArticleItems
      .forEach {
        articles[it.articleId]!!.stock -= it.amount
      }

    articles
      .map { it.value.toArticle() }
      .forEach { article ->
        article
          .runCatching { articlesRepository.save(article) }
          .onFailure {
            if (it.cause is OptimisticLockingFailureException) {
              processProductSell(product, retryAttempt + 1)
            } else {
              throw it
            }
          }
      }
  }

  private fun isEnoughOfArticleStockForProduct(
    product: Product,
    articles: Map<Long, Article.ModifiableInstance>
  ) =
    product
      .containedArticleItems
      .all { it.amount <= articles[it.articleId]!!.stock }

  companion object {
    val logger: Logger = LoggerFactory.getLogger(SellProductUseCase::class.java)
  }
}