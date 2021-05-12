package com.pancholi.squareemployees

import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import com.pancholi.squareemployees.helpers.EmployeeDeserializer
import com.pancholi.squareemployees.model.Directory
import com.pancholi.squareemployees.model.Employee
import org.junit.Assert
import org.junit.Test

class EmployeeTest {

  private fun compareDirectories(expected: Directory, actual: Directory) {
    Assert.assertEquals(expected, actual)
  }

  @Test
  fun getSingleEmployee() {
    val employee = Employee(
      "0d8fcc12-4d0c-425c-8355-390b312b909c",
      "Justine Mason",
      "5553280123",
      "jmason.demo@squareup.com",
      "Engineer on the Point of Sale team.",
      "https://s3.amazonaws.com/sq-mobile-interview/photos/16c00560-6dd3-4af4-97a6-d4754e7f2394/small.jpg",
      "https://s3.amazonaws.com/sq-mobile-interview/photos/16c00560-6dd3-4af4-97a6-d4754e7f2394/large.jpg",
      "Point of Sale",
      Employee.Type.FULL_TIME
    )

    val expected = Directory(mutableListOf(employee))
    val result = EmployeeDeserializer(EmployeeDeserializer.ResponseType.SINGLE).getEmployees()
    compareDirectories(expected, result)
  }

  @Test
  fun getMultipleEmployees() {
    val employee1 = Employee(
      "0d8fcc12-4d0c-425c-8355-390b312b909c",
      "Justine Mason",
      "5553280123",
      "jmason.demo@squareup.com",
      "Engineer on the Point of Sale team.",
      "https://s3.amazonaws.com/sq-mobile-interview/photos/16c00560-6dd3-4af4-97a6-d4754e7f2394/small.jpg",
      "https://s3.amazonaws.com/sq-mobile-interview/photos/16c00560-6dd3-4af4-97a6-d4754e7f2394/large.jpg",
      "Point of Sale",
      Employee.Type.FULL_TIME
    )
    val employee2 = Employee(
      "a98f8a2e-c975-4ba3-8b35-01f719e7de2d",
      "Camille Rogers",
      "5558531970",
      "crogers.demo@squareup.com",
      "Designer on the web marketing team.",
      "https://s3.amazonaws.com/sq-mobile-interview/photos/5095a907-abc9-4734-8d1e-0eeb2506bfa8/small.jpg",
      "https://s3.amazonaws.com/sq-mobile-interview/photos/5095a907-abc9-4734-8d1e-0eeb2506bfa8/large.jpg",
      "Public Web & Marketing",
      Employee.Type.PART_TIME
    )
    val employee3 = Employee(
      "b8cf3382-ecf2-4240-b8ab-007688426e8c",
      "Richard Stein",
      "5557223332",
      "rstein.demo@squareup.com",
      "Product manager for the Point of sale app!",
      "https://s3.amazonaws.com/sq-mobile-interview/photos/43ed39b3-fbc0-4eb8-8ed3-6a8de479a52a/small.jpg",
      "https://s3.amazonaws.com/sq-mobile-interview/photos/43ed39b3-fbc0-4eb8-8ed3-6a8de479a52a/large.jpg",
      "Point of Sale",
      Employee.Type.PART_TIME
    )

    val expected = Directory(mutableListOf(employee1, employee2, employee3))
    val result = EmployeeDeserializer(EmployeeDeserializer.ResponseType.MULTIPLE).getEmployees()
    compareDirectories(expected, result)
  }

  @Test
  fun getEmployeeWithNulls() {
    val employee = Employee(
      "0d8fcc12-4d0c-425c-8355-390b312b909c",
      "Justine Mason",
      null,
      "jmason.demo@squareup.com",
      null,
      null,
      null,
      "Point of Sale",
      Employee.Type.FULL_TIME
    )

    val expected = Directory(mutableListOf(employee))
    val result = EmployeeDeserializer(EmployeeDeserializer.ResponseType.NULLS).getEmployees()
    compareDirectories(expected, result)
  }

  @Test
  fun getEmptyEmployees() {
    val expected = Directory(mutableListOf())
    val result = EmployeeDeserializer(EmployeeDeserializer.ResponseType.EMPTY).getEmployees()
    compareDirectories(expected, result)
  }

  @Test(expected = MissingKotlinParameterException::class)
  fun getMalformedEmployeeThrowsException() {
    EmployeeDeserializer(EmployeeDeserializer.ResponseType.MALFORMED).getEmployees()
  }

  @Test(expected = JsonMappingException::class)
  fun getEmployeeThrowsException() {
    EmployeeDeserializer(EmployeeDeserializer.ResponseType.EXCEPTION).getEmployees()
  }
}