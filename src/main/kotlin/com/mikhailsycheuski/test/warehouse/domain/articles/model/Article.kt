package com.mikhailsycheuski.test.warehouse.domain.articles.model


data class Article(
  val id: Long? = null,
  val name: String,
  val stock: Long
)
