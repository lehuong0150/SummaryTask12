package com.example.summarytask12.model.payment

import java.time.LocalDateTime

abstract class Payment(
    val paymentId: String,
    val amount: Double,
    val paymentDate: LocalDateTime,
    val bookingId: String
) {
    abstract fun processPayment(): Boolean
    abstract fun getPaymentDetails(): String
}