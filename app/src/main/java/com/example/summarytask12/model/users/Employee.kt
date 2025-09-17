package com.example.summarytask12.model.users

import android.util.Log
import com.example.summarytask12.utils.Shifts

@Suppress("UNREACHABLE_CODE")
class Employee(
    id: String,
    name: String,
    email: String,
    phone: String? = null,
    private var shift: Shifts = Shifts.MORNING
) : Person(id, name, email, phone) {

    constructor(id: String, name: String, email: String, phone: String?, shift: String) :
            this(
                id,
                name,
                email,
                phone,
                when (shift.uppercase()) {
                    "MORNING" -> Shifts.MORNING
                    "AFTERNOON" -> Shifts.AFTERNOON
                    "NIGHT" -> Shifts.NIGHT
                    else -> Shifts.MORNING // giá trị mặc định
                }
            )
    val getShift = shift
    fun setShift(shift: Shifts) {
        this.shift = shift
    }

    override fun getRole(): String = "Employee"

    override fun getDisplayInfo() {
        Log.d(
            "InfoEmployee",
            "getDisplayInfo: ID: $id, Name: $name, Email: $email, Phone: $phone, Shift: $shift"
        )
    }

    override fun validateInfo() {
        if (name.isBlank()) {
            throw IllegalArgumentException("Name cannot be empty")
            Log.d("Error", "validateInfo: " + "Name cannot be empty")
        } else {
            Log.d("Input name success", "validateInfo: ")
        }
        when (email) {
            "" -> {
                throw IllegalArgumentException("Email cannot be empty")
                Log.d("Error", "validateInfo: " + "Email cannot be empty")
            }

            else -> {
                val emailRegex = "^[A-Za-z](.*)(@)(.+)(\\.)(.+)"
                if (!email.matches(emailRegex.toRegex())) {
                    throw IllegalArgumentException("Invalid email format")
                    Log.d("Error", "validateInfo: " + "Invalid email format")
                } else {
                    Log.d("Input email success", "validateInfo: ")
                }
            }
        }

        phone?.let {
            if (it.length < 10) {
                throw IllegalArgumentException("Phone number must be at least 10 digits")
                Log.d("Error", "validateInfo: " + "Phone number must be at least 10 digits")
            } else {
                Log.d("Input phone success", "validateInfo: ")
            }
        } ?: Log.d("Error", "validateInfo: Phone number cannot be empty")
    }
}