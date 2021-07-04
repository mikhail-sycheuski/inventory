package com.mikhailsycheuski.test.warehouse.domain.products.usecases

import com.mikhailsycheuski.test.warehouse.domain.articles.ArticlesRepository
import com.mikhailsycheuski.test.warehouse.domain.articles.model.Article
import com.mikhailsycheuski.test.warehouse.domain.products.ProductsRepository
import com.mikhailsycheuski.test.warehouse.domain.products.model.Product
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional


// TODO: think about moving same logic
// TODO: add batching execution here because there might be lots of products + paginated request/response
@Component
class GetProductsAvailabilityUseCase(
  private val productsRepository: ProductsRepository,
  private val articlesRepository: ArticlesRepository
) {

  @Transactional
  fun execute(): Map<Product, Int> =
    productsRepository
      .findAllProducts()
      .associateBy({ it }, { countNumberOfAvailableProductInstances(it) })

  private fun countNumberOfAvailableProductInstances(product: Product): Int {
    var numberOfAvailableProductInstances = 0

    // TODO: articles need to be cached
    val productArticlesMap =
      product
        .containedArticleItems
        .map { it.articleId }
        .let { articlesRepository.findAllByIds(it) }
        .map { it.toModifiableInstance() }
        .associateBy { it.id!! }

    while (isEnoughOfArticleStockForProduct(product, productArticlesMap)) {
      product
        .containedArticleItems
        .forEach {
          productArticlesMap[it.articleId]!!.stock -= it.amount
        }

      numberOfAvailableProductInstances++
    }

    return numberOfAvailableProductInstances
  }

  private fun isEnoughOfArticleStockForProduct(
    product: Product,
    articles: Map<Long, Article.ModifiableInstance>
  ) =
    product
      .containedArticleItems
      .all { it.amount <= articles[it.articleId]!!.stock }
}