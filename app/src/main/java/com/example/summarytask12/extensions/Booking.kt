package com.example.summarytask12.extensions

import com.example.summarytask12.model.Booking
import com.example.summarytask12.utils.BookingStatus

fun Booking.canCancel(): Boolean = status in listOf(BookingStatus.PENDING, BookingStatus.CONFIRMED)