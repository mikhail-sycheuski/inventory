package com.mikhailsycheuski.test.warehouse.distribution.products.service.imports

import com.fasterxml.jackson.databind.ObjectMapper
import com.mikhailsycheuski.test.warehouse.core.FileImportProcessor
import com.mikhailsycheuski.test.warehouse.core.FileImportProcessor.FileParsingException
import com.mikhailsycheuski.test.warehouse.domain.products.model.Product
import com.mikhailsycheuski.test.warehouse.domain.products.usecases.UploadProductsUseCase
import org.springframework.core.convert.ConversionService
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile


@Service("productsJsonFileImportProcessor")
class ProductsFileImportProcessorImplJson(
  private val objectMapper: ObjectMapper,
  private val conversionService: ConversionService,
  private val uploadProductsUseCase: UploadProductsUseCase
) : FileImportProcessor {

  override fun process(file: MultipartFile) {
    val products =
      objectMapper
        .runCatching { readValue(file.inputStream, ProductsJsonImportRepresentation::class.java) }
        .onFailure { throw FileParsingException("Failed to parse json file with products: ", it) }
        .getOrThrow()
        .products
        .map { conversionService.convert(it, Product::class.java)!! }

    uploadProductsUseCase.execute(products)
  }
}