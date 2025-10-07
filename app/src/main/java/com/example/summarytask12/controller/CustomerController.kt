package com.example.summarytask12.controller

import com.example.summarytask12.model.users.Customer
import com.example.summarytask12.model.users.VIPCustomer
import com.example.summarytask12.services.CustomerService
import com.example.summarytask12.utils.InputHandler
import com.example.summarytask12.utils.OutputHandler
import com.example.summarytask12.utils.Message

class CustomerController(
    private val customerService: CustomerService,
    private val input: InputHandler = InputHandler(),
    private val output: OutputHandler = OutputHandler()
) {

    fun showMenu() {
        while (true) {
            output.printHeader(Message.CUSTOMER_HEADER)
            output.printMessage("1. Register Normal Customer")
            output.printMessage("2. Register VIP Customer")
            output.printMessage("3. View All Customers")
            output.printMessage("4. Update Customer")
            output.printMessage("5. Delete Customer")
            output.printMessage("0. Back")

            when (input.prompt("Choose: ")) {
                "1" -> handleRegisterCustomer()
                "2" -> handleRegisterVIPCustomer()
                "3" -> handleViewAllCustomers()
                "4" -> handleUpdateCustomer()
                "5" -> handleDeleteCustomer()
                "0" -> return
                else -> output.printError(Message.INVALID_CHOICE)
            }
        }
    }

    private fun handleRegisterCustomer() {
        output.printSubHeader("Register Normal Customer")
        val id = input.prompt(Message.ENTER_ID).trim()
        val name = input.prompt(Message.ENTER_NAME).trim()
        val email = input.prompt(Message.ENTER_EMAIL).trim()
        val phone = input.prompt(Message.ENTER_PHONE).trim()

        val customer = Customer(id, name, email, phone)
        if (customer.validateInfo()) {
            if (customerService.registerCustomer(customer)) {
                output.printSuccess(Message.CUSTOMER_REGISTERED)
            } else {
                output.printError("Customer with ID already exists!")
            }
        } else {
            output.printError(Message.INPUT_ERROR)
        }
    }

    private fun handleRegisterVIPCustomer() {
        output.printSubHeader("Register VIP Customer")
        val id = input.prompt(Message.ENTER_ID).trim()
        val name = input.prompt(Message.ENTER_NAME).trim()
        val email = input.prompt(Message.ENTER_EMAIL).trim()
        val phone = input.prompt(Message.ENTER_PHONE).trim()

        val vipCustomer = VIPCustomer(id, name, email, phone, mutableListOf())
        val request = input.prompt(Message.CUSTOMER_SPECIAL_REQUEST).trim()
        if (request.isNotEmpty()) {
            vipCustomer.addSpecialRequest(request)
        }

        if (vipCustomer.validateInfo()) {
            if (customerService.registerCustomer(vipCustomer)) {
                output.printSuccess(Message.CUSTOMER_REGISTERED)
            } else {
                output.printError("Customer with ID already exists!")
            }
        } else {
            output.printError(Message.INPUT_ERROR)
        }
    }

    private fun handleViewAllCustomers() {
        val customers = customerService.getAllCustomers()
        if (customers.isEmpty()) {
            output.printMessage(Message.NO_DATA)
            return
        }

        output.printList("ALL CUSTOMERS", customers) { c ->
            val level = when (c::class.simpleName) {
                "VIPCustomer" -> "VIP"
                "PremiumCustomer" -> "PREMIUM"
                else -> "BASIC"
            }
            "$level -> ${c.name} | ${c.email} | ${c.phone}"
        }
    }

    private fun handleUpdateCustomer() {
        output.printSubHeader("Update Customer")
        val id = input.prompt(Message.CUSTOMER_ENTER_ID).trim()
        val customer = customerService.getCustomer(id)

        if (customer == null) {
            output.printError(Message.CUSTOMER_NOT_FOUND)
            return
        }

        output.printMessage("Current info: $customer")
        output.printMessage("(Leave blank to keep current value)")

        val name = input.prompt(Message.ENTER_NAME).trim()
        if (name.isNotEmpty()) customer.name = name

        val email = input.prompt(Message.ENTER_EMAIL).trim()
        if (email.isNotEmpty()) customer.email = email

        val phone = input.prompt(Message.ENTER_PHONE).trim()
        if (phone.isNotEmpty()) customer.phone = phone

        if (customer.validateInfo()) {
            if (customerService.updateCustomer(customer)) {
                output.printSuccess(Message.CUSTOMER_UPDATED)
                output.printMessage("Updated info: ${customer}")
            } else {
                output.printError(Message.ACTION_FAILED)
            }
        } else {
            output.printError(Message.INPUT_ERROR)
        }
    }

    private fun handleDeleteCustomer() {
        output.printSubHeader("Delete Customer")
        val id = input.prompt(Message.CUSTOMER_ENTER_ID).trim()

        val customer = customerService.getCustomer(id)
        if (customer == null) {
            output.printError(Message.CUSTOMER_NOT_FOUND)
            return
        }

        output.printMessage("About to delete: ${customer.name} (${customer.email})")
        val confirm = input.prompt("Are you sure? (yes/no)").trim().lowercase()

        if (confirm == "yes" || confirm == "y") {
            if (customerService.deleteCustomer(id)) {
                output.printSuccess(Message.CUSTOMER_DELETED)
            } else {
                output.printError(Message.ACTION_FAILED)
            }
        } else {
            output.printMessage("Deletion cancelled.")
        }
    }
}