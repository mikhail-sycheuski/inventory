package com.mikhailsycheuski.test.warehouse.distribution.products.converters

import com.mikhailsycheuski.test.warehouse.core.extensions.SelfRegisteringConverter
import com.mikhailsycheuski.test.warehouse.distribution.products.api.*
import com.mikhailsycheuski.test.warehouse.distribution.products.service.imports.ContainedArticleJsonImportRepresentation
import com.mikhailsycheuski.test.warehouse.distribution.products.service.imports.ProductJsonImportRepresentation
import com.mikhailsycheuski.test.warehouse.domain.products.model.Product
import com.mikhailsycheuski.test.warehouse.domain.products.model.Product.ContainedArticleItem
import com.mikhailsycheuski.test.warehouse.domain.products.model.ProductUpdateRequest
import org.springframework.core.convert.ConversionService
import org.springframework.core.convert.converter.ConverterRegistry
import org.springframework.stereotype.Component


@Component
class ContainedArticleItemToDTOConverter(
  converterRegistry: ConverterRegistry
) : SelfRegisteringConverter<ContainedArticleItem, ContainedArticleItemDTO>(converterRegistry) {

  override fun convert(source: ContainedArticleItem): ContainedArticleItemDTO =
    with(source) {
      ContainedArticleItemDTO(
        articleId,
        amount
      )
    }
}

@Component
class DTOToContainedArticleItemConverter(
  converterRegistry: ConverterRegistry
) : SelfRegisteringConverter<ContainedArticleItemDTO, ContainedArticleItem>(converterRegistry) {

  override fun convert(source: ContainedArticleItemDTO): ContainedArticleItem =
    with(source) {
      ContainedArticleItem(
        articleId,
        amount
      )
    }
}

@Component
class ContainedArticleJsonImportRepresentationToContainedArticleItemConverter(
  converterRegistry: ConverterRegistry
) : SelfRegisteringConverter<ContainedArticleJsonImportRepresentation, ContainedArticleItem>(converterRegistry) {

  override fun convert(source: ContainedArticleJsonImportRepresentation): ContainedArticleItem =
    with(source) {
      ContainedArticleItem(
        articleId,
        amount
      )
    }
}

@Component
class ProductToDTOConverter(
  converterRegistry: ConverterRegistry,
  private val conversionService: ConversionService
) : SelfRegisteringConverter<Product, ProductDTO>(converterRegistry) {

  override fun convert(source: Product): ProductDTO =
    with(source) {
      ProductDTO(
        id!!,
        name,
        containedArticleItems = containedArticleItems.map {
          conversionService.convert(
            it,
            ContainedArticleItemDTO::class.java
          )!!
        }
      )
    }
}

@Component
class DTOToProductConverter(
  converterRegistry: ConverterRegistry,
  private val conversionService: ConversionService
) : SelfRegisteringConverter<ProductDTO, Product>(converterRegistry) {

  override fun convert(source: ProductDTO): Product =
    with(source) {
      Product(
        id,
        name,
        containedArticleItems = containedArticleItems.mapTo(mutableSetOf()) {
          conversionService.convert(
            it,
            ContainedArticleItem::class.java
          )!!
        }
      )
    }
}

@Component
class CreateProductRequestToProductConverter(
  converterRegistry: ConverterRegistry,
  private val conversionService: ConversionService
) : SelfRegisteringConverter<CreateProductRequestDTO, Product>(converterRegistry) {

  override fun convert(source: CreateProductRequestDTO): Product =
    with(source) {
      Product(
        name = name,
        containedArticleItems = containedArticleItems.mapTo(mutableSetOf()) {
          conversionService.convert(
            it,
            ContainedArticleItem::class.java
          )!!
        }
      )
    }
}

@Component
class UpdateProductRequestToProductConverter(
  converterRegistry: ConverterRegistry,
  private val conversionService: ConversionService
) : SelfRegisteringConverter<UpdateProductRequestDTO, ProductUpdateRequest>(converterRegistry) {

  override fun convert(source: UpdateProductRequestDTO): ProductUpdateRequest =
    with(source) {
      ProductUpdateRequest(
        id!!,
        containedArticleItems = containedArticleItems.mapTo(mutableSetOf()) {
          conversionService.convert(
            it,
            ContainedArticleItem::class.java
          )!!
        }
      )
    }
}

@Component
class ProductJsonImportRepresentationToProductConverter(
  converterRegistry: ConverterRegistry,
  private val conversionService: ConversionService
) : SelfRegisteringConverter<ProductJsonImportRepresentation, Product>(converterRegistry) {

  override fun convert(source: ProductJsonImportRepresentation): Product =
    with(source) {
      Product(
        name = name,
        containedArticleItems = containArticles.mapTo(mutableSetOf()) {
          conversionService.convert(
            it,
            ContainedArticleItem::class.java
          )!!
        }
      )
    }
}

@Component
class ProductAvailabilityToDTOConverter(
  converterRegistry: ConverterRegistry,
  private val conversionService: ConversionService
) : SelfRegisteringConverter<Pair<Product, Int>, ProductAvailabilityDTO>(converterRegistry) {

  override fun convert(source: Pair<Product, Int>): ProductAvailabilityDTO =
    with(source) {
      ProductAvailabilityDTO(
        product = conversionService.convert(first, ProductDTO::class.java)!!,
        numberOfAvailable = second
      )
    }
}