package com.mikhailsycheuski.test.warehouse.core.api

import org.springframework.web.servlet.support.ServletUriComponentsBuilder


class PathBuilder {

  companion object {
    fun buildResourceLocationPath(resourceId: Long): String =
      ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(resourceId)
        .toUriString()

    fun buildCurrentRequestPath(): String =
      ServletUriComponentsBuilder
        .fromCurrentRequest()
        .toUriString()
  }
}