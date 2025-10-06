package com.example.summarytask12.model.payment

import android.os.Build
import com.example.summarytask12.utils.PaymentMethod
import java.time.LocalDateTime

class Payment(
    val paymentId: String,
    val totalAmount: Double,
    val paymentDate: LocalDateTime,
    val style: PaymentMethod,
    val bookingId: String
) {

    fun getPaymentDetails(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            "Payment:  $${totalAmount} on ${paymentDate.toLocalDate()}"
        } else {
            TODO("VERSION.SDK_INT < O")
        }
    }
}