package com.example.summarytask12.controller

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.summarytask12.model.payment.Invoice
import com.example.summarytask12.model.payment.InvoiceItem
import com.example.summarytask12.model.payment.Payment
import com.example.summarytask12.repository.HotelRepository
import com.example.summarytask12.services.PaymentService
import com.example.summarytask12.utils.*
import java.time.LocalDateTime.now

class PaymentController(
    private val paymentService: PaymentService,
    private val input: InputHandler = InputHandler(),
    private val output: OutputHandler = OutputHandler()
) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun showMenu() {
        while (true) {
            output.printHeader("PAYMENT MANAGEMENT")
            output.printMessage("1. Process Payment")
            output.printMessage("2. Issue Invoice")
            output.printMessage("3. View All Payments")
            output.printMessage("4. View All Invoices")
            output.printMessage("5. Find Payment by ID")
            output.printMessage("6. Find Invoice by ID")
            output.printMessage("0. Back")

            when (input.prompt("Choose")) {
                "1" -> handleProcessPayment()
                "2" -> handleIssueInvoice()
                "3" -> handleViewAllPayments()
                "4" -> handleViewAllInvoices()
                "5" -> handleFindPaymentById()
                "6" -> handleFindInvoiceById()
                "0" -> return
                else -> output.printError("Invalid choice!")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleProcessPayment() {
        output.printSubHeader("Process Payment")

        val paymentId = input.prompt("Payment ID")
        if (paymentId.isBlank()) {
            output.printError("Payment ID cannot be empty!")
            return
        }

        val bookingId = input.prompt("Booking ID")
        if (bookingId.isBlank()) {
            output.printError("Booking ID cannot be empty!")
            return
        }

        val booking = HotelRepository.bookingRepository.getBookingById(bookingId)
        if (booking == null) {
            output.printError("Booking not found!")
            return
        }

        val totalAmount = input.promptDouble("Amount (suggested: ${booking.totalAmount})") ?: run {
            output.printError("Invalid amount!")
            return
        }
        if (totalAmount <= 0) {
            output.printError("Amount must be positive!")
            return
        }

        val paymentMethodInput = input.prompt("Payment method (CASH/CREDIT_CARD)")
        val paymentMethod = enumValues<PaymentMethod>().find {
            it.name.equals(paymentMethodInput, ignoreCase = true)
        } ?: PaymentMethod.CASH

        val payment = when (paymentMethod) {
            PaymentMethod.CASH -> Payment(
                paymentId = paymentId,
                totalAmount = totalAmount,
                paymentDate = now(),
                style = PaymentStyle.CashPayment,
                bookingId = bookingId
            )

            PaymentMethod.CREDIT_CARD -> Payment(
                paymentId = paymentId,
                totalAmount = totalAmount,
                paymentDate = now(),
                style = PaymentStyle.CreditCardPayment,
                bookingId = bookingId
            )
        }

        if (paymentService.processPayment(payment)) {
            output.printSuccess("Payment processed successfully!")
            output.printMessage(payment.getPaymentDetails())
        } else {
            output.printError("Failed to process payment!")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleIssueInvoice() {
        output.printSubHeader("Issue Invoice")

        val invoiceId = input.prompt("Invoice ID")
        val bookingId = input.prompt("Booking ID")
        val customerId = input.prompt("Customer ID")

        val items = mutableListOf<InvoiceItem>()
        while (true) {
            val desc = input.prompt("Item description (Enter to stop)")
            if (desc.isBlank()) break

            val amt = input.promptDouble("Item amount") ?: continue
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
        output.printSuccess("Invoice issued successfully!")
        output.printMessage(invoice.generateInvoiceText())
    }

    private fun handleViewAllPayments() {
        val payments = paymentService.getAllPayments()
        if (payments.isEmpty()) output.printMessage("No payments found.")
        else output.printList("ALL PAYMENTS", payments) { it.getPaymentDetails() }
    }

    private fun handleViewAllInvoices() {
        val invoices = paymentService.getAllInvoices()
        if (invoices.isEmpty()) output.printMessage("No invoices found.")
        else output.printList("ALL INVOICES", invoices) { it.generateInvoiceText() }
    }

    private fun handleFindPaymentById() {
        val id = input.prompt("Enter Payment ID")
        val payment = paymentService.getPayment(id)
        if (payment != null)
            output.printMessage(payment.getPaymentDetails())
        else
            output.printError("Payment not found!")
    }

    private fun handleFindInvoiceById() {
        val id = input.prompt("Enter Invoice ID")
        val invoice = paymentService.getInvoice(id)
        if (invoice != null)
            output.printMessage(invoice.generateInvoiceText())
        else
            output.printError("Invoice not found!")
    }
}
