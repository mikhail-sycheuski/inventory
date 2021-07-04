package com.mikhailsycheuski.test.warehouse.distribution.articles.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.mikhailsycheuski.test.warehouse.core.service.FileImportProcessor
import com.mikhailsycheuski.test.warehouse.core.service.FileImportProcessor.FileParsingException
import com.mikhailsycheuski.test.warehouse.distribution.articles.api.CreateArticleRequestDTO
import com.mikhailsycheuski.test.warehouse.domain.articles.model.Article
import com.mikhailsycheuski.test.warehouse.domain.articles.usecases.UploadArticlesUseCase
import org.springframework.core.convert.ConversionService
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service("articlesJsonFileImportProcessor")
class ArticlesFileImportProcessorImplJson(
  private val objectMapper: ObjectMapper,
  private val conversionService: ConversionService,
  private val uploadArticlesUseCase: UploadArticlesUseCase
) : FileImportProcessor {

  override fun process(file: MultipartFile) {
    val articles =
      objectMapper
        .runCatching { readValue(file.inputStream, ArticlesJsonImportDTO::class.java) }
        .onFailure { throw FileParsingException("Failed to parse json file with articles: ", it) }
        .getOrThrow()
        .inventory
        .map { conversionService.convert(it, Article::class.java)!! }

    uploadArticlesUseCase.execute(articles);
  }

  private data class ArticlesJsonImportDTO(
    val inventory: List<CreateArticleRequestDTO>
  )
}