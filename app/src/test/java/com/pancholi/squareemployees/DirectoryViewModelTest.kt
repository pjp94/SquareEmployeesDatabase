package com.pancholi.squareemployees

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.pancholi.squareemployees.helpers.TestException
import com.pancholi.squareemployees.model.Directory
import com.pancholi.squareemployees.network.ApiService
import com.pancholi.squareemployees.network.NetworkResponse
import com.pancholi.squareemployees.ui.viewmodel.DirectoryViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import retrofit2.Response

@ExperimentalCoroutinesApi
class DirectoryViewModelTest {

  @Rule
  @JvmField
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  @Mock
  private lateinit var apiService: ApiService
  @Mock
  private lateinit var observer: Observer<NetworkResponse<Directory>>
  private lateinit var directoryViewModel: DirectoryViewModel

  private val testDispatcher = TestCoroutineDispatcher()
  private val testScope = TestCoroutineScope(testDispatcher)

  @Before
  fun setUp() {
    MockitoAnnotations.openMocks(this)
    directoryViewModel = DirectoryViewModel(apiService, testScope, testDispatcher)
    directoryViewModel.getDirectory().observeForever(observer)
  }

  @After
  fun tearDown() {
    testDispatcher.cleanupTestCoroutines()
    testScope.cleanupTestCoroutines()
  }

  @Test
  fun initialStateLoading() {
    verify(observer).onChanged(NetworkResponse.loading(null))
  }

  @Test
  fun getEmployeesSuccess() {
    val response = Response.success(Directory(mutableListOf()))

    runBlocking {
      `when`(apiService.getEmployees()).thenReturn(response)
      verify(observer).onChanged(NetworkResponse.loading(null))
      directoryViewModel.fetchDirectory()
      verify(observer).onChanged(NetworkResponse.success(response.body()!!))
    }
  }

  @Test
  fun getEmployeeError() {
    val response = Response.error<Directory>(400,
      "{}".toResponseBody("application/json".toMediaTypeOrNull()))

    runBlocking {
      `when`(apiService.getEmployees()).thenReturn(response)
      verify(observer).onChanged(NetworkResponse.loading(null))
      directoryViewModel.fetchDirectory()
      verify(observer).onChanged(NetworkResponse.error(
        null, "Getting employees was unsuccessful, code 400"))
    }
  }

  @Test
  fun getEmployeeException() {
    runBlocking {
      `when`(apiService.getEmployees()).thenThrow(TestException("Testing exception"))
      verify(observer).onChanged(NetworkResponse.loading(null))
      directoryViewModel.fetchDirectory()
      verify(observer).onChanged(NetworkResponse.error(
        null, "Failed to get employees: Testing exception"))
    }
  }
}