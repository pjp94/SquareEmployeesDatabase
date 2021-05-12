package com.pancholi.squareemployees.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pancholi.squareemployees.R
import com.pancholi.squareemployees.database.EmployeeRepository
import com.pancholi.squareemployees.model.Directory
import com.pancholi.squareemployees.model.Employee
import com.pancholi.squareemployees.network.ApiService
import com.pancholi.squareemployees.network.SquareApiCaller
import com.pancholi.squareemployees.network.NetworkResponse
import com.pancholi.squareemployees.ui.viewmodel.DirectoryViewModel
import com.pancholi.squareemployees.ui.viewmodel.DirectoryViewModelFactory
import com.pancholi.squareemployees.util.Logger
import kotlinx.android.synthetic.main.activity_directory.*
import kotlinx.android.synthetic.main.loading_progress_bar.*

class DirectoryActivity : AppCompatActivity() {

  private lateinit var employeeAdapter: EmployeeAdapter
  private lateinit var directoryViewModel: DirectoryViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_directory)
    setViewModel()
    setAdapter()
    loadEmployees()
  }

  private fun setViewModel() {
    val apiService = ApiService(SquareApiCaller())
    val repository = EmployeeRepository(this, apiService)
    directoryViewModel = ViewModelProvider(this, DirectoryViewModelFactory(repository))
      .get(DirectoryViewModel::class.java)
  }

  private fun setAdapter() {
    employeeAdapter = EmployeeAdapter()
    employeeList.layoutManager = LinearLayoutManager(this)
    employeeList.setHasFixedSize(true)
    employeeList.adapter = employeeAdapter
  }

  private fun loadEmployees() {
    directoryViewModel.directory.observe(this, getNetworkResponseObserver())
  }

  private fun getNetworkResponseObserver(): Observer<NetworkResponse<List<Employee>>> {
    return Observer<NetworkResponse<List<Employee>>> {
      when (it.status) {
        NetworkResponse.Status.SUCCESS -> handleSuccess(it.data)
        NetworkResponse.Status.ERROR -> handleError(it.message)
        NetworkResponse.Status.LOADING -> handleLoading()
      }
    }
  }

  private fun handleSuccess(employees: List<Employee>?) {
    setVisibility(progressBar, View.GONE)

    if (employees != null && employees.isNotEmpty()) {
      setVisibility(errorMessage, View.GONE)
      employeeAdapter.setEmployees(employees)
    } else {
      setVisibility(errorMessage, View.VISIBLE)
    }
  }

  private fun handleError(message: String?) {
    setVisibility(progressBar, View.GONE)
    setVisibility(errorMessage, View.VISIBLE)

    if (message != null) {
      Logger.log("Error getting employees: $message")
    }
  }

  private fun handleLoading() {
    setVisibility(progressBar, View.VISIBLE)
  }

  private fun setVisibility(view: View, visibility: Int) {
    view.visibility = visibility
  }
}