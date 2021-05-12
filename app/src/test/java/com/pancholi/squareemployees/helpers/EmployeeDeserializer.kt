package com.pancholi.squareemployees.helpers

import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.pancholi.squareemployees.model.Directory
import kotlin.jvm.Throws

class EmployeeDeserializer(private val responseType: ResponseType) {

  private val mapper = jacksonObjectMapper()

  enum class ResponseType {
    SINGLE,
    MULTIPLE,
    NULLS,
    EMPTY,
    MALFORMED,
    EXCEPTION
  }

  fun getEmployees(): Directory {
    return when (responseType) {
      ResponseType.SINGLE -> getSingleEmployee()
      ResponseType.MULTIPLE -> getMultipleEmployees()
      ResponseType.NULLS -> getEmployeeWithNullFields()
      ResponseType.EMPTY -> getEmptyEmployees()
      ResponseType.MALFORMED -> getMalformedEmployee()
      ResponseType.EXCEPTION -> getEmployeeInvalidJson()
    }
  }

  private fun getSingleEmployee(): Directory {
    val json = """
      {
        "employees" : 
        [
          {
            "uuid" : "0d8fcc12-4d0c-425c-8355-390b312b909c",
            "full_name" : "Justine Mason",
            "phone_number" : "5553280123",
            "email_address" : "jmason.demo@squareup.com",
            "biography" : "Engineer on the Point of Sale team.",
            "photo_url_small" : "https://s3.amazonaws.com/sq-mobile-interview/photos/16c00560-6dd3-4af4-97a6-d4754e7f2394/small.jpg",
            "photo_url_large" : "https://s3.amazonaws.com/sq-mobile-interview/photos/16c00560-6dd3-4af4-97a6-d4754e7f2394/large.jpg",
            "team" : "Point of Sale",
            "employee_type" : "FULL_TIME"
          }
        ]
      }
    """.trimIndent()

    return mapper.readValue(json)
  }

  private fun getMultipleEmployees(): Directory {
    val json = """
      {
        "employees": 
        [
          {
            "uuid": "0d8fcc12-4d0c-425c-8355-390b312b909c",
            "full_name": "Justine Mason",
            "phone_number": "5553280123",
            "email_address": "jmason.demo@squareup.com",
            "biography": "Engineer on the Point of Sale team.",
            "photo_url_small": "https://s3.amazonaws.com/sq-mobile-interview/photos/16c00560-6dd3-4af4-97a6-d4754e7f2394/small.jpg",
            "photo_url_large": "https://s3.amazonaws.com/sq-mobile-interview/photos/16c00560-6dd3-4af4-97a6-d4754e7f2394/large.jpg",
            "team": "Point of Sale",
            "employee_type": "FULL_TIME"
          },
          {
            "uuid": "a98f8a2e-c975-4ba3-8b35-01f719e7de2d",
            "full_name": "Camille Rogers",
            "phone_number": "5558531970",
            "email_address": "crogers.demo@squareup.com",
            "biography": "Designer on the web marketing team.",
            "photo_url_small": "https://s3.amazonaws.com/sq-mobile-interview/photos/5095a907-abc9-4734-8d1e-0eeb2506bfa8/small.jpg",
            "photo_url_large": "https://s3.amazonaws.com/sq-mobile-interview/photos/5095a907-abc9-4734-8d1e-0eeb2506bfa8/large.jpg",
            "team": "Public Web & Marketing",
            "employee_type": "PART_TIME"
          },
          {
            "uuid": "b8cf3382-ecf2-4240-b8ab-007688426e8c",
            "full_name": "Richard Stein",
            "phone_number": "5557223332",
            "email_address": "rstein.demo@squareup.com",
            "biography": "Product manager for the Point of sale app!",
            "photo_url_small": "https://s3.amazonaws.com/sq-mobile-interview/photos/43ed39b3-fbc0-4eb8-8ed3-6a8de479a52a/small.jpg",
            "photo_url_large": "https://s3.amazonaws.com/sq-mobile-interview/photos/43ed39b3-fbc0-4eb8-8ed3-6a8de479a52a/large.jpg",
            "team": "Point of Sale",
            "employee_type": "PART_TIME"
          }
        ]
      }
    """.trimIndent()

    return mapper.readValue(json)
  }

  private fun getEmployeeWithNullFields(): Directory {
    val json = """
      {
        "employees" : 
        [
          {
            "uuid" : "0d8fcc12-4d0c-425c-8355-390b312b909c",
            "full_name" : "Justine Mason",
            "phone_number" : null,
            "email_address" : "jmason.demo@squareup.com",
            "biography" : null,
            "photo_url_small" : null,
            "photo_url_large" : null,
            "team" : "Point of Sale",
            "employee_type" : "FULL_TIME"
          }
        ]
      }
    """.trimIndent()

    return mapper.readValue(json)
  }

  private fun getEmptyEmployees(): Directory {
    val json = """
      {
        "employees": []
      }
    """.trimIndent()

    return jacksonObjectMapper().readValue(json)
  }

  @Throws(MissingKotlinParameterException::class)
  private fun getMalformedEmployee(): Directory {
    val json = """
      {
        "employees" : 
        [
          {
            "uuid" : "0d8fcc12-4d0c-425c-8355-390b312b909c",
            "full_name" : "Justine Mason",
            "phone_number" : "5553280123",
            "biography" : "Engineer on the Point of Sale team.",
            "photo_url_small": "https://s3.amazonaws.com/sq-mobile-interview/photos/16c00560-6dd3-4af4-97a6-d4754e7f2394/small.jpg",
            "photo_url_large": "https://s3.amazonaws.com/sq-mobile-interview/photos/16c00560-6dd3-4af4-97a6-d4754e7f2394/large.jpg",
            "team" : "Point of Sale",
            "employee_type" : "FULL_TIME"
          }
        ]
      }
    """.trimIndent()

    return mapper.readValue(json)
  }

  @Throws(JsonMappingException::class)
  private fun getEmployeeInvalidJson(): Directory {
    val json = """
      {
        "employees" : 
        [
          {
            "uuid" : "0d8fcc12-4d0c-425c-8355-390b312b909c",,
            "full_name" : "Justine Mason",
            "phone_number" : "5553280123",
            "email_address": "jmason.demo@squareup.com",
            "biography" : "Engineer on the Point of Sale team.",
            "photo_url_small": "https://s3.amazonaws.com/sq-mobile-interview/photos/16c00560-6dd3-4af4-97a6-d4754e7f2394/small.jpg",
            "photo_url_large": "https://s3.amazonaws.com/sq-mobile-interview/photos/16c00560-6dd3-4af4-97a6-d4754e7f2394/large.jpg",
            "team" : "Point of Sale",
            "employee_type" : "FULL_TIME"
          }
        ]
      }
    """.trimIndent()

    return mapper.readValue(json)
  }
}