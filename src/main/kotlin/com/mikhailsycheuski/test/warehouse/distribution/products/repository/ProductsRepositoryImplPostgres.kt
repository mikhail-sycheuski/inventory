package com.mikhailsycheuski.test.warehouse.distribution.products.repository

import com.mikhailsycheuski.test.warehouse.core.extensions.unwrap
import com.mikhailsycheuski.test.warehouse.distribution.products.repository.ProductsRepositoryDataJDBCPort.ContainedArticleItemRepresentation
import com.mikhailsycheuski.test.warehouse.distribution.products.repository.ProductsRepositoryDataJDBCPort.ProductRepresentation
import com.mikhailsycheuski.test.warehouse.domain.products.model.Product
import com.mikhailsycheuski.test.warehouse.domain.products.ProductsRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class ProductsRepositoryImplPostgres(
  private val productsRepositoryDataJDBCPort: ProductsRepositoryDataJDBCPort
) : ProductsRepository {

  @Transactional(readOnly = true)
  override fun findById(productId: Long): Product? =
    productsRepositoryDataJDBCPort
      .findById(productId)
      .unwrap()
      ?.asDomain()

  @Transactional(readOnly = true)
  override fun findByName(productName: String): Product? =
    productsRepositoryDataJDBCPort
      .findByName(productName)
      ?.asDomain()

  @Transactional(readOnly = true)
  override fun findAllProducts(): List<Product> =
    productsRepositoryDataJDBCPort.findAll().map { it.asDomain() }

  @Transactional
  override fun save(product: Product): Long {
    val saved = productsRepositoryDataJDBCPort.save(product.asRepresentation())
    saveProductContainedArticleItems(saved.id!!, product.containedArticleItems)
    return saved.id!!
  }

  @Transactional
  override fun update(product: Product) {
    productsRepositoryDataJDBCPort.deleteProductContainedArticleItems(product.id!!)
    saveProductContainedArticleItems(product.id!!, product.containedArticleItems)
  }

  // TODO: Change to batch insert for efficiency
  private fun saveProductContainedArticleItems(productId: Long, items: Set<Product.ContainedArticleItem>) {
    items
      .asSequence()
      .map { it.asRepresentation() }
      .forEach { item ->
        productsRepositoryDataJDBCPort.saveProductContainedArticleItem(
          productId,
          item.articleId,
          item.amount
        )
      }
  }

  private fun ProductRepresentation.asDomain() =
    Product(
      id,
      name,
      containedArticleItems = containedItems.mapTo(mutableSetOf()) { it.asDomain() }
    )

  private fun ContainedArticleItemRepresentation.asDomain() =
    Product.ContainedArticleItem(articleId, amount)

  private fun Product.asRepresentation() =
    ProductRepresentation(
      id,
      name
    )

  private fun Product.ContainedArticleItem.asRepresentation() =
    ContainedArticleItemRepresentation(articleId, amount)
}