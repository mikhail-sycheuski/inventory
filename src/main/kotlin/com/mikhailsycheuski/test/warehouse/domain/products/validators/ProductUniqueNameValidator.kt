package com.mikhailsycheuski.test.warehouse.domain.products.validators

import com.mikhailsycheuski.test.warehouse.domain.exception.DomainObjectValidationException
import com.mikhailsycheuski.test.warehouse.domain.products.ProductsRepository
import com.mikhailsycheuski.test.warehouse.domain.products.model.Product
import com.mikhailsycheuski.test.warehouse.domain.validation.DomainObjectValidator
import org.springframework.stereotype.Component


@Component("productUniqueNameValidator")
class ProductUniqueNameValidator(
  private val productsRepository: ProductsRepository
) : DomainObjectValidator<Product> {

  override fun validate(domainObject: Product) {
    if (productsRepository.existsByName(domainObject.name)) {
      throw DomainObjectValidationException("Product with name['$domainObject.name'] already exists")
    }
  }
}