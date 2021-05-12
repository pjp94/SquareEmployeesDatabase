package com.pancholi.squareemployees.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pancholi.squareemployees.model.Employee

@Database(entities = [Employee::class], version = 1)
abstract class EmployeeDatabase : RoomDatabase() {

  abstract fun employeeDao(): EmployeeDao

  companion object {

    private const val DATABASE_NAME = "EmployeeDatabase"

    @Volatile
    private var instance: EmployeeDatabase? = null

    fun getInstance(context: Context): EmployeeDatabase {
      return instance ?: synchronized(this) {
        instance ?: buildDatabase(context).also { instance = it }
      }
    }

    private fun buildDatabase(context: Context): EmployeeDatabase {
      return Room.databaseBuilder(context, EmployeeDatabase::class.java, DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .build()
    }
  }
}