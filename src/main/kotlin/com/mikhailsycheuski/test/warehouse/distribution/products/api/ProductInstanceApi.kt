package com.mikhailsycheuski.test.warehouse.distribution.products.api

import com.mikhailsycheuski.test.warehouse.distribution.products.api.ProductInstanceApi.Companion.API_CONTEXT
import com.mikhailsycheuski.test.warehouse.distribution.products.service.ProductsService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(API_CONTEXT)
class ProductInstanceApi(
  private val productsService: ProductsService
) {

  @DeleteMapping("/{productId}")
  @ResponseStatus(HttpStatus.OK)
  fun getProduct(@PathVariable("productId") productId: Long) {
    productsService.sellProduct(productId)
  }

  companion object {
    const val API_CONTEXT = "/api/v1/product-instances"
  }
}