package com.example.summarytask12.controller

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.summarytask12.model.Booking
import com.example.summarytask12.model.users.VIPCustomer
import com.example.summarytask12.services.BookingService
import com.example.summarytask12.services.CustomerService
import com.example.summarytask12.services.RoomService
import com.example.summarytask12.utils.InputHandler
import com.example.summarytask12.utils.OutputHandler
import com.example.summarytask12.utils.PaymentMethod
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.time.format.DateTimeParseException

class BookingController(
    private val bookingService: BookingService,
    private val roomService: RoomService,
    private val customerService: CustomerService,
    private val input: InputHandler = InputHandler(),
    private val output: OutputHandler = OutputHandler()
) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun showMenu() {
        while (true) {
            output.printHeader("BOOKING MANAGEMENT")
            output.printMessage("1. Create booking")
            output.printMessage("2. View booking by ID")
            output.printMessage("3. View all bookings")
            output.printMessage("4. View bookings by customer")
            output.printMessage("5. Confirm booking")
            output.printMessage("6. Cancel booking")
            output.printMessage("7. Delete booking")
            output.printMessage("0. Back")

            when (input.prompt("Choose")) {
                "1" -> handleCreateBooking()
                "2" -> handleViewBookingById()
                "3" -> handleViewAllBookings()
                "4" -> handleViewBookingsByCustomer()
                "5" -> handleConfirmBooking()
                "6" -> handleCancelBooking()
                "7" -> handleDeleteBooking()
                "0" -> return
                else -> output.printError("Invalid choice!")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleCreateBooking() {
        output.printSubHeader("Create Booking")

        val customerId = input.prompt("Enter Customer ID")
        val customer = customerService.getCustomer(customerId)
        if (customer == null) {
            output.printError("Customer not found!")
            return
        }
        output.printMessage("Customer Info: ${customer.getDisplayInfo()}")

        if (customer is VIPCustomer) {
            output.printMessage("Membership Level: VIP")
            val request = input.prompt("Special Request (Enter to skip)")
            if (request.isNotBlank()) {
                customer.addSpecialRequest(request)
            }
        } else {
            output.printMessage("Membership Level: ${customer.getMembershipLevel}")
        }

        val roomId = input.prompt("Enter Room ID")
        val room = roomService.getRoom(roomId)?.takeIf { it.isAvailable }
        if (room == null) {
            output.printError("Room not found or not available!")
            return
        }
        output.printMessage("Room info: $room")

        val nights = input.promptInt("Enter number of nights") ?: run {
            output.printError("Invalid number of nights!")
            return
        }

        val paymentMethodInput = input.prompt("Payment method (CREDIT_CARD/CASH, Enter to skip)")
        val paymentMethod = enumValues<PaymentMethod>().find {
            it.name.equals(paymentMethodInput, ignoreCase = true)
        } ?: PaymentMethod.CASH

        val dateInput = input.prompt("Booking date (Enter to use today)")
        val bookingDate = if (dateInput.isBlank()) now() else {
            try {
                LocalDateTime.parse(dateInput)
            } catch (e: DateTimeParseException) {
                output.printError("Invalid date format! Using today instead.")
                now()
            }
        }

        val bookingId = "B" + (1000..9999).random()
        val booking = Booking(
            id = bookingId,
            customerId = customerId,
            roomId = roomId,
            date = bookingDate,
            nights = nights,
            paymentMethod = paymentMethod,
            totalAmount = Booking.calculateTotal(customerId, roomId, nights)
        )

        if (bookingService.createBooking(booking)) {
            output.printSuccess("Booking created successfully!")
            output.printMessage(booking.toString())
        } else {
            output.printError("Failed to create booking!")
        }
    }

    private fun handleViewBookingById() {
        val id = input.prompt("Enter Booking ID")
        val booking = bookingService.getBooking(id)
        if (booking != null)
            output.printMessage(booking.toString())
        else
            output.printError("Booking not found!")
    }

    private fun handleViewAllBookings() {
        val bookings = bookingService.getAllBookings()
        if (bookings.isEmpty())
            output.printMessage("No bookings found.")
        else
            output.printList("ALL BOOKINGS", bookings) { it.toString() }
    }

    private fun handleViewBookingsByCustomer() {
        val customerId = input.prompt("Enter Customer ID")
        val bookings = bookingService.getBookingsByCustomer(customerId)
        if (bookings.isEmpty())
            output.printMessage("No bookings for this customer.")
        else
            output.printList("BOOKINGS BY CUSTOMER", bookings) { it.toString() }
    }

    private fun handleConfirmBooking() {
        val id = input.prompt("Enter Booking ID to confirm")
        if (bookingService.confirmBooking(id))
            output.printSuccess("Booking confirmed successfully.")
        else
            output.printError("Booking not found.")
    }

    private fun handleCancelBooking() {
        val id = input.prompt("Enter Booking ID to cancel")
        if (bookingService.cancelBooking(id))
            output.printSuccess("Booking cancelled successfully.")
        else
            output.printError("Booking not found.")
    }

    private fun handleDeleteBooking() {
        val id = input.prompt("Enter Booking ID to delete")
        if (bookingService.deleteBooking(id))
            output.printSuccess("Booking deleted successfully.")
        else
            output.printError("Booking not found.")
    }
}
