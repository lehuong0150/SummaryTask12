package com.example.summarytask12.controller

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.summarytask12.model.payment.CashPayment
import com.example.summarytask12.model.payment.CreditCardPayment
import com.example.summarytask12.model.payment.Invoice
import com.example.summarytask12.model.payment.InvoiceItem
import com.example.summarytask12.model.payment.Payment
import com.example.summarytask12.repository.HotelRepository
import com.example.summarytask12.services.PaymentService
import com.example.summarytask12.utils.InvoiceStatus
import com.example.summarytask12.utils.PaymentMethod
import java.time.LocalDateTime.now
import java.util.*

class PaymentController(
    private val paymentService: PaymentService,
) {
    private val scanner: Scanner = Scanner(System.`in`)

    @RequiresApi(Build.VERSION_CODES.O)
    fun showMenu() {
        while (true) {
            println("\n--- PAYMENT MANAGEMENT ---")
            println("1. Process Payment")
            println("2. Issue Invoice")
            println("3. View All Payments")
            println("4. View All Invoices")
            println("5. Find Payment by ID")
            println("6. Find Invoice by ID")
            println("0. Back")
            print("Choose: ")

            when (scanner.nextLine()) {
                "1" -> handleProcessPayment()
                "2" -> handleIssueInvoice()
                "3" -> handleViewAllPayments()
                "4" -> handleViewAllInvoices()
                "5" -> handleFindPaymentById()
                "6" -> handleFindInvoiceById()
                "0" -> return
                else -> println("Invalid choice!")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleProcessPayment() {
        println("------- Process Payment -------")

        print("Payment ID: ")
        val paymentId = scanner.nextLine().trim()
        if (paymentId.isBlank()) {
            println("Payment ID cannot be empty!")
            return
        }

        print("Booking ID: ")
        val bookingId = scanner.nextLine().trim()
        if (bookingId.isBlank()) {
            println("Booking ID cannot be empty!")
            return
        }

        val booking = HotelRepository.bookingRepository.getBookingById(bookingId)
        if (booking == null) {
            println("Booking not found!")
            return
        }

        print("Amount (suggested: ${booking.totalAmount}): ")
        val totalAmount = scanner.nextLine().toDoubleOrNull() ?: run {
            println("Invalid amount!")
            return
        }
        if (totalAmount <= 0) {
            println("Amount must be positive!")
            return
        }

        print("Payment method (CASH/CREDIT_CARD): ")
        val paymentMethod = scanner.nextLine()
            .takeIf { it.isNotBlank() }
            ?.let { input ->
                enumValues<PaymentMethod>().find { it.name.equals(input, ignoreCase = true) }
            } ?: run {
            println("Invalid payment method! Using CASH as default.")
            PaymentMethod.CASH
        }

        val payment: Payment = when (paymentMethod) {
            PaymentMethod.CASH -> {
                print("Received By: ")
                val receivedBy = scanner.nextLine().trim()
                if (receivedBy.isBlank()) {
                    println("Received by name cannot be empty!")
                    return
                }
                CashPayment(
                    id = paymentId,
                    totalAmount = totalAmount,
                    date = now(),
                    relatedBookingId = bookingId,
                    receivedBy = receivedBy
                )
            }
            PaymentMethod.CREDIT_CARD -> {
                print("Card Number (16 digits): ")
                val cardNumber = scanner.nextLine().trim()
                if (cardNumber.length != 16 || !cardNumber.matches(Regex("\\d{16}"))) {
                    println("Invalid card number!")
                    return
                }
                print("Card Holder Name: ")
                val cardHolderName = scanner.nextLine().trim()
                if (cardHolderName.isBlank()) {
                    println("Card holder name cannot be empty!")
                    return
                }
                CreditCardPayment(
                    id = paymentId,
                    totalAmount = totalAmount,
                    date = now(),
                    relatedBookingId = bookingId,
                    cardNumber = cardNumber,
                    cardHolderName = cardHolderName
                )
            }
        }

        if (paymentService.processPayment(payment)) {
            println("Payment processed successfully: ${payment.getPaymentDetails()}")
        } else {
            println("Failed to process payment!")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleIssueInvoice() {
        println("------- Issue Invoice -------")

        print("Invoice ID: ")
        val invoiceId = scanner.nextLine().trim()

        print("Booking ID: ")
        val bookingId = scanner.nextLine().trim()

        print("Customer ID: ")
        val customerId = scanner.nextLine().trim()

        val items = mutableListOf<InvoiceItem>()
        while (true) {
            print("Item description (Enter to stop): ")
            val desc = scanner.nextLine()
            if (desc.isBlank()) break

            print("Item amount: ")
            val amt = scanner.nextLine().toDoubleOrNull() ?: continue

            items.add(InvoiceItem(desc, amt))
        }

        val subtotal = items.sumOf { it.amount }
        val tax = subtotal * 0.1
        val discount = 0.0
        val total = subtotal + tax - discount

        val invoice = Invoice(
            invoiceId = invoiceId,
            bookingId = bookingId,
            customerId = customerId,
            items = items,
            subtotal = subtotal,
            tax = tax,
            discount = discount,
            total = total,
            issueDate = now(),
            dueDate = now().plusDays(7),
            status = InvoiceStatus.PENDING
        )

        paymentService.issueInvoice(invoice)
        println(invoice.generateInvoiceText())
    }

    private fun handleViewAllPayments() {
        println("------- All Payments -------")
        paymentService.getAllPayments().forEach { println(it.getPaymentDetails()) }
    }

    private fun handleViewAllInvoices() {
        println("------- All Invoices -------")
        paymentService.getAllInvoices().forEach { println(it.generateInvoiceText()) }
    }

    private fun handleFindPaymentById() {
        print("Enter Payment ID: ")
        val id = scanner.nextLine().trim()
        val payment = paymentService.getPayment(id)
        if (payment != null) {
            println(payment.getPaymentDetails())
        } else {
            println("Payment not found!")
        }
    }

    private fun handleFindInvoiceById() {
        print("Enter Invoice ID: ")
        val id = scanner.nextLine().trim()
        val invoice = paymentService.getInvoice(id)
        if (invoice != null) {
            println(invoice.generateInvoiceText())
        } else {
            println("Invoice not found!")
        }
    }
}
