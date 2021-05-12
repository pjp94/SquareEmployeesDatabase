package com.pancholi.squareemployees.model

class PhoneNumberFormatter {

  fun getFormattedNumber(phoneNumber: String?): String? {
    val trimmedNumber = phoneNumber?.removeWhitespaces()

    if (trimmedNumber == null || trimmedNumber.isEmpty()) {
      return null
    }

    if (trimmedNumber.length > 6) {
      return formatNumberOverSixDigits(trimmedNumber)
    } else if (trimmedNumber.length > 3) {
      return formatNumberOverThreeDigits(trimmedNumber)
    }

    return trimmedNumber
  }

  private fun formatNumberOverSixDigits(number: String): String {
    val first = number.substring(0, 3)
    val middle = number.substring(3, 6)
    val end = number.substring(6)

    return "($first) $middle-$end"
  }

  private fun formatNumberOverThreeDigits(number: String): String {
    val first = number.substring(0, 3)
    val second = number.substring(3)

    return "$first-$second"
  }

  private fun String.removeWhitespaces(): String {
    return this.replace("\\s".toRegex(), "")
  }
}