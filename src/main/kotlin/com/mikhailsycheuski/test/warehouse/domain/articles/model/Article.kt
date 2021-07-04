package com.mikhailsycheuski.test.warehouse.domain.articles.model


data class Article(
  val id: Long? = null,
  val name: String,
  val stock: Long
) {

  fun toModifiableInstance() = ModifiableInstance(id, name, stock)

  data class ModifiableInstance(
    val id: Long? = null,
    val name: String,
    var stock: Long
  ) {
    fun toArticle() = Article(id, name, stock)
  }
}


