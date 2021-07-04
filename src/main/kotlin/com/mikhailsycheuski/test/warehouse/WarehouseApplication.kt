package com.mikhailsycheuski.test.warehouse

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.mikhailsycheuski.test.warehouse"])
class WarehouseApplication

fun main(args: Array<String>) {
  runApplication<WarehouseApplication>(*args)
}

