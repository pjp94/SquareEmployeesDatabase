package com.pancholi.squareemployees.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fasterxml.jackson.annotation.JsonProperty

@Entity(tableName = "employees")
data class Employee(
  @JsonProperty("uuid")
  @PrimaryKey
  val uuid: String,
  @JsonProperty("full_name")
  val fullName: String,
  @JsonProperty("phone_number")
  val phoneNumber: String?,
  @JsonProperty("email_address")
  val emailAddress: String,
  @JsonProperty("biography")
  val biography: String?,
  @JsonProperty("photo_url_small")
  val photoUrlSmall: String?,
  @JsonProperty("photo_url_large")
  val photoUrlLarge: String?,
  @JsonProperty("team")
  val team: String,
  @JsonProperty("employee_type")
  val type: Type
) {

  enum class Type {
    FULL_TIME,
    PART_TIME,
    CONTRACTOR
  }

  fun getFormattedNumber(): String? {
    return PhoneNumberFormatter().getFormattedNumber(phoneNumber)
  }
}