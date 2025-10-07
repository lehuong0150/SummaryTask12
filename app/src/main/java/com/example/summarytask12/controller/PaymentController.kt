package com.example.summarytask12.controller

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.summarytask12.model.payment.Invoice
import com.example.summarytask12.model.payment.InvoiceItem
import com.example.summarytask12.model.payment.Payment
import com.example.summarytask12.repository.HotelRepository
import com.example.summarytask12.services.PaymentService
import com.example.summarytask12.utils.*
import kotlinx.coroutines.*
import java.time.LocalDateTime.now

class PaymentController(
    private val paymentService: PaymentService,
    private val input: InputHandler = InputHandler(),
    private val output: OutputHandler = OutputHandler()
) {

    // --- MAIN MENU ---
    @RequiresApi(Build.VERSION_CODES.O)
    fun showMenu() = runBlocking {
        while (true) {
            output.printHeader(Message.PAYMENT_HEADER)
            output.printMessage("1. Process Payment")
            output.printMessage("2. Issue Invoice")
            output.printMessage("3. View All Payments")
            output.printMessage("4. View All Invoices")
            output.printMessage("5. Find Payment by ID")
            output.printMessage("6. Find Invoice by ID")
            output.printMessage("0. Back")

            when (input.prompt("Choose: ")) {
                "1" -> {
                    val job = launch { handleProcessPayment() }
                    job.join()
                }

                "2" -> handleIssueInvoice()
                "3" -> handleViewAllPayments()
                "4" -> handleViewAllInvoices()
                "5" -> handleFindPaymentById()
                "6" -> handleFindInvoiceById()
                "0" -> return@runBlocking
                else -> output.printError(Message.INVALID_CHOICE)
            }
        }
    }

    // --- PROCESS PAYMENT (ASYNC) ---
    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun handleProcessPayment() = coroutineScope {
        output.printSubHeader("Process Payment")

        val paymentId = input.prompt(Message.PAYMENT_ENTER_ID)
        if (paymentId.isBlank()) {
            output.printError(Message.PAYMENT_ID_EMPTY)
            return@coroutineScope
        }

        val bookingId = input.prompt(Message.BOOKING_ENTER_ID)
        if (bookingId.isBlank()) {
            output.printError(Message.BOOKING_ID_EMPTY)
            return@coroutineScope
        }

        val booking = HotelRepository.bookingRepository.getBookingById(bookingId)
        if (booking == null) {
            output.printError(Message.BOOKING_NOT_FOUND)
            return@coroutineScope
        }

        val totalAmount = input.promptDouble("Enter amount (suggested: ${booking.totalAmount})")
            ?: run {
                output.printError(Message.PAYMENT_AMOUNT_INVALID)
                return@coroutineScope
            }

        if (totalAmount <= 0) {
            output.printError(Message.PAYMENT_AMOUNT_POSITIVE)
            return@coroutineScope
        }

        val paymentMethodInput = input.prompt(Message.BOOKING_ENTER_PAYMENT_METHOD)
        val paymentMethod = enumValues<PaymentMethod>().find {
            it.name.equals(paymentMethodInput, ignoreCase = true)
        } ?: PaymentMethod.CASH

        val payment = Payment(
            paymentId = paymentId,
            totalAmount = totalAmount,
            paymentDate = now(),
            style = paymentMethod,
            bookingId = bookingId
        )

        // Goi ham suspend xu ly bat dong bo (gia lap delay)
        output.printMessage("Processing payment... Please wait...")
        val success = paymentService.processPaymentAsync(payment)

        if (success) {
            output.printSuccess(Message.PAYMENT_PROCESS_SUCCESS)
            output.printMessage(payment.getPaymentDetails())
        } else {
            output.printError(Message.PAYMENT_PROCESS_FAILED)
        }
    }

    // --- ISSUE INVOICE ---
    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleIssueInvoice() {
        output.printSubHeader("Issue Invoice")

        val invoiceId = input.prompt(Message.INVOICE_ENTER_ID)
        val bookingId = input.prompt(Message.BOOKING_ENTER_ID)
        val customerId = input.prompt(Message.CUSTOMER_ENTER_ID)

        val items = mutableListOf<InvoiceItem>()
        while (true) {
            val desc = input.prompt("Item description (Enter to stop)")
            if (desc.isBlank()) break

            val amt = input.promptDouble("Item amount") ?: continue
            items.add(InvoiceItem(desc, amt))
        }

        val subtotal = items.sumOf { it.amount }
        val tax = subtotal * 0.1
        val total = subtotal + tax

        val invoice = Invoice(
            invoiceId = invoiceId,
            bookingId = bookingId,
            customerId = customerId,
            items = items,
            subtotal = subtotal,
            tax = tax,
            discount = 0.0,
            total = total,
            issueDate = now(),
            dueDate = now().plusDays(7),
            status = InvoiceStatus.PENDING
        )

        paymentService.issueInvoice(invoice)
        output.printSuccess(Message.INVOICE_ISSUE_SUCCESS)
        output.printMessage(invoice.generateInvoiceText())
    }

    // --- VIEW ALL PAYMENTS ---
    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleViewAllPayments() {
        val payments = paymentService.getAllPayments()
        if (payments.isEmpty())
            output.printMessage(Message.NO_DATA)
        else
            output.printList("ALL PAYMENTS", payments) { it.getPaymentDetails() }
    }

    // --- VIEW ALL INVOICES ---
    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleViewAllInvoices() {
        val invoices = paymentService.getAllInvoices()
        if (invoices.isEmpty())
            output.printMessage(Message.NO_DATA)
        else
            output.printList("ALL INVOICES", invoices) { it.generateInvoiceText() }
    }

    // --- FIND PAYMENT ---
    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleFindPaymentById() {
        val id = input.prompt(Message.PAYMENT_ENTER_ID)
        val payment = paymentService.getPayment(id)
        if (payment != null)
            output.printMessage(payment.getPaymentDetails())
        else
            output.printError(Message.PAYMENT_NOT_FOUND)
    }

    // --- FIND INVOICE ---
    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleFindInvoiceById() {
        val id = input.prompt(Message.INVOICE_ENTER_ID)
        val invoice = paymentService.getInvoice(id)
        if (invoice != null)
            output.printMessage(invoice.generateInvoiceText())
        else
            output.printError(Message.INVOICE_NOT_FOUND)
    }
}
