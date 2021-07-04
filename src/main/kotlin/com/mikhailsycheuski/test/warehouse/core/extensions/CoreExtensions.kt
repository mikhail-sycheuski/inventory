package com.mikhailsycheuski.test.warehouse.core.extensions

import java.util.*


fun <T> Optional<T>.unwrap(): T? = orElse(null)