package com.example.summarytask12.model

interface Discountable {
    fun discountRate(): Double
    fun applyDiscount(price: Double): Double{
        return price * (1 - discountRate()*price)
    }
}