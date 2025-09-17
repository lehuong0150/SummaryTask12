package com.example.summarytask12

import com.example.summarytask12.database.HotelDB
import com.example.summarytask12.model.Booking
import com.example.summarytask12.model.accommodation.Room
import com.example.summarytask12.model.users.Customer

fun main(){
    val room: Room? = HotelDB.getRoom("R005")
    println(room)
    val booking: Booking? = HotelDB.getBooking("B001")
    println(booking)
    val customer: Customer? = HotelDB.getCustomer("C002")
    println(customer)

    println("-----------------------------------------------------------------------------------------")
    println(HotelDB.getCustomerList())

    println("-----------------------------------------------------------------------------------------")
    println(HotelDB.getRoomList())

    println("-----------------------------------------------------------------------------------------")
    println(HotelDB.getBookingList())
}