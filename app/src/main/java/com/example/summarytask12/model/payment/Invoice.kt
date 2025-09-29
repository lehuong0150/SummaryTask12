package com.example.summarytask12.model.payment

import android.os.Build
import com.example.summarytask12.utils.InvoiceStatus
import java.time.LocalDateTime

data class Invoice(
    val invoiceId: String,
    val bookingId: String,
    val customerId: String,
    val items: List<InvoiceItem>,
    val subtotal: Double,
    val tax: Double,
    val discount: Double,
    val total: Double,
    val issueDate: LocalDateTime,
    val dueDate: LocalDateTime,
    val status: InvoiceStatus = InvoiceStatus.PENDING
) {
    fun generateInvoiceText(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            "==================================================\n" +
                    "              HOTEL INVOICE\n" +
                    "==================================================\n" +
                    "Invoice ID: $invoiceId\n" +
                    "Booking ID: $bookingId\n" +
                    "Customer ID: $customerId\n" +
                    "Issue Date: ${issueDate.toLocalDate()}\n" +
                    "Due Date: ${dueDate.toLocalDate()}\n" +
                    "--------------------------------------------------\n" +
                    "ITEMS:\n" +
                    items.joinToString("\n") { "${it.description}: $${it.amount}" } + "\n" +
                    "--------------------------------------------------\n" +
                    "Subtotal: $$subtotal\n" +
                    "Tax: $$tax\n" +
                    "Discount: -$$discount\n" +
                    "TOTAL: $$total\n" +
                    "=================================================="
        } else {
            TODO("VERSION.SDK_INT < O")
        }
    }
}