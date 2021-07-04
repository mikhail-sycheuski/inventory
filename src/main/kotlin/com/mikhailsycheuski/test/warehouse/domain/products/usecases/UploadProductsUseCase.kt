package com.mikhailsycheuski.test.warehouse.domain.products.usecases

import com.mikhailsycheuski.test.warehouse.domain.products.ProductsRepository
import com.mikhailsycheuski.test.warehouse.domain.products.model.Product
import com.mikhailsycheuski.test.warehouse.domain.validation.DomainObjectValidator
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional


@Component
class UploadProductsUseCase(
  private val productsRepository: ProductsRepository,
  @Qualifier("productContainedArticleItemsValidator")
  private val productContainedArticleItemsValidator: DomainObjectValidator<Product>
) {

  @Transactional
  fun execute(products: List<Product>) {
    products.forEach { createOrUpdateProduct(it) }
  }

  private fun createOrUpdateProduct(product: Product) {
    productContainedArticleItemsValidator.validate(product)
    productsRepository
      .findByName(product.name)
      ?.copy(containedArticleItems = product.containedArticleItems)
      ?.let {
        productsRepository.update(it)
        logger.debug("Updated existing product[${it.name}] contained article items")
      }
      ?: run {
        productsRepository.save(product)
        logger.debug("Saved new product[${product.name}] with contained article items")
      }
  }

  companion object {
    val logger: Logger = LoggerFactory.getLogger(UploadProductsUseCase::class.java)
  }
}