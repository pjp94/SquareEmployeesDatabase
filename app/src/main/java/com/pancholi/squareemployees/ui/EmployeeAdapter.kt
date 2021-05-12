package com.pancholi.squareemployees.ui

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pancholi.squareemployees.model.Employee
import com.pancholi.squareemployees.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_details.view.*
import kotlinx.android.synthetic.main.card_photo.view.*
import kotlinx.android.synthetic.main.employee_card.view.*

class EmployeeAdapter : RecyclerView.Adapter<EmployeeAdapter.ViewHolder>() {

  private var employees = listOf<Employee>()

  fun setEmployees(employees: List<Employee>) {
    this.employees = employees
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val cardView = LayoutInflater.from(parent.context)
      .inflate(R.layout.employee_card, parent, false)
    return ViewHolder(cardView)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val employee = employees[position]
    holder.bind(employee)
  }

  override fun getItemCount(): Int {
    return employees.size
  }

  inner class ViewHolder(cardView: View) : RecyclerView.ViewHolder(cardView) {

    private val photo = cardView.employee_card_photo.cardPhoto
    private val name = cardView.employee_card_details.cardName
    private val team = cardView.employee_card_details.cardTeam
    private val email = cardView.employee_card_details.cardEmail
    private val number = cardView.employee_card_details.cardNumber

    fun bind(employee: Employee) {
      loadPhoto(employee)
      setDetails(employee)
    }

    private fun loadPhoto(employee: Employee) {
      Picasso.get()
        .load(Uri.parse(employee.photoUrlLarge))
        .resizeDimen(R.dimen.photo_size, R.dimen.photo_size)
        .centerInside()
        .into(photo)
    }

    private fun setDetails(employee: Employee) {
      name.text = employee.fullName
      team.text = employee.team
      email.text = employee.emailAddress
      setPhoneNumber(employee)
    }

    private fun setPhoneNumber(employee: Employee) {
      val formattedNumber = employee.getFormattedNumber()

      if (formattedNumber != null) {
        number.apply {
          text = formattedNumber
          visibility = View.VISIBLE
        }
      }
    }
  }
}