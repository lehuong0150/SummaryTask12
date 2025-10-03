package com.example.summarytask12.repository

import com.example.summarytask12.model.users.Customer
import com.example.summarytask12.model.users.VIPCustomer

class CustomerRepository {
    private val customers = mutableListOf<Customer>()

    fun addCustomer(customer: Customer): Boolean =
        customers.none { it.id == customer.id }
            .also { canAdd -> if (canAdd) customers.add(customer) }

    fun getAllCustomers(): List<Customer> = customers.toList()

    fun getCustomerById(id: String): Customer? = customers.find { it.id == id }

    fun updateCustomer(customer: Customer): Boolean =
        customers.indexOfFirst { it.id == customer.id }
            .takeIf { it != -1 }
            ?.let { customers[it] = customer; true } ?: false

    fun deleteCustomer(customerId: String): Boolean =
        customers.removeIf { it.id == customerId }
}
