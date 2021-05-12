package com.pancholi.squareemployees.util

import android.util.Log

object Logger {

  private const val TAG = "SquareEmployeesTag"

  fun log(message: String) {
    Log.d(TAG, message)
  }
}