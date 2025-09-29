package com.example.summarytask12.model.users

class VIPCustomer(
    id: String,
    name: String,
    email: String,
    phone: String? = null,
    private var specialRequests: MutableList<String>? = null,
) : Customer(id, name, email, phone) {

    override fun getDisplayInfo() {
        return println(
            "Information Customer VIP: ID: $id, Name: $name, Email: $email, " +
                    "Phone: $phone, Membership Level: VIP, SpecialRequests: $getSpecialRequests \n"
        )
    }

    override fun toString(): String {
        return "VIPCustomer(id=$id, name=$name, email=$email, phone=$phone, membershipLevel=VIP," +
                " SpecialRequests =$getSpecialRequests)"
    }

    private val getSpecialRequests = specialRequests

    fun addSpecialRequest(request: String) {
        specialRequests?.add(request)
    }
}