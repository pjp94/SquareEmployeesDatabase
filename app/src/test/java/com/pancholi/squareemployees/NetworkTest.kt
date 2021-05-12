package com.pancholi.squareemployees

import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import com.pancholi.squareemployees.helpers.FakeApiCaller
import com.pancholi.squareemployees.helpers.MockResponseBody
import com.pancholi.squareemployees.model.Directory
import com.pancholi.squareemployees.model.Employee
import com.pancholi.squareemployees.network.ApiService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class NetworkTest {

  private lateinit var mockWebServer: MockWebServer

  @Before
  fun setUp() {
    mockWebServer = MockWebServer()
    mockWebServer.start()
  }

  @After
  fun tearDown() {
    mockWebServer.shutdown()
  }

  @Test
  fun getResponseNotNull() {
    val body = MockResponseBody("employees.json").getBody()
    Assert.assertNotNull(body)
  }

  @Test
  fun responseHasSuccessCode() {
    val body = MockResponseBody("employees.json").getBody()
    val mockResponse = MockResponse()
      .setResponseCode(200)
      .setBody(body ?: "")
    mockWebServer.enqueue(mockResponse)

    runBlocking {
      val actualResponse = ApiService(
        FakeApiCaller(mockWebServer.url("/"))
      ).getEmployees()

      Assert.assertTrue(actualResponse.code() == 200)
    }
  }

  @Test
  fun directoryWithEmployeesCreated() {
    val body = MockResponseBody("employees.json").getBody()
    val response = MockResponse()
      .setResponseCode(200)
      .setBody(body ?: "")
    mockWebServer.enqueue(response)

    runBlocking {
      val actual = ApiService(
        FakeApiCaller(mockWebServer.url("/"))
      ).getEmployees()

      val expected = Directory(
        mutableListOf(
          Employee(
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
        )
      )

      Assert.assertEquals(expected, actual.body())
    }
  }

  @Test
  fun emptyDirectoryCreated() {
    val body = MockResponseBody("employees_empty.json").getBody()
    val response = MockResponse()
      .setResponseCode(200)
      .setBody(body ?: "")
    mockWebServer.enqueue(response)

    runBlocking {
      val actual = ApiService(
        FakeApiCaller(mockWebServer.url("/"))
      ).getEmployees()

      val expected = Directory(mutableListOf())

      Assert.assertEquals(expected, actual.body())
    }
  }

  @Test(expected = MissingKotlinParameterException::class)
  fun getEmployeeMalformedResponse() {
    val body = MockResponseBody("employees_malformed.json").getBody()
    val response = MockResponse()
      .setResponseCode(200)
      .setBody(body ?: "")
    mockWebServer.enqueue(response)

    runBlocking {
      ApiService(FakeApiCaller(mockWebServer.url("/"))).getEmployees()
    }
  }
}