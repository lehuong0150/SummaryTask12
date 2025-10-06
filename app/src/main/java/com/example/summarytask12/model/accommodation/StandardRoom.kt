package com.example.summarytask12.model.accommodation

import com.example.summarytask12.model.Discountable


class StandardRoom(
    id: String,
    numberRoom: String,
    price: Double,
    isAvailable: Boolean = true
) : Room(
    id, "112", "STANDARD", price, maxGuests = 2, isAvailable
), Discountable {
    override fun getDescribeRoom(): MutableList<String> {
        return mutableListOf("WiFi", "TV", "Điều hòa")
    }

    override fun calculatePrice(nights: Int): Double {
        val basePrice = price * nights
        return applyDiscount(basePrice)
    }

    override fun discountRate(): Double {
        return 0.05
    }

    override fun toString(): String {
        return "StandardRoom(id= $id, numberRoom= $numberRoom, type= $type, price= $price, isAvailable= $isAvailable, " +
                "maxGuests= $maxGuests, describeRoom = ${getDescribeRoom()})\n"
    }
}