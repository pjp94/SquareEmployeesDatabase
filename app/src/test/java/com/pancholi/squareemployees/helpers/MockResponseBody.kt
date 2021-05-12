package com.pancholi.squareemployees.helpers

import java.io.InputStreamReader

class MockResponseBody(private val file: String) {

  fun getBody(): String? {
    val inputStream = this.javaClass.classLoader?.getResourceAsStream(file) ?: return null
    val reader = InputStreamReader(inputStream)
    val body = reader.readText()
    reader.close()

    return body
  }
}