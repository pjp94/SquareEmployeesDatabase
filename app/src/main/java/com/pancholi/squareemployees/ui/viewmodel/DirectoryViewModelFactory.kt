package com.pancholi.squareemployees.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pancholi.squareemployees.database.EmployeeRepository
import com.pancholi.squareemployees.network.ApiService

@Suppress("UNCHECKED_CAST")
class DirectoryViewModelFactory(private val employeeRepository: EmployeeRepository) : ViewModelProvider.Factory {

  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(DirectoryViewModel::class.java)) {
      return DirectoryViewModel(employeeRepository, null, null) as T
    }

    throw IllegalArgumentException("Invalid class for DirectoryViewModel.")
  }
}