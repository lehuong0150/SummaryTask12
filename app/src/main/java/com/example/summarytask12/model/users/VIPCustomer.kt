package com.example.summarytask12.model.users

import android.util.Log

class VIPCustomer(
    id: String,
    name: String,
    email: String,
    phone: String? = null,
    private var specialRequests: MutableList<String>? = null,
) : Customer(id, name, email, phone) {


    override fun getDisplayInfo() {
        Log.d(
            "InfoCustomer",
            "getDisplayInfo: ID: $id, Name: $name, Email: $email, Phone: $phone, Membership Level: VIP, SpecialRequests: $getSpecialRequests"
        )
    }

    override fun toString(): String {
        return "VIPCustomer(id=$id, name=$name, email=$email, phone=$phone, membershipLevel=VIP, SpecialRequests =$getSpecialRequests)"
    }

    private val getSpecialRequests = specialRequests

    fun addSpecialRequest(request: String) {
        specialRequests?.add(request)
    }

}