package com.mikhailsycheuski.test.warehouse.distribution.articles.api

import com.mikhailsycheuski.test.warehouse.core.service.FileImportProcessor
import com.mikhailsycheuski.test.warehouse.distribution.articles.api.ArticlesImportApi.Companion.API_CONTEXT
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping(API_CONTEXT)
class ArticlesImportApi(
  @Qualifier("articlesJsonFileImportProcessor")
  private val articlesJsonFileImportProcessor: FileImportProcessor
) {

  @PostMapping("/json/upload")
  @ResponseStatus(HttpStatus.OK)
  fun importArticlesJsonFile(
    @RequestParam("file") file: MultipartFile
  ) {
    articlesJsonFileImportProcessor.process(file)
  }


  companion object {
    const val API_CONTEXT = "/api/v1/articles/import"
  }
}