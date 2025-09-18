package com.example.summarytask12.model

import com.example.summarytask12.database.HotelDB
import com.example.summarytask12.utils.BookingStatus

data class Booking(
    val id: String,
    val customerId: String,
    val roomId: String,
    val date: String,
    val nights: Int,
    var status: BookingStatus = BookingStatus.PENDING,
    var paymentMethod: String?,
    var totalAmount: Double
) {
    constructor(
        id: String,
        customerId: String,
        roomId: String,
        date: String,
        nights: Int,
        status: String,
        paymentMethod: String?,
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
        paymentMethod,
        calculateTotal(customerId, roomId, nights)
    )

    override fun toString(): String {
        return "Booking(" +
                "id='$id', " +
                "customerId='$customerId', " +
                "roomId='$roomId', " +
                "date='$date', " +
                "nights=$nights, " +
                "status=$status, " +
                "paymentMethod=$paymentMethod, " +
                "totalAmount=$totalAmount" +
                ")\n"
    }


    companion object {
        fun calculateTotal(customerId: String, roomId: String, nights: Int): Double {
            val room = HotelDB.getRoom(roomId)
            val customer = HotelDB.getCustomer(customerId)

            val baseAmount = room?.calculatePrice(nights) ?: 0.0
            return customer?.applyDiscount(baseAmount) ?: baseAmount

        }
    }


}