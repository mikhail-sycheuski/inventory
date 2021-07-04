package com.mikhailsycheuski.test.warehouse.distribution.products.api

import com.mikhailsycheuski.test.warehouse.core.api.PathBuilder
import com.mikhailsycheuski.test.warehouse.distribution.products.api.ProductsApi.Companion.API_CONTEXT
import com.mikhailsycheuski.test.warehouse.domain.products.model.Product
import com.mikhailsycheuski.test.warehouse.distribution.products.service.ProductsService
import com.mikhailsycheuski.test.warehouse.domain.exception.DomainObjectNotFountException
import com.mikhailsycheuski.test.warehouse.domain.products.model.ProductUpdateRequest
import org.springframework.core.convert.ConversionService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
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
      ?: throw DomainObjectNotFountException("Product with id[${productId}] doesn't exist")

  // TODO: change on paginated request/response
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  fun getProducts(
    @RequestParam("name", required = false) productName: String? = null
  ): List<ProductDTO> =
    productName
      ?.let { getProductsByName(it) }
      ?: getAllProducts()

  @PostMapping
  @ResponseStatus(CREATED)
  fun addProduct(
    @RequestBody @Valid createProductRequest: CreateProductRequestDTO
  ): ResponseEntity<Nothing> =
    productsService
      .addProduct(conversionService.convert(createProductRequest, Product::class.java)!!)
      .let {
        ResponseEntity
          .status(CREATED)
          .header(HttpHeaders.LOCATION, PathBuilder.buildResourceLocationPath(it))
          .build()
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

  private fun getProductsByName(productName: String) =
    productsService
      .findProductByName(productName)
      ?.let { conversionService.convert(it, ProductDTO::class.java) }
      ?.let { listOf(it) }
      ?: emptyList()

  private fun getAllProducts() =
    productsService
      .findAllProducts()
      .map { conversionService.convert(it, ProductDTO::class.java) }

  companion object {
    const val API_CONTEXT = "/api/v1/products"
  }
}