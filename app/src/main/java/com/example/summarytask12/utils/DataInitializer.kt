package com.example.summarytask12.utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.summarytask12.extensions.toLocalDateTime
import com.example.summarytask12.model.Booking
import com.example.summarytask12.model.accommodation.DeluxeRoom
import com.example.summarytask12.model.accommodation.StandardRoom
import com.example.summarytask12.model.accommodation.SuiteRoom
import com.example.summarytask12.model.payment.Invoice
import com.example.summarytask12.model.payment.InvoiceItem
import com.example.summarytask12.model.payment.Payment
import com.example.summarytask12.model.users.Customer
import com.example.summarytask12.model.users.VIPCustomer
import com.example.summarytask12.repository.HotelRepository

object DataInitializer {

    @RequiresApi(Build.VERSION_CODES.O)
    fun initialize() {
        initCustomers()
        initRooms()
        initBookings()
        initPayments()
        initInvoices()
    }

    private fun initCustomers() = with(HotelRepository.customerRepository) {
        listOf(
            Customer(
                "C001", "Nguyễn Văn Hải", "vana@email.com", "0901234567",
                "BASIC"
            ),
            VIPCustomer(
                "C002", "Trần Thị Nga", "thib@vip.com", "0907654321",
                mutableListOf(
                    "Phòng tầng cao view biển", "Late checkout 3PM", "Bữa sáng tại phòng",
                    "Xe đưa đón sân bay"
                )
            ),
            Customer(
                "C003", "Lê Ngọc Linh", "linh@email.com", "0905634567",
                "BASIC"
            ),
            VIPCustomer(
                "C004", "Phan Đức Thanh Duy", "duy@vip.com",
                "0907654321",
                mutableListOf(
                    "Late checkout 3PM", "Bữa sáng tại phòng", "Xe đưa đón sân bay",
                    "Welcome drink champagne"
                )
            ),
            Customer(
                "C005", "Trần Đình Hưng", "hung@email.com",
                "0945632507", "PREMIUM"
            ),
            Customer(
                "C006", "Võ Tiến Khoa", "khoa@email.com",
                "0925634169", "PREMIUM"
            )
        ).forEach { addCustomer(it) }
    }


    private fun initRooms() = with(HotelRepository.roomRepository) {
        listOf(
            StandardRoom("R001", "101", 800000.0, true),
            StandardRoom("R002", "102", 850000.0, true),
            DeluxeRoom(
                "R003", "103", 1200000.0, true,
                hasOceanView = true
            ),
            DeluxeRoom(
                "R004", "104", 1100000.0, false,
                hasOceanView = false
            ),
            SuiteRoom("R005", "105", 2000000.0, false),
            StandardRoom("R006", "106", 850000.0, false)
        ).forEach { addRoom(it) }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun initBookings() = with(HotelRepository.bookingRepository) {
        listOf(
            Booking(
                "B001", "C001", "R004", "2025-09-17T00:00:00".toLocalDateTime(),
                2, BookingStatus.CONFIRMED, PaymentMethod.CASH, 2200000.0
            ),
            Booking(
                "B002", "C006", "R005", "2025-09-18T00:00:00".toLocalDateTime(),
                3, BookingStatus.CONFIRMED, PaymentMethod.CASH, 6000000.0
            ),
            Booking(
                "B003", "C002", "R006", "2025-09-16T00:00:00".toLocalDateTime(),
                4, BookingStatus.CONFIRMED, PaymentMethod.CREDIT_CARD, 3400000.0
            )
        ).forEach { addBooking(it) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initPayments() = with(HotelRepository.paymentRepository) {
        listOf(
            Payment(
                "P001", 1600000.0, "2025-09-17T10:30:00".toLocalDateTime(),
                PaymentMethod.CASH, "B001"
            ),
            Payment(
                "P002", 6000000.0, "2025-09-18T15:45:00".toLocalDateTime(),
                PaymentMethod.CREDIT_CARD, "B002"
            ),
            Payment(
                "P003", 3400000.0, "2025-09-19T09:00:00".toLocalDateTime(),
                PaymentMethod.CASH, "B003"
            )
        ).forEach { addPayment(it) }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun initInvoices() = with(HotelRepository.paymentRepository) {
        listOf(
            Invoice(
                "I001", "B001", "C001",
                listOf(
                    InvoiceItem("Deluxe Room (2 nights)", 1600000.0),
                    InvoiceItem("Breakfast service", 200000.0)
                ),
                subtotal = 1800000.0, tax = 180000.0, discount = 0.0, total = 1980000.0,
                issueDate = "2025-09-17T11:00:00".toLocalDateTime(),
                dueDate = "2025-09-20T11:00:00".toLocalDateTime()
            ),
            Invoice(
                "I002", "B002", "C006",
                listOf(InvoiceItem("Suite Room (3 nights)", 6000000.0)),
                subtotal = 6000000.0, tax = 600000.0, discount = 500000.0, total = 6100000.0,
                issueDate = "2025-09-18T15:30:00".toLocalDateTime(),
                dueDate = "2025-09-21T15:30:00".toLocalDateTime()
            ),
            Invoice(
                "I003", "B003", "C002",
                listOf(InvoiceItem("Standard Room (4 nights)", 3400000.0)),
                subtotal = 3400000.0, tax = 340000.0, discount = 0.0, total = 3740000.0,
                issueDate = "2025-09-19T09:00:00".toLocalDateTime(),
                dueDate = "2025-09-22T09:00:00".toLocalDateTime()
            )
        ).forEach { addInvoice(it) }
    }
}
