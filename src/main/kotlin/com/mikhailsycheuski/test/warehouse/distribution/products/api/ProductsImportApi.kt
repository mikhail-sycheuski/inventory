package com.mikhailsycheuski.test.warehouse.distribution.products.api

import com.mikhailsycheuski.test.warehouse.core.FileImportProcessor
import com.mikhailsycheuski.test.warehouse.distribution.products.api.ProductsImportApi.Companion.API_CONTEXT
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile


@RestController
@RequestMapping(API_CONTEXT)
class ProductsImportApi(
  @Qualifier("productsJsonFileImportProcessor")
  private val productsJsonFileImportProcessor: FileImportProcessor
) {

  @PostMapping("/json/upload")
  @ResponseStatus(HttpStatus.OK)
  fun importArticlesJsonFile(
    @RequestParam("file") file: MultipartFile
  ) {
    productsJsonFileImportProcessor.process(file)
  }


  companion object {
    const val API_CONTEXT = "/api/v1/products/import"
  }
}