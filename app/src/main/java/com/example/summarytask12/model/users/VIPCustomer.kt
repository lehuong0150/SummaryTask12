package com.example.summarytask12.model.users

import android.util.Log

class VIPCustomer(
    id: String,
    name: String,
    email: String,
    phone: String? = null,
    private var loyaltyPoints: Int = 0,
) : Customer(id, name, email, phone) {


    override fun getDisplayInfo() {
        Log.d(
            "InfoCustomer",
            "getDisplayInfo: ID: $id, Name: $name, Email: $email, Phone: $phone, Membership Level: VIP, Loyalty Points: $loyaltyPoints"
        )
    }

    override fun toString(): String {
        return "VIPCustomer(id=$id, name=$name, email=$email, phone=$phone, membershipLevel=VIP,Loyalty Points =$loyaltyPoints)"
    }

    val getLoyaltyPoints = loyaltyPoints
    fun setLoyaltyPoints(loyaltyPoints: Int){
         this.loyaltyPoints = loyaltyPoints
    }

}