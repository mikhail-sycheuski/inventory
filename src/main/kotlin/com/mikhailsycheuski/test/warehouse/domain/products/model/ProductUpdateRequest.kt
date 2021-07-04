package com.mikhailsycheuski.test.warehouse.domain.products.model


data class ProductUpdateRequest(
  val id: Long,
  val containedArticleItems: Set<Product.ContainedArticleItem>
)
