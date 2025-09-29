package com.example.summarytask12.controller

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.summarytask12.extensions.summary
import com.example.summarytask12.services.CustomerService
import com.example.summarytask12.services.PaymentService
import com.example.summarytask12.services.RoomService

@RequiresApi(Build.VERSION_CODES.O)
class ReportController(
    private val roomService: RoomService,
    private val customerService: CustomerService,
    private val paymentService: PaymentService
) {

    fun showMenu() {
        while (true) {
            println("\n" + "=".repeat(50))
            println("REPORT MENU")
            println("1. Revenue Report")
            println("2. Room Status Report")
            println("3. Customer Report")
            println("0. Back to Main Menu")
            println("=".repeat(50))
            print("Choose: ")

            when (readLine()) {
                "1" -> showRevenueReport()
                "2" -> showRoomStatusReport()
                "3" -> showCustomerReport()
                "0" -> return
                else -> println("Invalid choice!")
            }
        }
    }

    private fun <T> printReport(title: String, items: List<T>, mapper: (T) -> String) {
        println("\n$title")
        println("-".repeat(50))
        items.map(mapper).forEach(::println)
        println("-".repeat(50))
    }

    private fun showRevenueReport() {
        val payments = paymentService.getAllPayments()
        val totalRevenue = payments.sumOf { it.amount }

        println("\nTOTAL REVENUE: $totalRevenue")
        printReport("REVENUE REPORT", payments) { it.summary() }
    }

    private fun showRoomStatusReport() {
        val rooms = roomService.getAllRooms()
        printReport("ROOM STATUS REPORT", rooms) { room ->
            room.let {
                "Room ${it.numberRoom} | Type: ${it.type} " +
                        "| Status: ${if (it.isAvailable) "Available" else "Occupied"}"
            }
        }
    }

    private fun showCustomerReport() {
        val customers = customerService.getAllCustomers()
        printReport("CUSTOMER REPORT", customers) { c -> "${c.name} | ${c.email} | ${c.phone}" }
    }
}
