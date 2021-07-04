package com.mikhailsycheuski.test.warehouse.distribution.products.api

import com.mikhailsycheuski.test.warehouse.distribution.products.api.ProductsAvailabilityApi.Companion.API_CONTEXT
import com.mikhailsycheuski.test.warehouse.distribution.products.service.ProductsService
import org.springframework.core.convert.ConversionService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(API_CONTEXT)
class ProductsAvailabilityApi(
  private val conversionService: ConversionService,
  private val productsService: ProductsService
) {

  // TODO: change on paginated request/response
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  fun getProductsAvailability(): List<ProductAvailabilityDTO> =
    productsService
      .getAvailableProducts()
      .asSequence()
      .map { it.toPair() }
      .map { conversionService.convert(it, ProductAvailabilityDTO::class.java) }
      .toList()

  companion object {
    const val API_CONTEXT = "/api/v1/products-availability"
  }
}