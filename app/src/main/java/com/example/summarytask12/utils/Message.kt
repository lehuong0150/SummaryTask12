package com.example.summarytask12.utils

object Message {
    // ==== COMMON ====
    const val INVALID_CHOICE = "Invalid choice!"
    const val NOT_FOUND = "Not found!"
    const val INPUT_ERROR = "Invalid input!"
    const val ACTION_SUCCESS = "Action completed successfully!"
    const val ACTION_FAILED = "Action failed!"
    const val ENTER_ID = "Enter ID: "
    const val ENTER_NAME = "Enter Name (Enter to skip): "
    const val ENTER_EMAIL = "Enter Email (Enter to skip): "
    const val ENTER_PHONE = "Enter Phone (Enter to skip): "
    const val ENTER_PRICE = "Enter Price (Enter to skip): "
    const val ENTER_STATUS = "Enter Status (true/false): "
    const val ENTER_DATE = "Enter Date (Enter to use today): "
    const val NO_DATA = "No data found!"

    // ==== BOOKING ====
    const val BOOKING_HEADER = "BOOKING MANAGEMENT"
    const val BOOKING_CREATE = "Create Booking"
    const val BOOKING_CREATED = "Booking created successfully!"
    const val BOOKING_FAILED = "Failed to create booking!"
    const val BOOKING_CONFIRM = "Booking confirmed successfully."
    const val BOOKING_CANCEL = "Booking cancelled successfully."
    const val BOOKING_DELETE = "Booking deleted successfully."
    const val BOOKING_NOT_FOUND = "Booking not found!"
    const val BOOKING_NO_DATA = "No bookings found."
    const val BOOKING_CUSTOMER_NOT_FOUND = "Customer not found!"
    const val BOOKING_ROOM_NOT_FOUND = "Room not found or not available!"
    const val BOOKING_ENTER_ID = "Enter Booking ID: "
    const val BOOKING_ENTER_CUSTOMER_ID = "Enter Customer ID: "
    const val BOOKING_ENTER_ROOM_ID = "Enter Room ID: "
    const val BOOKING_ENTER_NIGHTS = "Enter number of nights"
    const val BOOKING_ENTER_PAYMENT_METHOD = "Payment method (CREDIT_CARD/CASH, Enter to skip): "
    const val BOOKING_ENTER_DATE = "Booking date (Enter to use today): "

    // ==== ROOM ====
    const val ROOM_HEADER = "ROOM MANAGEMENT"
    const val ROOM_ENTER_ID = "Enter Room ID: "
    const val ROOM_ENTER_NUMBER = "Enter Room Number: "
    const val ROOM_ENTER_TYPE = "Room Type (STANDARD/DELUXE/SUITE), Enter to skip: "
    const val ROOM_CREATED = "Room added successfully!"
    const val ROOM_DELETED = "Room deleted successfully!"
    const val ROOM_NOT_FOUND = "Room not found!"
    const val ROOM_UPDATED = "Room updated successfully!"

    // ==== CUSTOMER ====
    const val CUSTOMER_HEADER = "CUSTOMER MANAGEMENT"
    const val CUSTOMER_ENTER_ID = "Enter Customer ID: "
    const val CUSTOMER_SPECIAL_REQUEST = "Add special request (Enter to skip): "
    const val CUSTOMER_REGISTERED = "Customer registered successfully!"
    const val CUSTOMER_UPDATED = "Customer updated successfully!"
    const val CUSTOMER_DELETED = "Customer deleted successfully!"
    const val CUSTOMER_NOT_FOUND = "Customer not found!"

    // Payment
    const val PAYMENT_HEADER = "PAYMENT MANAGEMENT"
    const val PAYMENT_ENTER_ID = "Enter Payment ID: "
    const val PAYMENT_PROCESS_SUCCESS = "Payment processed successfully!"
    const val PAYMENT_PROCESS_FAILED = "Failed to process payment!"
    const val PAYMENT_NOT_FOUND = "Payment not found!"
    const val PAYMENT_AMOUNT_INVALID = "Invalid amount!"
    const val PAYMENT_AMOUNT_POSITIVE = "Amount must be positive!"
    const val PAYMENT_ID_EMPTY = "Payment ID cannot be empty!"
    const val BOOKING_ID_EMPTY = "Booking ID cannot be empty!"

    // Invoice
    const val REPORT_HEADER = "REPORT MANAGEMENT"
    const val INVOICE_ENTER_ID = "Enter Invoice ID: "
    const val INVOICE_ISSUE_SUCCESS = "Invoice issued successfully!"
    const val INVOICE_NOT_FOUND = "Invoice not found!"
}