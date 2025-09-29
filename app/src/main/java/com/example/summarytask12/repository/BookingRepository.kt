package com.example.summarytask12.repository

import com.example.summarytask12.model.Booking
import com.example.summarytask12.utils.BookingStatus

class BookingRepository {
    private val bookings = mutableListOf<Booking>()

    fun addBooking(booking: Booking): Boolean =
        bookings.none { it.id == booking.id }
            .also { canAdd -> if (canAdd) bookings.add(booking) }

    fun getAllBookings(): List<Booking> = bookings.toList()

    fun getBookingById(id: String): Booking? = bookings.find { it.id == id }

    fun getBookingsByCustomerId(customerId: String): List<Booking> =
        bookings.filter { it.customerId == customerId }

    fun updateBookingStatus(bookingId: String, status: BookingStatus): Boolean =
        getBookingById(bookingId)?.let { booking ->
            bookings.indexOf(booking)
                .takeIf { it != -1 }
                ?.let { bookings[it] = booking.copy(status = status); true } ?: false
        } ?: false

    fun deleteBooking(bookingId: String): Boolean =
        bookings.removeIf { it.id == bookingId }
}
