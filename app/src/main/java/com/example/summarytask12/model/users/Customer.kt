package com.example.summarytask12.model.users

import android.util.Log
import com.example.summarytask12.model.Discountable
import com.example.summarytask12.utils.MemberShipLevels

@Suppress("UNREACHABLE_CODE")
open class Customer(
    override val id: String,
    override val name: String,
    override val email: String,
    override val phone: String? = null,
    private var membershipLevel: MemberShipLevels? = MemberShipLevels.BASIC
) : Person(id, name, email, phone), Discountable {

    constructor(
        id: String,
        name: String,
        email: String,
        phone: String?,
        membershipLevel: String
    ) : this(
        id, name, email, phone, when (membershipLevel) {
            "VIP" -> MemberShipLevels.VIP
            "PREMIUM" -> MemberShipLevels.PREMIUM
            "BASIC" -> MemberShipLevels.BASIC
            else -> MemberShipLevels.BASIC
        }
    )

    val getMembershipLevel = membershipLevel
    fun setMembershipLevel(level: MemberShipLevels?) {
        membershipLevel = level
    }

    override fun getRole(): String = "Customer"

    override fun getDisplayInfo() {
        Log.d(
            "InfoCustomer",
            "getDisplayInfo: ID: $id, Name: $name, Email: $email, Phone: $phone, Membership Level: $membershipLevel"
        )
    }


    override fun toString(): String {
        return "Customer(id=$id, name=$name, email=$email, phone=$phone, membershipLevel= $membershipLevel)"
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

    override fun discountRate(): Double = when (membershipLevel) {
        MemberShipLevels.VIP-> 0.2
        MemberShipLevels.PREMIUM -> 0.1
        MemberShipLevels.BASIC -> 0.05
        else -> 0.0
    }

}