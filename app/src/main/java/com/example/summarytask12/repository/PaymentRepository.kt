package com.example.summarytask12.repository

import com.example.summarytask12.model.payment.Invoice
import com.example.summarytask12.model.payment.Payment

class PaymentRepository : BaseRepository<Payment>() {

    private val invoices = mutableListOf<Invoice>()

    // Payment
    fun addPayment(payment: Payment): Boolean = data.add(payment)

    fun getPaymentById(id: String): Payment? = findBy { it.paymentId == id }

    fun getAllPayments(): List<Payment> = getAll()

    // Invoice
    fun addInvoice(invoice: Invoice) {
        invoices.add(invoice)
    }

    fun getInvoiceById(id: String): Invoice? =
        invoices.find { it.invoiceId == id }

    fun getAllInvoices(): List<Invoice> = invoices.toList()
}
