package com.pancholi.squareemployees.network

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

class SquareApiCaller : ApiCaller {

  companion object {
    private const val BASE_URL = "https://s3.amazonaws.com/sq-mobile-interview/"
  }

  override fun getEmployeeApi(): EmployeeApi {
    return getRetrofit().create(EmployeeApi::class.java)
  }

  private fun getRetrofit(): Retrofit {
    return Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(JacksonConverterFactory.create(jacksonObjectMapper()))
      .client(getClient())
      .build()
  }

  private fun getClient(): OkHttpClient {
    return OkHttpClient.Builder()
      .addInterceptor(getLoggingInterceptor())
      .connectTimeout(10, TimeUnit.SECONDS)
      .readTimeout(10, TimeUnit.SECONDS)
      .build()
  }

  private fun getLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
  }
}