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
    var totalAmount: Double
) {
    constructor(
        id: String,
        customerId: String,
        roomId: String,
        date: LocalDateTime,
        nights: Int,
        status: String,
        paymentMethod: String,
    ) : this(
        id,
        customerId,
        roomId,
        date,
        nights,
        when (status.uppercase()) {
            "PENDING" -> BookingStatus.PENDING
            "CONFIRMED" -> BookingStatus.CONFIRMED
            "CANCELLED" -> BookingStatus.CANCELLED
            else -> BookingStatus.PENDING
        },
        when (paymentMethod.uppercase()) {
            "CASH" -> PaymentMethod.CASH
            "CARD" -> PaymentMethod.CREDIT_CARD
            else -> PaymentMethod.CASH
        },
        calculateTotal(customerId, roomId, nights)
    )

    companion object {
        fun calculateTotal(customerId: String, roomId: String, nights: Int): Double {
            val room = HotelRepository.roomRepository.getRoomById(roomId)
            val customer = HotelRepository.customerRepository.getCustomerById(customerId)
            val baseAmount = room?.calculatePrice(nights) ?: 0.0
            return customer?.applyDiscount(baseAmount) ?: baseAmount
        }
    }
}