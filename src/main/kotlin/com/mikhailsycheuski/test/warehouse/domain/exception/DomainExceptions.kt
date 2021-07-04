package com.mikhailsycheuski.test.warehouse.domain.exception


open class DomainException : RuntimeException {
  constructor(message: String) : super(message)
  constructor(message: String, cause: Throwable) : super(message, cause)
}

class DomainObjectValidationException : DomainException {
  constructor(message: String) : super(message)
  constructor(message: String, cause: Throwable) : super(message, cause)
}

class DomainObjectNotFountException : DomainException {
  constructor(message: String) : super(message)
  constructor(message: String, cause: Throwable) : super(message, cause)
}

