package com.mikhailsycheuski.test.warehouse.core

import com.mikhailsycheuski.test.warehouse.domain.exception.DomainObjectValidationException
import org.springframework.web.multipart.MultipartFile
import java.lang.RuntimeException


interface FileImportProcessor {

  @Throws(
    FileParsingException::class,
    DomainObjectValidationException::class
  )
  fun process(file: MultipartFile)

  class FileParsingException(message: String, cause: Throwable) : RuntimeException(message, cause)
}