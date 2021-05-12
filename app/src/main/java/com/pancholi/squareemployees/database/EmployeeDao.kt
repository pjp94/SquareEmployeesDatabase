package com.pancholi.squareemployees.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pancholi.squareemployees.model.Employee

@Dao
interface EmployeeDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertEmployees(employees: List<Employee>)

  @Query("SELECT * FROM employees ORDER BY fullName ASC")
  fun getAllEmployees(): List<Employee>
}