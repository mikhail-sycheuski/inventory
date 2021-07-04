package com.mikhailsycheuski.test.warehouse.distribution.products.service.imports

import com.fasterxml.jackson.annotation.JsonProperty


data class ProductsJsonImportRepresentation(
  val products: List<ProductJsonImportRepresentation>
)

data class ProductJsonImportRepresentation(
  val name: String,
  @JsonProperty("contain_articles") val containArticles: List<ContainedArticleJsonImportRepresentation>
)

data class ContainedArticleJsonImportRepresentation(
  @JsonProperty("art_id") val articleId: Long,
  @JsonProperty("amount_of") val amount: Int
)