package com.example.summarytask12.model.payment

import android.os.Build
import com.example.summarytask12.utils.PaymentStyle
import java.time.LocalDateTime

class Payment(
    val paymentId: String,
    val totalAmount: Double,
    val paymentDate: LocalDateTime,
    val style: PaymentStyle,
    val bookingId: String
) {
    fun processPayment(): Boolean {
        println("Processing cash payment of $${totalAmount}")
        return true
    }

    fun getPaymentDetails(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            "Payment:  $${totalAmount} on ${paymentDate.toLocalDate()}"
        } else {
            TODO("VERSION.SDK_INT < O")
        }
    }
}