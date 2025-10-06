package com.example.summarytask12.model.accommodation

import com.example.summarytask12.model.Discountable

class SuiteRoom(
    id: String,
    numberRoom: String,
    price: Double,
    isAvailable: Boolean = true
) : Room(id, "115", "SUITE", price, maxGuests = 6, isAvailable), Discountable {

    override fun getDescribeRoom(): MutableList<String> {
        return mutableListOf(
            "WiFi",
            "TV",
            "Điều hòa",
            "Minibar",
            "Ban công",
            "Phòng khách",
            "Nhà bếp"
        )
    }

    override fun calculatePrice(nights: Int): Double {
        val basePrice = price * nights * 1.2
        return applyDiscount(basePrice)
    }

    override fun discountRate(): Double {
        return 0.15
    }

    override fun toString(): String {
        return "SuiteRoom(id= $id,numberRoom= $numberRoom, type= $type, price= $price, isAvailable= $isAvailable, " +
                "maxGuests= $maxGuests, describeRoom = ${getDescribeRoom()})\n"
    }

}