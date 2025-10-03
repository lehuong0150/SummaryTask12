package com.example.summarytask12.services

import com.example.summarytask12.model.payment.Invoice
import com.example.summarytask12.model.payment.Payment
import com.example.summarytask12.repository.PaymentRepository

class PaymentService(
    private val paymentRepo: PaymentRepository
) {

    fun processPayment(payment: Payment): Boolean {
        return if (paymentRepo.addPayment(payment) {
                println("Payment processed: ${payment.paymentId}")
                true
            } false
    }

    fun issueInvoice(invoice: Invoice) {
        paymentRepo.addInvoice(invoice)
        println("Invoice issued: ${invoice.invoiceId}")
    }

    fun getPayment(id: String): Payment? =
        paymentRepo.getPaymentById(id)

    fun getPaymentsByBooking(bookingId: String): List<Payment> =
        paymentRepo.getPaymentsByBookingId(bookingId)

    fun getInvoice(id: String): Invoice? =
        paymentRepo.getInvoiceById(id)

    fun getAllPayments(): List<Payment> =
        paymentRepo.getAllPayments()

    fun getAllInvoices(): List<Invoice> =
        paymentRepo.getAllInvoices()
}