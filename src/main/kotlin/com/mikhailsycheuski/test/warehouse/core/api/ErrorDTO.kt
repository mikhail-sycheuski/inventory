package com.mikhailsycheuski.test.warehouse.core.api

import java.time.LocalDateTime


data class ErrorDTO(
  val code: Int,
  val errorDescription: String,
  val errorMessage: String,
  val timestamp: LocalDateTime = LocalDateTime.now(),
  val path: String = PathBuilder.buildCurrentRequestPath()
)
