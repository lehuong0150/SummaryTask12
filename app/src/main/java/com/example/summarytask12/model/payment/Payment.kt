package com.example.summarytask12.model.payment

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.summarytask12.utils.PaymentMethod
import java.time.LocalDateTime

class Payment(
    val paymentId: String,
    val totalAmount: Double,
    val paymentDate: LocalDateTime,
    val style: PaymentMethod,
    val bookingId: String
) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun getPaymentDetails(): String {
        return "Payment:  $${totalAmount} on ${paymentDate.toLocalDate()}"
    }
}