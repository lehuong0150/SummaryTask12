package com.example.summarytask12.repository


object HotelRepository {
    val customerRepository = CustomerRepository()
    val roomRepository = RoomRepository()
    val bookingRepository = BookingRepository()
    val paymentRepository = PaymentRepository()
}