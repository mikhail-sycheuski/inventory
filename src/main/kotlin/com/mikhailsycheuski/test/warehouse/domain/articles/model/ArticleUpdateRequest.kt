package com.mikhailsycheuski.test.warehouse.domain.articles.model

data class ArticleUpdateRequest(
  val id: Long,
  val increaseStockValue: Long
)
