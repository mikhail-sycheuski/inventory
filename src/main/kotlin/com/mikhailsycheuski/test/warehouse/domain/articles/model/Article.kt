package com.mikhailsycheuski.test.warehouse.domain.articles.model


data class Article(
  val id: Long? = null,
  val name: String,
  val stock: Long,
  val version: Long? = null
) {

  fun toModifiableInstance() = ModifiableInstance(id, name, stock, version)

  data class ModifiableInstance(
    val id: Long?,
    val name: String,
    var stock: Long,
    val version: Long?
  ) {
    fun toArticle() = Article(id, name, stock, version)
  }
}


