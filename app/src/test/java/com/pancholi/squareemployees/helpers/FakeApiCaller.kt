package com.pancholi.squareemployees.helpers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.pancholi.squareemployees.network.EmployeeApi
import com.pancholi.squareemployees.network.ApiCaller
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

class FakeApiCaller(private val url: HttpUrl) : ApiCaller {

  override fun getEmployeeApi(): EmployeeApi {
    return getRetrofit().create(EmployeeApi::class.java)
  }

  private fun getRetrofit(): Retrofit {
    return Retrofit.Builder()
      .baseUrl(url)
      .addConverterFactory(JacksonConverterFactory.create(jacksonObjectMapper()))
      .client(getClient())
      .build()
  }

  private fun getClient(): OkHttpClient {
    return OkHttpClient.Builder()
      .connectTimeout(1, TimeUnit.SECONDS)
      .readTimeout(1, TimeUnit.SECONDS)
      .build()
  }
}