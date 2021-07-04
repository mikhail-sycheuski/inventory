package com.mikhailsycheuski.test.warehouse.core.extensions

import org.springframework.core.convert.converter.Converter
import org.springframework.core.convert.converter.ConverterRegistry
import javax.annotation.PostConstruct


abstract class SelfRegisteringConverter<Source, Target>(
  private val converterRegistry: ConverterRegistry
) : Converter<Source, Target> {

  @PostConstruct
  fun selfRegister() {
    converterRegistry.addConverter(this);
  }
}