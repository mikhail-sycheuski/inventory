package com.mikhailsycheuski.test.warehouse.domain.articles.usecases

import com.mikhailsycheuski.test.warehouse.domain.articles.ArticlesRepository
import com.mikhailsycheuski.test.warehouse.domain.articles.model.Article
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component


@Component
class UploadArticlesUseCase(
  private val articlesRepository: ArticlesRepository
) {

  fun execute(articles: List<Article>) {
    articles.forEach { article ->
      articlesRepository
        .findByName(article.name)
        ?.let {
          articlesRepository.increaseStockBy(it.id!!, it.stock)
          logger.debug("Increased existing article[${it.name}] stock by ${it.stock}")
        }
        ?: run {
          articlesRepository.save(article)
          logger.debug("Saved new article[${article.name}] with stock ${article.stock}")
        }
    }
  }

  companion object {
    val logger: Logger = LoggerFactory.getLogger(UploadArticlesUseCase::class.java)
  }
}