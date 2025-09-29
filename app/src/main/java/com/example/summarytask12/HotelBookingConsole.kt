package com.example.summarytask12

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.summarytask12.controller.*
import com.example.summarytask12.controllers.CustomerController
import com.example.summarytask12.repository.*
import com.example.summarytask12.services.*
import java.util.*

class HotelBookingConsole {
    private val scanner = Scanner(System.`in`)
    private val roomController = RoomController(RoomService(HotelRepository.roomRepository))
    private val bookingController = BookingController(
        BookingService(HotelRepository.bookingRepository),
        RoomService(HotelRepository.roomRepository),
        CustomerService(HotelRepository.customerRepository)
    )
    private val customerController =
        CustomerController(CustomerService(HotelRepository.customerRepository))
    private val paymentController =
        PaymentController(PaymentService(HotelRepository.paymentRepository))

    @RequiresApi(Build.VERSION_CODES.O)
    private val reportController = ReportController(
        RoomService(HotelRepository.roomRepository),
        CustomerService(HotelRepository.customerRepository),
        PaymentService(HotelRepository.paymentRepository)
    )

    @RequiresApi(Build.VERSION_CODES.O)
    fun showMainMenu() {
        while (true) {
            println("\n" + "=".repeat(50))
            println("HOTEL MANAGEMENT SYSTEM")
            println("1. Room Management")
            println("2. Booking Management")
            println("3. Customer Management")
            println("4. Payment & Invoice Management")
            println("5. Report Management")
            println("0. Exit")
            println("=".repeat(50))
            print("Choose: ")

            when (scanner.nextLine()) {
                "1" -> roomController.showMenu()
                "2" -> bookingController.showMenu()
                "3" -> customerController.showMenu()
                "4" -> paymentController.showMenu()
                "5" -> reportController.showMenu()
                "0" -> {
                    println("Exit!!!")
                    return
                }

                else -> println("Invalid choice!")
            }
        }
    }
}
