package com.example.summarytask12.services

import com.example.summarytask12.model.payment.Invoice
import com.example.summarytask12.model.payment.Payment
import com.example.summarytask12.repository.PaymentRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

class PaymentService(
    private val paymentRepo: PaymentRepository
) {

    // Xu ly thanh toan bat dong bo
    suspend fun processPaymentAsync(payment: Payment): Boolean = coroutineScope {
        try {
            println("Processing payment asynchronously...")
            delay(1000) // mo phong do tre goi API
            val result = paymentRepo.addPayment(payment)
            if (result) {
                println("Payment processed: ${payment.paymentId}")
            } else {
                println("Failed to process payment: ${payment.paymentId}")
            }
            result
        } catch (e: Exception) {
            println("Error while processing payment: ${e.message}")
            false
        }
    }

    fun issueInvoice(invoice: Invoice) {
        paymentRepo.addInvoice(invoice)
        println("Invoice issued: ${invoice.invoiceId}")
    }

    fun getPayment(id: String): Payment? =
        paymentRepo.getPaymentById(id)

    fun getInvoice(id: String): Invoice? =
        paymentRepo.getInvoiceById(id)

    fun getAllPayments(): List<Payment> =
        paymentRepo.getAllPayments()

    fun getAllInvoices(): List<Invoice> =
        paymentRepo.getAllInvoices()
}
