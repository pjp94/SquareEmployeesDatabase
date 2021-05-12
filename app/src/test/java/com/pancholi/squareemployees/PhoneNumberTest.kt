package com.pancholi.squareemployees

import com.pancholi.squareemployees.model.PhoneNumberFormatter
import org.junit.Assert
import org.junit.Test

class PhoneNumberTest {

  @Test
  fun phoneNumberTenDigits() {
    val phoneNumber = "1234567890"
    val formattedNumber = PhoneNumberFormatter().getFormattedNumber(phoneNumber)

    Assert.assertEquals("(123) 456-7890", formattedNumber)
  }

  @Test
  fun phoneNumberTenDigitsWithSpaces() {
    val phoneNumber = " 123 456 7890  "
    val formattedNumber = PhoneNumberFormatter().getFormattedNumber(phoneNumber)

    Assert.assertEquals("(123) 456-7890", formattedNumber)
  }

  @Test
  fun phoneNumberNineDigits() {
    val phoneNumber = "123456789"
    val formattedNumber = PhoneNumberFormatter().getFormattedNumber(phoneNumber)

    Assert.assertEquals("(123) 456-789", formattedNumber)
  }

  @Test
  fun phoneNumberSixDigits() {
    val phoneNumber = "123456"
    val formattedNumber = PhoneNumberFormatter().getFormattedNumber(phoneNumber)

    Assert.assertEquals("123-456", formattedNumber)
  }

  @Test
  fun phoneNumberFourDigits() {
    val phoneNumber = "1234"
    val formattedNumber = PhoneNumberFormatter().getFormattedNumber(phoneNumber)

    Assert.assertEquals("123-4", formattedNumber)
  }

  @Test
  fun phoneNumberThreeDigits() {
    val phoneNumber = "123"
    val formattedNumber = PhoneNumberFormatter().getFormattedNumber(phoneNumber)

    Assert.assertEquals("123", formattedNumber)
  }

  @Test
  fun phoneNumberNull() {
    val phoneNumber = null
    val formattedNumber = PhoneNumberFormatter().getFormattedNumber(phoneNumber)

    Assert.assertNull(formattedNumber)
  }

  @Test
  fun phoneNumberEmpty() {
    val phoneNumber = ""
    val formattedNumber = PhoneNumberFormatter().getFormattedNumber(phoneNumber)

    Assert.assertNull(formattedNumber)
  }

  @Test
  fun phoneNumberEmptyWithSpaces() {
    val phoneNumber = " "
    val formattedNumber = PhoneNumberFormatter().getFormattedNumber(phoneNumber)

    Assert.assertNull(formattedNumber)
  }
}