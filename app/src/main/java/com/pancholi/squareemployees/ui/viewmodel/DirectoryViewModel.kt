package com.pancholi.squareemployees.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.pancholi.squareemployees.database.EmployeeRepository
import com.pancholi.squareemployees.model.Directory
import com.pancholi.squareemployees.model.Employee
import com.pancholi.squareemployees.network.NetworkResponse
import kotlinx.coroutines.*
import retrofit2.Response

class DirectoryViewModel(
  private val employeeRepository: EmployeeRepository,
  private val coroutineScope: CoroutineScope?,
  private val dispatcher: CoroutineDispatcher?
) : ViewModel() {

  val directory: LiveData<NetworkResponse<List<Employee>>> = liveData {
    emit(NetworkResponse(NetworkResponse.Status.LOADING, null, null))
    emit(fetchDirectory())
  }

  suspend fun fetchDirectory(): NetworkResponse<List<Employee>> {
    return withContext(getCoroutineDispatcher()) {
      employeeRepository.getAllEmployees()
    }
  }

  private fun getCoroutineScope(): CoroutineScope {
    return coroutineScope ?: viewModelScope
  }

  private fun getCoroutineDispatcher(): CoroutineDispatcher {
    return dispatcher ?: Dispatchers.IO
  }
}