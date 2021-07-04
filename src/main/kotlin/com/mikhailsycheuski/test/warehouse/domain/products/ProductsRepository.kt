package com.mikhailsycheuski.test.warehouse.domain.products

import com.mikhailsycheuski.test.warehouse.domain.products.model.Product


interface ProductsRepository {

  // TODO: add products caching
  fun findById(productId: Long): Product?
  fun findByName(productName: String): Product?
  // TODO: change on paginated response
  fun findAllProducts(): List<Product>
  fun existsByName(productName: String): Boolean = findByName(productName)?.let { true } ?: false
  fun save(product: Product)
  fun update(product: Product)
}