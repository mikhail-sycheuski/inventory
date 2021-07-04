package com.mikhailsycheuski.test.warehouse.distribution.products.service

import com.mikhailsycheuski.test.warehouse.domain.products.model.Product
import com.mikhailsycheuski.test.warehouse.domain.products.model.ProductUpdateRequest


interface ProductsService {
  fun findProductById(productId: Long): Product?
  fun findProductByName(productName: String): Product?
  // TODO: change to paginated request/response
  fun findAllProducts(): List<Product>
  fun addProduct(product: Product): Long
  fun updateProduct(productUpdateRequest: ProductUpdateRequest)
  // TODO: change to paginated request/response
  fun getAvailableProducts(): Map<Product, Int>
}