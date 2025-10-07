package com.example.summarytask12.model.payment

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.summarytask12.utils.InvoiceStatus
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
    @RequiresApi(Build.VERSION_CODES.O)
    fun generateInvoiceText(): String {
        val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

        return buildString {
            appendLine("==================================================")
            appendLine("              HOTEL INVOICE")
            appendLine("==================================================")
            appendLine("Invoice ID: $invoiceId")
            appendLine("Booking ID: $bookingId")
            appendLine("Customer ID: $customerId")
            appendLine("Issue Date: ${issueDate.format(dateFormatter)}")
            appendLine("Due Date: ${dueDate.format(dateFormatter)}")
            appendLine("--------------------------------------------------")
            appendLine("ITEMS:")
            items.forEach {
                appendLine("${it.description}: $$${String.format("%.2f", it.amount)}")
            }
            appendLine("--------------------------------------------------")
            appendLine("Subtotal: $$${String.format("%.2f", subtotal)}")
            appendLine("Tax: $$${String.format("%.2f", tax)}")
            appendLine("Discount: -$$${String.format("%.2f", discount)}")
            appendLine("==================================================")
            appendLine("TOTAL: $$${String.format("%.2f", total)}")
            appendLine("==================================================")
        }
    }
}