package com.mikhailsycheuski.test.warehouse.distribution.articles.service

import com.mikhailsycheuski.test.warehouse.domain.articles.*
import com.mikhailsycheuski.test.warehouse.domain.articles.model.Article
import com.mikhailsycheuski.test.warehouse.domain.articles.model.ArticleUpdateRequest
import com.mikhailsycheuski.test.warehouse.domain.articles.usecases.AddNewArticleUseCase
import com.mikhailsycheuski.test.warehouse.domain.articles.usecases.UpdateArticleUseCase
import org.springframework.stereotype.Service

@Service
class ArticlesServiceImpl(
  private val articlesRepository: ArticlesRepository,
  private val addNewArticleUseCase: AddNewArticleUseCase,
  private val addOrUpdateArticleUseCase: UpdateArticleUseCase
) : ArticlesService {

  override fun findArticleById(articleId: Long): Article? =
    articlesRepository.findById(articleId)

  override fun findArticleByName(articleName: String): Article? =
    articlesRepository.findByName(articleName)

  override fun findAllArticles(): List<Article> =
    articlesRepository.findAll()

  override fun addArticle(article: Article): Long =
    addNewArticleUseCase.execute(article)

  override fun updateArticle(articleUpdateRequest: ArticleUpdateRequest) =
    addOrUpdateArticleUseCase.execute(articleUpdateRequest)
}