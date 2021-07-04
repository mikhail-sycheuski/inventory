package com.mikhailsycheuski.test.warehouse.distribution.articles.converters

import com.mikhailsycheuski.test.warehouse.domain.articles.model.Article
import com.mikhailsycheuski.test.warehouse.core.extensions.SelfRegisteringConverter
import com.mikhailsycheuski.test.warehouse.distribution.articles.api.ArticleDTO
import com.mikhailsycheuski.test.warehouse.distribution.articles.api.CreateArticleRequestDTO
import com.mikhailsycheuski.test.warehouse.distribution.articles.api.UpdateArticleRequestDTO
import com.mikhailsycheuski.test.warehouse.domain.articles.model.ArticleUpdateRequest
import org.springframework.core.convert.converter.ConverterRegistry
import org.springframework.stereotype.Component

@Component
class ArticleToDTOConverter(
  converterRegistry: ConverterRegistry
) : SelfRegisteringConverter<Article, ArticleDTO>(converterRegistry) {

  override fun convert(source: Article): ArticleDTO =
    with(source) {
      ArticleDTO(
        id!!,
        name,
        stock
      )
    }
}

@Component
class DTOToArticleConverter(
  converterRegistry: ConverterRegistry
) : SelfRegisteringConverter<ArticleDTO, Article>(converterRegistry) {

  override fun convert(source: ArticleDTO): Article =
    with(source) {
      Article(
        id,
        name,
        stock
      )
    }
}

@Component
class CreateArticleRequestToArticleConverter(
  converterRegistry: ConverterRegistry
) : SelfRegisteringConverter<CreateArticleRequestDTO, Article>(converterRegistry) {

  override fun convert(source: CreateArticleRequestDTO): Article =
    with(source) {
      Article(
        name = name,
        stock = stock
      )
    }
}

@Component
class UpdateArticleRequestToArticleConverter(
  converterRegistry: ConverterRegistry
) : SelfRegisteringConverter<UpdateArticleRequestDTO, ArticleUpdateRequest>(converterRegistry) {

  override fun convert(source: UpdateArticleRequestDTO): ArticleUpdateRequest =
    with(source) {
      ArticleUpdateRequest(
        id!!,
        increaseStockValue = stock
      )
    }
}