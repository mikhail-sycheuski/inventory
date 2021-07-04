package com.mikhailsycheuski.test.warehouse.distribution.articles.api

import com.mikhailsycheuski.test.warehouse.domain.articles.model.Article
import com.mikhailsycheuski.test.warehouse.distribution.articles.api.ArticlesApi.Companion.API_CONTEXT
import com.mikhailsycheuski.test.warehouse.distribution.articles.service.ArticlesService
import com.mikhailsycheuski.test.warehouse.distribution.products.api.ProductDTO
import com.mikhailsycheuski.test.warehouse.domain.articles.model.ArticleUpdateRequest
import org.springframework.core.convert.ConversionService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping(API_CONTEXT)
class ArticlesApi(
  private val conversionService: ConversionService,
  private val articlesService: ArticlesService
) {

  @GetMapping("/{articleId}")
  @ResponseStatus(HttpStatus.OK)
  fun getArticle(
    @PathVariable("articleId") articleId: Long
  ): ArticleDTO =
    articlesService
      .findArticleById(articleId)
      ?.let { conversionService.convert(it, ArticleDTO::class.java)!! }
      ?: throw RuntimeException("not found")

  // TODO: change on paginated request/response
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  fun getArticles(@RequestParam("name", required = false) articleName: String? = null): List<ArticleDTO> =
    articleName
      ?.let { getArticlesByName(it) }
      ?: getAllArticles()

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  fun addArticle(
    @RequestBody @Valid createArticleRequest: CreateArticleRequestDTO
  ) {
    articlesService.addArticle(conversionService.convert(createArticleRequest, Article::class.java)!!)
  }

  @PatchMapping("/{articleId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  fun updateArticle(
    @PathVariable("articleId") articleId: Long,
    @RequestBody @Valid updateArticleRequestDTO: UpdateArticleRequestDTO
  ) {
    articlesService.updateArticle(
      conversionService.convert(
        updateArticleRequestDTO.copy(id = articleId),
        ArticleUpdateRequest::class.java
      )!!
    )
  }

  private fun getArticlesByName(articleName: String) =
    articlesService
      .findArticleByName(articleName)
      ?.let { conversionService.convert(it, ArticleDTO::class.java)!! }
      ?.let { listOf(it) }
      ?: emptyList()

  private fun getAllArticles() =
    articlesService
      .findAllArticles()
      .map { conversionService.convert(it, ArticleDTO::class.java) }

  companion object {
    const val API_CONTEXT = "/api/v1/articles"
  }
}