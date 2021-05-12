package com.pancholi.squareemployees.database

import android.content.Context
import com.pancholi.squareemployees.model.Employee
import com.pancholi.squareemployees.network.ApiService
import com.pancholi.squareemployees.network.NetworkResponse

class EmployeeRepository(private val context: Context, private val apiService: ApiService) {

  private val database = EmployeeDatabase.getInstance(context)

  suspend fun getAllEmployees(): NetworkResponse<List<Employee>> {
    val preferences = context.getSharedPreferences("SquarePreferences", Context.MODE_PRIVATE)
    val lastUpdated = preferences.getLong("LastUpdated", -1)
    val dayInMillis = 3600 * 24 * 1000

    if (System.currentTimeMillis() - lastUpdated > dayInMillis) {
      val response = apiService.getEmployees()

      if (response.isSuccessful) {
        val directory = response.body()
        insertEmployees(directory?.employees!!)
      } else {
        return NetworkResponse(NetworkResponse.Status.ERROR, null, "Network error")
      }

      preferences.edit().putLong("LastUpdated", System.currentTimeMillis()).apply()
    }

    val employees = database.employeeDao().getAllEmployees()
    return NetworkResponse(NetworkResponse.Status.SUCCESS, employees, null)
  }

  private fun insertEmployees(employees: List<Employee>) {
    database.employeeDao().insertEmployees(employees)
  }

}