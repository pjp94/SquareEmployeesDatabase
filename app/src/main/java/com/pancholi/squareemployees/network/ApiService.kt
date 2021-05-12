package com.pancholi.squareemployees.network

import com.pancholi.squareemployees.model.Directory
import retrofit2.Response

class ApiService(caller: ApiCaller) {

  private val api = caller.getEmployeeApi()

  suspend fun getEmployees(): Response<Directory> {
    return api.getEmployees()
  }

  suspend fun getMalformedEmployees(): Response<Directory> {
    return api.getMalformedEmployees()
  }

  suspend fun getEmptyEmployees(): Response<Directory> {
    return api.getEmptyEmployees()
  }
}