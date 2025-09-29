package com.example.summarytask12.extensions

import android.os.Build
import com.example.summarytask12.model.Booking
import com.example.summarytask12.utils.BookingStatus
import com.example.summarytask12.utils.PaymentMethod
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun Booking.canCancel(): Boolean {
    return ((status in listOf(BookingStatus.PENDING, BookingStatus.CONFIRMED))
            && (paymentMethod == PaymentMethod.CASH))

}

fun Booking.checkInDate(): LocalDateTime =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDateTime.parse(date.toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    } else {
        TODO("VERSION.SDK_INT < O")
    }
