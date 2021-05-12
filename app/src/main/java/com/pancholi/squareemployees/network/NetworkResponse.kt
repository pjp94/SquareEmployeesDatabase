package com.pancholi.squareemployees.network

/**
 * This class is based off of Google's GithubBrowserSample Resource class to attach a status to the
 * LiveData so that the observing view can determine if the operation was successful or not:
 * https://github.com/android/architecture-components-samples/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/vo/Resource.kt
 */
data class NetworkResponse<out T>(
  val status: Status,
  val data: T?,
  val message: String?
) {

  enum class Status {
    SUCCESS,
    ERROR,
    LOADING
  }

  companion object {

    fun <T> success(data: T): NetworkResponse<T> =
      NetworkResponse(Status.SUCCESS, data, null)

    fun <T> error(data: T?, message: String): NetworkResponse<T> =
      NetworkResponse(Status.ERROR, data, message)

    fun <T> loading(data: T?): NetworkResponse<T> =
      NetworkResponse(Status.LOADING, data, null)
  }
}