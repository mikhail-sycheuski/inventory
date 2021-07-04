package com.mikhailsycheuski.test.warehouse.distribution.products.api


data class ProductDTO(
  val id: Long,
  val name: String,
  val containedArticleItems: List<ContainedArticleItemDTO>
)

data class ContainedArticleItemDTO(
  val articleId: Long,
  val amount: Int
)

data class CreateProductRequestDTO(
  val name: String,
  val containedArticleItems: List<ContainedArticleItemDTO>
)

data class UpdateProductRequestDTO(
  val id: Long? = null,
  val containedArticleItems: List<ContainedArticleItemDTO>
)

data class ProductAvailabilityDTO(
  val product: ProductDTO,
  val numberOfAvailable: Int
)
