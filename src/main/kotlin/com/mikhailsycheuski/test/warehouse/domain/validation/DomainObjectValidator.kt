package com.mikhailsycheuski.test.warehouse.domain.validation

import com.mikhailsycheuski.test.warehouse.domain.exception.DomainObjectValidationException


interface DomainObjectValidator<DO> {

  @Throws(DomainObjectValidationException::class)
  fun validate(domainObject: DO)

  data class ValidationResult(
    val success: Boolean,
    val errorMessage: String? = null
  ) {

    fun isFailure() = !success
    fun isSuccess() = success

    companion object {
      fun success() = ValidationResult(success = true)
      fun failure(errorMessage: String) = ValidationResult(success = false, errorMessage)
    }
  }
}