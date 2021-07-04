package com.mikhailsycheuski.test.warehouse.distribution.products.repository

import com.mikhailsycheuski.test.warehouse.distribution.products.repository.ProductsRepositoryDataJDBCPort.ProductRepresentation
import org.springframework.data.annotation.Id
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.relational.core.mapping.MappedCollection
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param


interface ProductsRepositoryDataJDBCPort : PagingAndSortingRepository<ProductRepresentation, Long> {

  fun findByName(name: String): ProductRepresentation?

  @Modifying
  @Query("DELETE FROM t_products_contained_items WHERE product_id = :productId")
  fun deleteProductContainedArticleItems(productId: Long)

  @Modifying
  @Query(
    """
      INSERT INTO t_products_contained_items 
        (product_id, article_id, amount) 
      VALUES 
        (:productId, :articleId, :amount)
      """
  )
  fun saveProductContainedArticleItem(
    productId: Long,
    articleId: Long,
    amount: Int
  )

  @Table("t_products")
  data class ProductRepresentation(
    @Id
    val id: Long? = null,
    val name: String,
    @MappedCollection(idColumn = "product_id")
    val containedItems: MutableSet<ContainedArticleItemRepresentation> = mutableSetOf()
  ) {
    fun addContainedItem(containedItem: ContainedArticleItemRepresentation) {
      this.containedItems.add(containedItem)
    }
  }

  @Table("t_products_contained_items")
  data class ContainedArticleItemRepresentation(
    val articleId: Long,
    val amount: Int
  )
}