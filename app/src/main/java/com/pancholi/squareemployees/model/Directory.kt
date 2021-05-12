package com.pancholi.squareemployees.model

data class Directory(val employees: List<Employee>) {

  override fun equals(other: Any?): Boolean {
    if (other !is Directory) {
      return false
    }

    return this.employees == other.employees
  }

  override fun hashCode(): Int {
    return employees.hashCode()
  }
}