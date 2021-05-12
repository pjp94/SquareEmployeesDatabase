package com.pancholi.squareemployees.network

import com.pancholi.squareemployees.model.Directory
import retrofit2.Response
import retrofit2.http.GET

interface EmployeeApi {

  @GET("employees.json")
  suspend fun getEmployees(): Response<Directory>

  @GET("employees_malformed.json")
  suspend fun getMalformedEmployees(): Response<Directory>

  @GET("employees_empty.json")
  suspend fun getEmptyEmployees(): Response<Directory>
}