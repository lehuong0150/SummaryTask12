package com.example.summarytask12.model

import com.example.summarytask12.repository.HotelRepository
import com.example.summarytask12.utils.BookingStatus
import com.example.summarytask12.utils.PaymentMethod
import java.time.LocalDateTime

data class Booking(
    val id: String,
    val customerId: String,
    val roomId: String,
    val date: LocalDateTime,
    val nights: Int,
    var status: BookingStatus = BookingStatus.PENDING,
    var paymentMethod: PaymentMethod,
    var totalAmount: Double = 0.0
) {

    companion object {
        fun create(
            id: String,
            customerId: String,
            roomId: String,
            date: LocalDateTime,
            nights: Int,
            status: String,
            paymentMethod: String
        ): Booking {
            val statusEnum = when (status.uppercase()) {
                "PENDING" -> BookingStatus.PENDING
                "CONFIRMED" -> BookingStatus.CONFIRMED
                "CANCELLED" -> BookingStatus.CANCELLED
                else -> BookingStatus.PENDING
            }

            val paymentEnum = when (paymentMethod.uppercase()) {
                "CASH" -> PaymentMethod.CASH
                "CARD", "CREDIT_CARD" -> PaymentMethod.CREDIT_CARD
                else -> PaymentMethod.CASH
            }

            val total = calculateTotal(customerId, roomId, nights)

            return Booking(
                id = id,
                customerId = customerId,
                roomId = roomId,
                date = date,
                nights = nights,
                status = statusEnum,
                paymentMethod = paymentEnum,
                totalAmount = total
            )
        }

        fun calculateTotal(customerId: String, roomId: String, nights: Int): Double {
            val room = HotelRepository.roomRepository.getRoomById(roomId)
            val customer = HotelRepository.customerRepository.getCustomerById(customerId)
            val roomPrice = (room as? Discountable)?.applyDiscount(room.price * nights)
                ?: room?.price?.times(nights) ?: 0.0
            return (customer as? Discountable)?.applyDiscount(roomPrice) ?: roomPrice
        }
    }
}
