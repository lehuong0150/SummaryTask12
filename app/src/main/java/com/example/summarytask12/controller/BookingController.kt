package com.example.summarytask12.controller

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.summarytask12.model.Booking
import com.example.summarytask12.model.users.VIPCustomer
import com.example.summarytask12.services.BookingService
import com.example.summarytask12.services.CustomerService
import com.example.summarytask12.services.RoomService
import com.example.summarytask12.utils.PaymentMethod
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.time.format.DateTimeParseException
import java.util.*

class BookingController(
    private val bookingService: BookingService,
    private val roomService: RoomService,
    private val customerService: CustomerService,
) {
    private val scanner: Scanner = Scanner(System.`in`)

    @RequiresApi(Build.VERSION_CODES.O)
    fun showMenu() {
        while (true) {
            println("\n--- BOOKING MANAGEMENT ---")
            println("1. Create booking")
            println("2. View booking by ID")
            println("3. View all bookings")
            println("4. View bookings by customer")
            println("5. Confirm booking")
            println("6. Cancel booking")
            println("7. Delete booking")
            println("0. Back")
            print("Choose: ")

            when (scanner.nextLine().trim()) {
                "1" -> handleCreateBooking()
                "2" -> handleViewBookingById()
                "3" -> handleViewAllBookings()
                "4" -> handleViewBookingsByCustomer()
                "5" -> handleConfirmBooking()
                "6" -> handleCancelBooking()
                "7" -> handleDeleteBooking()
                "0" -> return
                else -> println("Invalid choice!!!")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleCreateBooking() {
        println("------- Booking Room -------")

        // Nhập Customer ID
        print("Input Customer ID: ")
        val customerId = scanner.nextLine().trim()
        val customer = customerService.getCustomer(customerId)
        if (customer == null) {
            println("Customer not found!")
            return
        }
        customer.getDisplayInfo()

        // Nếu VIP thì thêm Special Request
        if (customer is VIPCustomer) {
            println("Membership Level: VIP")
            print("Special Request (Enter to skip): ")
            val request = scanner.nextLine().trim()
            if (request.isNotBlank()) {
                customer.addSpecialRequest(request)
            }
        } else {
            println("Membership Level: ${customer.getMembershipLevel}")
        }

        // Nhập Room ID
        print("Input Room ID: ")
        val roomId = scanner.nextLine().trim()
        val room = roomService.getRoom(roomId)?.takeIf { it.isAvailable }
        if (room == null) {
            println("Room not found or not available!")
            return
        }
        println("Room info: $room")

        // Nhập số đêm
        print("Input nights: ")
        val nights = scanner.nextLine().toIntOrNull()
        if (nights == null || nights <= 0) {
            println("Invalid number of nights!")
            return
        }

        // Nhập phương thức thanh toán
        print("Payment method (CREDIT_CARD/CASH, Enter to skip): ")
        val paymentMethod = scanner.nextLine()
            .takeIf { it.isNotBlank() }
            ?.let { input ->
                enumValues<PaymentMethod>().find { it.name.equals(input, ignoreCase = true) }
            } ?: PaymentMethod.CASH

        // Nhập ngày đặt phòng (hoặc mặc định LocalDateTime.now())
        print("Booking date (Enter to use today): ")
        val dateInput = scanner.nextLine().trim()
        val dateBooking = if (dateInput.isBlank()) {
            now()
        } else {
            try {
                dateInput
            } catch (e: DateTimeParseException) {
                println("Invalid date format! Using today instead.")
                now()
            }
        }

        // Tạo Booking
        val bookingId = "B" + (1000..9999).random()
        val booking = Booking(
            id = bookingId,
            customerId = customerId,
            roomId = roomId,
            date = dateBooking as LocalDateTime,
            nights = nights,
            paymentMethod = paymentMethod,
            totalAmount = Booking.calculateTotal(customerId, roomId, nights)
        )

        // Lưu booking
        if (bookingService.createBooking(booking)) {
            println("Booking created successfully!")
            println(booking)
        } else {
            println("Failed to create booking!")
        }
    }

    private fun handleViewBookingById() {
        print("Enter Booking ID: ")
        val id = scanner.nextLine().trim()
        val booking = bookingService.getBooking(id)
        println(booking ?: "Booking not found")
    }

    private fun handleViewAllBookings() {
        val bookings = bookingService.getAllBookings()
        if (bookings.isEmpty()) println("No bookings found")
        else bookings.forEach { println(it) }
    }

    private fun handleViewBookingsByCustomer() {
        print("Enter Customer ID: ")
        val customerId = scanner.nextLine().trim()
        val bookings = bookingService.getBookingsByCustomer(customerId)
        if (bookings.isEmpty()) println("No bookings for this customer")
        else bookings.forEach { println(it) }
    }

    private fun handleConfirmBooking() {
        print("Enter Booking ID to confirm: ")
        val id = scanner.nextLine().trim()
        if (bookingService.confirmBooking(id)) {
            println("Booking confirmed successfully.")
        } else {
            println("Booking not found.")
        }
    }

    private fun handleCancelBooking() {
        print("Enter Booking ID to cancel: ")
        val id = scanner.nextLine().trim()
        if (bookingService.cancelBooking(id)) {
            println("Booking cancelled successfully.")
        } else {
            println("Booking not found.")
        }
    }

    private fun handleDeleteBooking() {
        print("Enter Booking ID to delete: ")
        val id = scanner.nextLine().trim()
        if (bookingService.deleteBooking(id)) {
            println("Booking deleted successfully.")
        } else {
            println("Booking not found.")
        }
    }
}
