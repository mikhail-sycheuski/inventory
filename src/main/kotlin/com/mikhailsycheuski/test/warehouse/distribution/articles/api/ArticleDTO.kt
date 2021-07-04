package com.mikhailsycheuski.test.warehouse.distribution.articles.api

import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

data class ArticleDTO(
  val id: Long,
  val name: String,
  val stock: Long = 0L
)

data class CreateArticleRequestDTO(
  @NotNull val name: String,
  @Min(value = 0L) val stock: Long = 0L
)

data class UpdateArticleRequestDTO(
  val id: Long? = null,
  @Min(value = 0L) val stock: Long = 0L
)
