package com.mikhailsycheuski.test.warehouse.core.api

import org.springframework.web.servlet.support.ServletUriComponentsBuilder


class LocationBuilder {

  companion object {
    fun build(resourceId: Long) =
      ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(resourceId)
        .toUriString()
  }
}