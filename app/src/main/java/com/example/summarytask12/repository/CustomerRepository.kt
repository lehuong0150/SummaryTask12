package com.example.summarytask12.repository

import com.example.summarytask12.model.users.Customer

class CustomerRepository : BaseRepository<Customer>() {

    fun addCustomer(customer: Customer): Boolean =
        data.none { it.id == customer.id }
            .also { canAdd ->
                if (canAdd) data.add(customer)
            }

    fun getAllCustomers(): List<Customer> = getAll()

    fun getCustomerById(id: String): Customer? = findBy { it.id == id }

    fun updateCustomer(customer: Customer): Boolean {
        val index = data.indexOfFirst { it.id == customer.id }
        if (index == -1) return false
        data[index] = customer
        return true
    }

    fun deleteCustomer(customerId: String): Boolean =
        data.removeIf { it.id == customerId }
}
