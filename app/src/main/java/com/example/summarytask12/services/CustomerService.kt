package com.example.summarytask12.services

import com.example.summarytask12.model.users.Customer
import com.example.summarytask12.model.users.VIPCustomer
import com.example.summarytask12.repository.CustomerRepository
import com.example.summarytask12.repository.HotelRepository


class CustomerService(private val customerRepo: CustomerRepository) {

    fun registerCustomer(customer: Customer): Boolean {
        if (customerRepo.addCustomer(customer)) {
            println("Customer registered: ${customer.id}")
            return true
        } else {
            return false
        }
    }

    fun getCustomer(id: String): Customer? =
        customerRepo.getCustomerById(id)

    fun getAllCustomers(): List<Customer> {
        return customerRepo.getAllCustomers()
    }

    fun updateCustomer(customer: Customer): Boolean =
        customerRepo.updateCustomer(customer)

    fun deleteCustomer(id: String): Boolean =
        customerRepo.deleteCustomer(id)
}