package com.mikhailsycheuski.test.warehouse.domain.products.model


data class Product(
  val id: Long? = null,
  val name: String,
  val containedArticleItems: Set<ContainedArticleItem>
) {
  data class ContainedArticleItem(
    val articleId: Long,
    val amount: Int
  )
}
