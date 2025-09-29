package com.example.summarytask12.model.payment

import android.os.Build
import java.time.LocalDateTime

class CreditCardPayment(
    val id: String,
    val totalAmount: Double,
    val date: LocalDateTime,
    val relatedBookingId: String,
    val cardNumber: String,
    val cardHolderName: String,
) : Payment(id, totalAmount, date, relatedBookingId){
    override fun processPayment(): Boolean {
        println("Processing credit card payment of $${totalAmount}")
        val lastFourDigits = cardNumber.takeLast(4)
        println("Charging card ending in $lastFourDigits...")
        return true
    }

    override fun getPaymentDetails(): String {
        val maskedCard = "**** **** **** ${cardNumber.takeLast(4)}"
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            "Credit Card Payment: $${totalAmount} charged to $maskedCard ($cardHolderName) on ${date.toLocalDate()}"
        } else {
            TODO("VERSION.SDK_INT < O")
        }
    }

}