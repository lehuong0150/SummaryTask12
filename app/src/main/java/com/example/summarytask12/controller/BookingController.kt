package com.example.summarytask12.controller

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.summarytask12.model.Booking
import com.example.summarytask12.model.users.VIPCustomer
import com.example.summarytask12.services.BookingService
import com.example.summarytask12.services.CustomerService
import com.example.summarytask12.services.RoomService
import com.example.summarytask12.utils.*
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
            output.printHeader(Message.BOOKING_HEADER)
            output.printMessage("1. ${Message.BOOKING_CREATE}")
            output.printMessage("2. View booking by ID")
            output.printMessage("3. View all bookings")
            output.printMessage("4. View bookings by customer")
            output.printMessage("5. Confirm booking")
            output.printMessage("6. Cancel booking")
            output.printMessage("7. Delete booking")
            output.printMessage("0. Back")

            when (input.prompt("Choose: ")) {
                "1" -> handleCreateBooking()
                "2" -> handleViewBookingById()
                "3" -> handleViewAllBookings()
                "4" -> handleViewBookingsByCustomer()
                "5" -> handleConfirmBooking()
                "6" -> handleCancelBooking()
                "7" -> handleDeleteBooking()
                "0" -> return
                else -> output.printError(Message.INVALID_CHOICE)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleCreateBooking() {
        output.printSubHeader(Message.BOOKING_CREATE)

        val customerId = input.prompt(Message.BOOKING_ENTER_CUSTOMER_ID)
        val customer = customerService.getCustomer(customerId)
        if (customer == null) {
            output.printError(Message.BOOKING_CUSTOMER_NOT_FOUND)
            return
        }

        if (customer is VIPCustomer) {
            output.printMessage("Membership Level: VIP")
            val request = input.prompt(Message.CUSTOMER_SPECIAL_REQUEST)
            if (request.isNotBlank()) customer.addSpecialRequest(request)
        } else {
            output.printMessage("Membership Level: ${customer.getMembershipLevel}")
        }

        val roomId = input.prompt(Message.BOOKING_ENTER_ROOM_ID)
        val room = roomService.getRoom(roomId)?.takeIf { it.isAvailable }
        if (room == null) {
            output.printError(Message.BOOKING_ROOM_NOT_FOUND)
            return
        }
        output.printMessage("Room info: $room")

        val nights = input.promptInt(Message.BOOKING_ENTER_NIGHTS) ?: run {
            output.printError(Message.INPUT_ERROR)
            return
        }

        val paymentMethodInput = input.prompt(Message.BOOKING_ENTER_PAYMENT_METHOD)
        val paymentMethod = enumValues<PaymentMethod>().find {
            it.name.equals(paymentMethodInput, ignoreCase = true)
        } ?: PaymentMethod.CASH

        val dateInput = input.prompt(Message.BOOKING_ENTER_DATE)
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
            room.isAvailable = false
            roomService.updateRoomAvailability(roomId,room.isAvailable)

            output.printSuccess(Message.BOOKING_CREATED)
            output.printMessage(booking.toString())
        } else {
            output.printError(Message.BOOKING_FAILED)
        }
    }


    private fun handleViewBookingById() {
        val id = input.prompt(Message.BOOKING_ENTER_ID)
        val booking = bookingService.getBooking(id)
        if (booking != null)
            output.printMessage(booking.toString())
        else
            output.printError(Message.BOOKING_NOT_FOUND)
    }

    private fun handleViewAllBookings() {
        val bookings = bookingService.getAllBookings()
        if (bookings.isEmpty())
            output.printMessage(Message.BOOKING_NO_DATA)
        else
            output.printList("ALL BOOKINGS", bookings) { it.toString() }
    }

    private fun handleViewBookingsByCustomer() {
        val customerId = input.prompt(Message.BOOKING_ENTER_CUSTOMER_ID)
        val bookings = bookingService.getBookingsByCustomer(customerId)
        if (bookings.isEmpty())
            output.printMessage("No bookings for this customer.")
        else
            output.printList("BOOKINGS BY CUSTOMER", bookings) { it.toString() }
    }

    private fun handleConfirmBooking() {
        output.printHeader("CONFIRM BOOKING")
        val id = input.prompt(Message.BOOKING_ENTER_ID)
        if (bookingService.confirmBooking(id))
            output.printSuccess(Message.BOOKING_CONFIRM)
        else
            output.printError(Message.BOOKING_NOT_FOUND)
    }

    private fun handleCancelBooking() {
        output.printHeader("CANCEL BOOKING")
        val id = input.prompt(Message.BOOKING_ENTER_ID)
        if (bookingService.cancelBooking(id))
            output.printSuccess(Message.BOOKING_CANCEL)
        else
            output.printError(Message.BOOKING_NOT_FOUND)
    }

    private fun handleDeleteBooking() {
        output.printHeader("DELETE BOOKING")
        val id = input.prompt(Message.BOOKING_ENTER_ID)
        if (bookingService.deleteBooking(id))
            output.printSuccess(Message.BOOKING_DELETE)
        else
            output.printError(Message.BOOKING_NOT_FOUND)
    }
}
