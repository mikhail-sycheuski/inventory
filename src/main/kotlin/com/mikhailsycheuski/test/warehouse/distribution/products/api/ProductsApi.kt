package com.mikhailsycheuski.test.warehouse.distribution.products.api

import com.mikhailsycheuski.test.warehouse.distribution.products.api.ProductsApi.Companion.API_CONTEXT
import com.mikhailsycheuski.test.warehouse.domain.products.model.Product
import com.mikhailsycheuski.test.warehouse.distribution.products.service.ProductsService
import com.mikhailsycheuski.test.warehouse.domain.products.model.ProductUpdateRequest
import org.springframework.core.convert.ConversionService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping(API_CONTEXT)
class ProductsApi(
  private val conversionService: ConversionService,
  private val productsService: ProductsService
) {

  @GetMapping("/{productId}")
  @ResponseStatus(HttpStatus.OK)
  fun getProduct(
    @PathVariable("productId") productId: Long
  ): ProductDTO =
    productsService
      .findProductById(productId)
      ?.let { conversionService.convert(it, ProductDTO::class.java) }
      ?: throw RuntimeException("not found")

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  fun getProductsByName(
    @RequestParam("productName") productName: String
  ): List<ProductDTO> =
    productsService
      .findProductByName(productName)
      ?.let { conversionService.convert(it, ProductDTO::class.java) }
      ?.let { listOf(it) }
      ?: emptyList()

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  fun addProduct(
    @RequestBody @Valid createProductRequest: CreateProductRequestDTO
  ) {
    productsService.addProduct(
      conversionService.convert(
        createProductRequest,
        Product::class.java
      )!!
    )
  }

  @PatchMapping("/{productId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  fun updateProduct(
    @PathVariable("productId") productId: Long,
    @RequestBody @Valid updateProductRequest: UpdateProductRequestDTO
  ) {
    productsService.updateProduct(
      conversionService.convert(
        updateProductRequest.copy(id = productId),
        ProductUpdateRequest::class.java
      )!!
    )
  }

  companion object {
    const val API_CONTEXT = "/api/v1/products"
  }
}