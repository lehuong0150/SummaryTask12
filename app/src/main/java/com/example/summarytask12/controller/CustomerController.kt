package com.example.summarytask12.controller

import com.example.summarytask12.model.users.Customer
import com.example.summarytask12.model.users.VIPCustomer
import com.example.summarytask12.services.CustomerService
import com.example.summarytask12.utils.InputHandler
import com.example.summarytask12.utils.OutputHandler

class CustomerController(
    private val customerService: CustomerService,
    private val input: InputHandler = InputHandler(),
    private val output: OutputHandler = OutputHandler()
) {

    fun showMenu() {
        while (true) {
            output.printHeader("CUSTOMER MANAGEMENT")
            output.printMessage("1. Register Normal Customer")
            output.printMessage("2. Register VIP Customer")
            output.printMessage("3. View All Customers")
            output.printMessage("4. Update Customer")
            output.printMessage("5. Delete Customer")
            output.printMessage("0. Back")

            when (input.prompt("Choose")) {
                "1" -> handleRegisterCustomer()
                "2" -> handleRegisterVIPCustomer()
                "3" -> handleViewAllCustomers()
                "4" -> handleUpdateCustomer()
                "5" -> handleDeleteCustomer()
                "0" -> return
                else -> output.printError("Invalid choice!")
            }
        }
    }

    private fun handleRegisterCustomer() {
        output.printSubHeader("Register Normal Customer")
        val id = input.prompt("Enter ID")
        val name = input.prompt("Enter Name")
        val email = input.prompt("Enter Email")
        val phone = input.prompt("Enter Phone")

        val customer = Customer(id, name, email, phone)
        if (customer.validateInfo()) {
            customerService.registerCustomer(customer)
            output.printSuccess("Customer registered successfully!")
        } else {
            output.printError("Customer info invalid!")
        }
    }

    private fun handleRegisterVIPCustomer() {
        output.printSubHeader("Register VIP Customer")
        val id = input.prompt("Enter ID")
        val name = input.prompt("Enter Name")
        val email = input.prompt("Enter Email")
        val phone = input.prompt("Enter Phone")

        val vipCustomer = VIPCustomer(id, name, email, phone, mutableListOf())
        val request = input.prompt("Add special request (Enter to skip)")
        if (request.isNotBlank()) vipCustomer.addSpecialRequest(request)

        if (vipCustomer.validateInfo()) {
            customerService.registerCustomer(vipCustomer)
            output.printSuccess("VIP Customer registered successfully!")
        } else {
            output.printError("VIP Customer info invalid!")
        }
    }

    private fun handleViewAllCustomers() {
        val customers = customerService.getAllCustomers()
        if (customers.isEmpty()) {
            output.printMessage("No customers found.")
        } else {
            output.printList("ALL CUSTOMERS", customers) { c ->
                if (c is VIPCustomer) "VIP -> $c" else "Normal -> $c"
            }
        }
    }

    private fun handleUpdateCustomer() {
        output.printSubHeader("Update Customer")
        val id = input.prompt("Enter Customer ID")
        val customer = customerService.getCustomer(id)

        if (customer == null) {
            output.printError("Customer not found!")
            return
        }

        output.printMessage("Before update: $customer")

        val name = input.prompt("Enter new Name (Enter to skip)")
        if (name.isNotBlank()) customer.name = name

        val email = input.prompt("Enter new Email (Enter to skip)")
        if (email.isNotBlank()) customer.email = email

        val phone = input.prompt("Enter new Phone (Enter to skip)")
        if (phone.isNotBlank()) customer.phone = phone

        if (customerService.updateCustomer(customer)) {
            output.printSuccess("Customer updated successfully!")
            output.printMessage(customer.toString())
        } else {
            output.printError("Update failed!")
        }
    }

    private fun handleDeleteCustomer() {
        output.printSubHeader("Delete Customer")
        val id = input.prompt("Enter Customer ID")

        if (customerService.deleteCustomer(id)) {
            output.printSuccess("Customer deleted successfully!")
        } else {
            output.printError("Delete failed!")
        }
    }
}
