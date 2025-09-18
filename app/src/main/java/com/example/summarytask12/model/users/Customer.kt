package com.example.summarytask12.model.users

import android.util.Log
import com.example.summarytask12.model.Discountable
import com.example.summarytask12.utils.MemberShipLevels

@Suppress("UNREACHABLE_CODE")
open class Customer(
    id: String,
    name: String,
    email: String,
    phone: String? = null,
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

    override fun getRole(): String = "Customer"

    override fun getDisplayInfo() {
        println("Information Customer: ID: $id, Name: $name, Email: $email, Phone: $phone, Membership Level: $membershipLevel")
    }

    override fun validateInfo() {
        if (name.isBlank()) {
            throw IllegalArgumentException("Name cannot be empty!!!")
            println("Name cannot be empty!!!")
        } else {
            println("Input name success!!!")
        }
        when (email) {
            "" -> {
                throw IllegalArgumentException("Email cannot be empty")
               println("Email cannot be empty!!!!")
            }

            else -> {
                val emailRegex = "^[A-Za-z](.*)(@)(.+)(\\.)(.+)"
                if (!email.matches(emailRegex.toRegex())) {
                    throw IllegalArgumentException("Invalid email format")
                    println("Invalid email format!!!")
                } else {
                    println("Input email success!!!!")
                }
            }
        }

        phone?.let {
            if (it.length < 10) {
                throw IllegalArgumentException("Phone number must be at least 10 digits")
               println("Phone number must be at least 10 digits!!!")
            } else {
               println("Input phone success!!!!")
            }
        } ?: println("Phone number cannot be empty!!!!")
    }

    override fun discountRate(): Double = when (membershipLevel) {
        MemberShipLevels.VIP -> 0.2
        MemberShipLevels.PREMIUM -> 0.1
        MemberShipLevels.BASIC -> 0.05
        else -> 0.0
    }

}