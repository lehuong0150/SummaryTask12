package com.example.summarytask12.repository

import com.example.summarytask12.model.Booking
import com.example.summarytask12.utils.BookingStatus

class BookingRepository : BaseRepository<Booking>() {

    fun addBooking(booking: Booking): Boolean =
        data.none { it.id == booking.id }
            .also { canAdd ->
                if (canAdd) data.add(booking)
            }

    fun getAllBookings(): List<Booking> = getAll()

    fun getBookingById(id: String): Booking? = findBy { it.id == id }

    fun getBookingsByCustomerId(customerId: String): List<Booking> =
        data.filter { it.customerId == customerId }

    fun updateBookingStatus(bookingId: String, status: BookingStatus): Boolean {
        val booking = getBookingById(bookingId) ?: return false
        val index = data.indexOf(booking)
        if (index == -1) return false
        data[index] = booking.copy(status = status)
        return true
    }

    fun deleteBooking(bookingId: String): Boolean =
        data.removeIf { it.id == bookingId }
}
