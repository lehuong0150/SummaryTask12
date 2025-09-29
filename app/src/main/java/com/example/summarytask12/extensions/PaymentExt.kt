package com.example.summarytask12.extensions

import com.example.summarytask12.model.payment.Payment

fun Payment.summary(): String {
    return "Booking $bookingId | Amount: $amount | Date: $paymentDate"
}