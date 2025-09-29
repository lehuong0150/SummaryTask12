package com.example.summarytask12.model.payment

import android.os.Build
import java.time.LocalDateTime

class CashPayment(
    val id: String,
    val totalAmount: Double,
    val date: LocalDateTime,
    val relatedBookingId: String,
    val receivedBy: String
) : Payment(id, totalAmount, date, relatedBookingId) {
    override fun processPayment(): Boolean {
        // xu ly thanh toan qua tien mat
        println("Processing cash payment of $${totalAmount}")
        return true
    }

    override fun getPaymentDetails(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            "Cash Payment: $${totalAmount} received by $receivedBy on ${date.toLocalDate()}"
        } else {
            TODO("VERSION.SDK_INT < O")
        }
    }
}
