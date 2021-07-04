package com.mikhailsycheuski.test.warehouse.distribution.products.service

import com.mikhailsycheuski.test.warehouse.domain.products.model.Product
import com.mikhailsycheuski.test.warehouse.domain.products.model.ProductUpdateRequest


interface ProductsService {
  fun findProductById(productId: Long): Product?
  fun findProductByName(productName: String): Product?
  fun addProduct(product: Product)
  fun updateProduct(productUpdateRequest: ProductUpdateRequest)
}