package com.mikhailsycheuski.test.warehouse.domain.products.usecases

import com.mikhailsycheuski.test.warehouse.domain.exception.DomainObjectNotFountException
import com.mikhailsycheuski.test.warehouse.domain.products.ProductsRepository
import com.mikhailsycheuski.test.warehouse.domain.products.model.Product
import com.mikhailsycheuski.test.warehouse.domain.products.model.ProductUpdateRequest
import com.mikhailsycheuski.test.warehouse.domain.validation.DomainObjectValidator
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class UpdateProductUseCase(
  private val productsRepository: ProductsRepository,
  @Qualifier("productContainedArticleItemsValidator")
  private val productContainedArticleItemsValidator: DomainObjectValidator<Product>
) {

  fun execute(productUpdateRequest: ProductUpdateRequest) {
    productsRepository
      .findById(productUpdateRequest.id)
      ?.let {
        updateProduct(
          it.copy(containedArticleItems = productUpdateRequest.containedArticleItems)
        )
      }
      ?: throw DomainObjectNotFountException("Product with id[${productUpdateRequest.id}] doesn't exist")
  }

  private fun updateProduct(product: Product) {
    product
      .also { productContainedArticleItemsValidator.validate(it) }
      .let { productsRepository.update(it) }
  }
}