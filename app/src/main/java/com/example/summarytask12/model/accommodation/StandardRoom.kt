package com.example.summarytask12.model.accommodation


class StandardRoom(
    id: String,
    price: Double,
    isAvailable: Boolean = true
) : Room(
    id, "STANDARD", price, maxGuests = 2, isAvailable
) {
    override fun getDescribeRoom(): MutableList<String> {
       return mutableListOf("WiFi", "TV", "Điều hòa")
    }

    override fun calculatePrice(nights: Int): Double {
        return price*nights
    }

    override fun toString(): String {
        return "StandardRoom(id=$id, type=$type, price=$price, isAvailable=$isAvailable, " +
                "maxGuests=$maxGuests, describeRoom = ${getDescribeRoom()})"
    }
}