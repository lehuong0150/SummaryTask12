package com.example.summarytask12.repository

import com.example.summarytask12.model.payment.Invoice
import com.example.summarytask12.model.payment.Payment

class PaymentRepository {
    private val payments = mutableListOf<Payment>()
    private val invoices = mutableListOf<Invoice>()

    fun addPayment(payment: Payment): Boolean {
        return payments.add(payment)
    }

    fun addInvoice(invoice: Invoice) {
        invoices.add(invoice)
    }

    fun getAllPayments(): List<Payment> = payments.toList()

    fun getAllInvoices(): List<Invoice> = invoices.toList()

    fun getPaymentById(id: String): Payment? =
        payments.find { it.paymentId == id }

    fun getPaymentsByBookingId(bookingId: String): List<Payment> =
        payments.filter { it.bookingId == bookingId }

    fun getInvoiceById(id: String): Invoice? =
        invoices.find { it.invoiceId == id }
}