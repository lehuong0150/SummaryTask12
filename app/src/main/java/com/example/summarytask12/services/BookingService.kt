package com.example.summarytask12.services

import com.example.summarytask12.repository.*
import com.example.summarytask12.extensions.canCancel
import com.example.summarytask12.model.Booking
import com.example.summarytask12.utils.BookingStatus

class BookingService(
    private val bookingRepo: BookingRepository,
) {

    fun createBooking(booking: Booking): Boolean =
        bookingRepo.addBooking(booking)
            .also { if (it) println("Booking created: ${booking.id}") }

    fun getBooking(id: String): Booking? = bookingRepo.getBookingById(id)

    fun getBookingsByCustomer(customerId: String): List<Booking> = 
        bookingRepo.getBookingsByCustomerId(customerId)

    fun confirmBooking(id: String): Boolean = 
        bookingRepo.updateBookingStatus(id, BookingStatus.CONFIRMED)

    fun cancelBooking(id: String): Boolean =
        bookingRepo.getBookingById(id)?.takeIf { it.canCancel() }?.let { booking ->
            bookingRepo.updateBookingStatus(id, BookingStatus.CANCELLED)
                .also { updated ->
                    if (updated) {
                        HotelRepository.roomRepository.updateRoomAvailability(booking.roomId, true)
                    }
                }
        } ?: false.also { println("Can't cancel booking!!!") }

    fun deleteBooking(id: String): Boolean = bookingRepo.deleteBooking(id)

    fun getAllBookings(): List<Booking> = bookingRepo.getAllBookings()
}
