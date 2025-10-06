package com.example.summarytask12.model.accommodation

import com.example.summarytask12.model.Discountable

abstract class Room(
    val id: String,
    val numberRoom: String,
    val type: String? = null,
    var price: Double,
    val maxGuests: Int = 2,
    var isAvailable: Boolean = true
) {
    abstract fun getDescribeRoom(): MutableList<String>
    abstract fun calculatePrice(nights: Int): Double
}