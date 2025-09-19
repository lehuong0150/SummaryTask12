package com.example.summarytask12.model.users


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

    public override fun validateInfo(): Boolean {
        if (name.isBlank()) {
            println("Name customer cannot be empty!!!")
            return false
        } else {
            return true
        }

        when (email) {
            "" -> {
                println("Email customer cannot be empty!!!!")
                return false
            }

            else -> {
                val emailRegex = "^[A-Za-z](.*)(@)(.+)(\\.)(.+)"
                if (!email.matches(emailRegex.toRegex())) {
                    println("Invalid email customer format!!!")
                    return false
                } else {
                    return true
                }
            }
        }

        phone?.let {
            if (it.length < 10) {
                println("Phone number customer must be at least 10 digits!!!")
                return false
            } else {
                return true
            }
        } ?: run {
            println("Phone number customer cannot be empty!!!!")
            return false
        }

    }

    override fun toString(): String {
        return "Customer: ID: $id, Name: $name, Email: $email, Phone: $phone, Membership Level: $membershipLevel\n"
    }

    override fun discountRate(): Double = when (membershipLevel) {
        MemberShipLevels.VIP -> 0.2
        MemberShipLevels.PREMIUM -> 0.1
        MemberShipLevels.BASIC -> 0.05
        else -> 0.0
    }

}