package com.example.summarytask12.model.accommodation

class DeluxeRoom(
    id: String,
    numberRoom: String,
    price: Double,
    isAvailable: Boolean = true,
    var hasOceanView: Boolean = false
) : Room(id, "116", "DELUXE", price, maxGuests = 4, isAvailable) {

    override fun getDescribeRoom(): MutableList<String> = mutableListOf<String>().apply {
        addAll(mutableListOf("WiFi", "TV", "Điều hòa", "Minibar", "Ban công"))
        if (hasOceanView) {
            add("Ocean View")
        }
    }

    override fun calculatePrice(nights: Int): Double {
        return if (hasOceanView) price * 1.2 * nights else price * nights * 1.1
    }

    override fun toString(): String {
        return "DeluxeRoom(id=$id, numberRoom= $numberRoom, type=$type, price=$price, isAvailable=$isAvailable, " +
                "maxGuests=$maxGuests, hasOceanView=$hasOceanView, describeRoom = ${getDescribeRoom()})\n"
    }
}