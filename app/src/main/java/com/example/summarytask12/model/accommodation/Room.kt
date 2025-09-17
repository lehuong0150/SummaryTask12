package com.example.summarytask12.model.accommodation

abstract class Room (
    open val id: String,
    open val type: String?=null,
    open val price : Double,
    open val maxGuests: Int = 2,
    open val isAvailable: Boolean = true
){
    abstract fun getDescribeRoom(): MutableList<String>
    abstract fun calculatePrice(nights:Int): Double
}