package com.mikhailsycheuski.test.warehouse.core.api

import com.mikhailsycheuski.test.warehouse.domain.exception.DomainObjectNotFountException
import com.mikhailsycheuski.test.warehouse.domain.exception.DomainObjectValidationException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

// TODO: add more exception handlers including javax.validation exception handlers
@RestControllerAdvice
class ErrorHandler : ResponseEntityExceptionHandler() {

  override fun handleMethodArgumentNotValid(
    exception: MethodArgumentNotValidException,
    headers: HttpHeaders,
    status: HttpStatus,
    request: WebRequest
  ): ResponseEntity<Any>? {
    logApiError(exception)

    val errorDTO = ErrorDTO(
      code = status.value(),
      errorDescription = status.reasonPhrase,
      errorMessage = exception.message ?: ""
    )

    return ResponseEntity.status(status).body(errorDTO)
  }

  override fun handleExceptionInternal(
    exception: Exception,
    body: Any,
    headers: HttpHeaders,
    status: HttpStatus,
    request: WebRequest
  ): ResponseEntity<Any>? {
    logApiError(exception)

    val errorDTO = ErrorDTO(
      code = status.value(),
      errorDescription = status.reasonPhrase,
      errorMessage = exception.message ?: ""
    )

    return ResponseEntity.status(status).body(errorDTO)
  }

  // FALLBACK EXCEPTION HANDLER
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception::class)
  fun handle(exception: Exception): ErrorDTO {
    logApiError(exception)

    return ErrorDTO(
      code = INTERNAL_SERVER_ERROR.value(),
      errorDescription = INTERNAL_SERVER_ERROR.reasonPhrase,
      errorMessage = UNEXPECTED_SERVER_ERROR_MSG
    )
  }

  @ResponseStatus(NOT_FOUND)
  @ExceptionHandler(DomainObjectNotFountException::class)
  fun handle(exception: DomainObjectNotFountException): ErrorDTO {
    logApiError(exception)

    return ErrorDTO(
      code = NOT_FOUND.value(),
      errorDescription = NOT_FOUND.reasonPhrase,
      errorMessage = RESOURCE_NOT_FOUND
    )
  }

  @ResponseStatus(BAD_REQUEST)
  @ExceptionHandler(DomainObjectValidationException::class)
  fun handle(exception: DomainObjectValidationException): ErrorDTO {
    logApiError(exception)

    return ErrorDTO(
      code = BAD_REQUEST.value(),
      errorDescription = BAD_REQUEST.reasonPhrase,
      errorMessage = RESOURCE_NOT_VALID
    )
  }

  private fun logApiError(cause: Throwable) {
    ErrorHandler.logger.error("HTTP Request Failed: ", cause)
  }

  companion object {
    const val UNEXPECTED_SERVER_ERROR_MSG = "Unexpected server error"
    const val RESOURCE_NOT_FOUND = "Requested resource is not found"
    const val RESOURCE_NOT_VALID = "Resource is not valid"

    val logger: Logger = LoggerFactory.getLogger(ErrorHandler::class.java)
  }
}