package com.example.summarytask12.controller

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.summarytask12.extensions.summary
import com.example.summarytask12.services.CustomerService
import com.example.summarytask12.services.PaymentService
import com.example.summarytask12.services.RoomService
import com.example.summarytask12.utils.InputHandler
import com.example.summarytask12.utils.OutputHandler

@RequiresApi(Build.VERSION_CODES.O)
class ReportController(
    private val roomService: RoomService,
    private val customerService: CustomerService,
    private val paymentService: PaymentService,
    private val input: InputHandler = InputHandler(),
    private val output: OutputHandler = OutputHandler()
) {

    fun showMenu() {
        while (true) {
            output.printHeader("REPORT MANAGEMENT")
            output.printMessage("1. Revenue Report")
            output.printMessage("2. Room Status Report")
            output.printMessage("3. Customer Report")
            output.printMessage("0. Back")

            when (input.prompt("Choose")) {
                "1" -> showRevenueReport()
                "2" -> showRoomStatusReport()
                "3" -> showCustomerReport()
                "0" -> return
                else -> output.printError("Invalid choice!")
            }
        }
    }

    private fun <T> printReport(title: String, items: List<T>, mapper: (T) -> String) {
        output.printList(title, items, mapper)
    }

    private fun showRevenueReport() {
        output.printSubHeader("Revenue Report")

        val payments = paymentService.getAllPayments()
        val totalRevenue = payments.sumOf { it.totalAmount }

        output.printMessage("Total Revenue: $totalRevenue")
        if (payments.isEmpty()) {
            output.printMessage("No payments found.")
        } else {
            printReport("REVENUE REPORT", payments) { it.summary() }
        }
    }

    private fun showRoomStatusReport() {
        output.printSubHeader("Room Status Report")

        val rooms = roomService.getAllRooms()
        if (rooms.isEmpty()) {
            output.printMessage("No rooms found.")
        } else {
            printReport("ROOM STATUS REPORT", rooms) { room ->
                "Room ${room.numberRoom} | Type: ${room.type} | Status: ${
                    if (room.isAvailable)
                        "Available" else "Occupied"
                }"
            }
        }
    }

    private fun showCustomerReport() {
        output.printSubHeader("Customer Report")

        val customers = customerService.getAllCustomers()
        if (customers.isEmpty()) {
            output.printMessage("No customers found.")
        } else {
            printReport("CUSTOMER REPORT", customers) {
                "${it.name} | ${it.email} | ${it.phone}"
            }
        }
    }
}
