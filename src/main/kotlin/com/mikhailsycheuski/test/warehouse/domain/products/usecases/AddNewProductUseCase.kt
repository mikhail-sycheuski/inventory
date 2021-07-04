package com.mikhailsycheuski.test.warehouse.domain.products.usecases

import com.mikhailsycheuski.test.warehouse.domain.products.ProductsRepository
import com.mikhailsycheuski.test.warehouse.domain.products.model.Product
import com.mikhailsycheuski.test.warehouse.domain.validation.DomainObjectValidator
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional


@Component
class AddNewProductUseCase(
  private val productsRepository: ProductsRepository,
  @Qualifier("productUniqueNameValidator")
  private val productUniqueNameValidator: DomainObjectValidator<Product>
) {

  @Transactional
  fun execute(product: Product) {
    productUniqueNameValidator.validate(product)
    productsRepository.save(product);
  }
}