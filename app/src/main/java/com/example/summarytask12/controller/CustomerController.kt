package com.example.summarytask12.controllers

import com.example.summarytask12.model.users.Customer
import com.example.summarytask12.model.users.VIPCustomer
import com.example.summarytask12.services.CustomerService
import java.util.*

class CustomerController(
    private val customerService: CustomerService
) {
    private val scanner = Scanner(System.`in`)

    // Show Menu
    fun showMenu() {
        while (true) {
            println("\n=== CUSTOMER MENU ===")
            println("1. Register Normal Customer")
            println("2. Register VIP Customer")
            println("3. View All Customers")
            println("4. Update Customer")
            println("5. Delete Customer")
            println("0. Exit")
            print("Choose option: ")

            when (scanner.nextLine()) {
                "1" -> handleRegisterCustomer()
                "2" -> handleRegisterVIPCustomer()
                "3" -> handleViewAllCustomers()
                "4" -> handleUpdateCustomer()
                "5" -> handleDeleteCustomer()
                "0" -> {
                    println("Exit Customer Menu...")
                    return
                }

                else -> println("Invalid choice!")
            }
        }
    }

    // 1. Đăng ký khách hàng thường
    private fun handleRegisterCustomer() {
        println("---- Register Normal Customer ----")
        print("Enter ID: ")
        val id = scanner.nextLine().trim()
        print("Enter Name: ")
        val name = scanner.nextLine().trim()
        print("Enter Email: ")
        val email = scanner.nextLine().trim()
        print("Enter Phone: ")
        val phone = scanner.nextLine().trim()

        val customer = Customer(id, name, email, phone)
        if (customer.validateInfo()) {
            customerService.registerCustomer(customer)
        } else {
            println("Customer info invalid!")
        }
    }

    // 2. Đăng ký VIP Customer
    private fun handleRegisterVIPCustomer() {
        println("---- Register VIP Customer ----")
        print("Enter ID: ")
        val id = scanner.nextLine().trim()
        print("Enter Name: ")
        val name = scanner.nextLine().trim()
        print("Enter Email: ")
        val email = scanner.nextLine().trim()
        print("Enter Phone: ")
        val phone = scanner.nextLine().trim()

        val vipCustomer = VIPCustomer(id, name, email, phone, mutableListOf())
        println("Add special request (Enter để bỏ qua): ")
        val request = scanner.nextLine()
        if (request.isNotBlank()) {
            vipCustomer.addSpecialRequest(request)
        }

        if (vipCustomer.validateInfo()) {
            customerService.registerCustomer(vipCustomer) // lưu chung
        } else {
            println("VIP Customer info invalid!")
        }
    }

    // 3. Xem tất cả customer
    private fun handleViewAllCustomers() {
        println("---- All Customers ----")
        val customers = customerService.getAllCustomers()
        if (customers.isEmpty()) {
            println("No customers found.")
        } else {
            customers.forEach { customer ->
                if (customer is VIPCustomer) {
                    println("VIP -> $customer")
                } else {
                    println("Normal -> $customer")
                }
            }
        }
    }

    // 4. Cập nhật Customer
    private fun handleUpdateCustomer() {
        println("---- Update Customer ----")
        print("Enter Customer ID: ")
        val id = scanner.nextLine().trim()

        val customer = customerService.getCustomer(id)
        if (customer == null) {
            println("Customer not found!")
            return
        }
        println("Before update: $customer")

        print("Enter new Name (Enter để bỏ qua): ")
        val name = scanner.nextLine()
        if (name.isNotBlank()) customer.name = name

        print("Enter new Email (Enter để bỏ qua): ")
        val email = scanner.nextLine()
        if (email.isNotBlank()) customer.email = email

        print("Enter new Phone (Enter để bỏ qua): ")
        val phone = scanner.nextLine()
        if (phone.isNotBlank()) customer.phone = phone

        if (customerService.updateCustomer(customer)) {
            println("Customer updated: $customer")
        } else {
            println("Update failed!")
        }
    }

    // 5. Xóa Customer
    private fun handleDeleteCustomer() {
        println("---- Delete Customer ----")
        print("Enter Customer ID: ")
        val id = scanner.nextLine().trim()

        if (customerService.deleteCustomer(id)) {
            println("Customer deleted successfully!")
        } else {
            println("Delete failed!")
        }
    }
}
