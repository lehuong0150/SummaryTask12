package com.example.summarytask12

import com.example.summarytask12.database.HotelDB
import com.example.summarytask12.extensions.availableOnly
import com.example.summarytask12.extensions.canCancel
import com.example.summarytask12.extensions.filterByPriceRange
import com.example.summarytask12.extensions.filterByType
import com.example.summarytask12.extensions.sortByPrice
import com.example.summarytask12.model.Booking
import com.example.summarytask12.model.accommodation.DeluxeRoom
import com.example.summarytask12.model.accommodation.StandardRoom
import com.example.summarytask12.model.accommodation.SuiteRoom
import com.example.summarytask12.model.users.VIPCustomer
import com.example.summarytask12.utils.BookingStatus

import java.util.Scanner

class HotelBookingApp {
    private val scanner = Scanner(System.`in`)

    //1. search room
    private fun searchRooms() {
        println("-------Search room...------- ")

        print("Type room (STANDARD/DELUXE/SUITE): ")
        val typeRoom = scanner.nextLine().takeIf { it.isNotBlank() }?.uppercase()

        print("Min price (VND): ")
        val minPrice = scanner.nextLine().toDoubleOrNull() ?: 0.0

        print("Max price (VND): ")
        val maxPrice = scanner.nextLine().toDoubleOrNull() ?: Double.MAX_VALUE

        // Lọc danh sách phòng
        val availableRooms = HotelDB.getRoomList()
            .availableOnly()
            .filterByPriceRange(minPrice, maxPrice)
            .let { if (typeRoom != null) it.filterByType(typeRoom) else it }
            .sortByPrice()

        if (availableRooms.isEmpty()) {
            println("Room not found")
        } else {
            println("List room: ")
            availableRooms.forEach { println(it) }
        }
    }

    //2. createBooking
    private fun createBooking() {
        println("-------Booking Room-------: ")
        print("Input Id Customer: ")
        val customerId = scanner.nextLine()

        HotelDB.getCustomer(customerId)?.let { customer ->
            customer.getDisplayInfo()

            if (customer is VIPCustomer) {
                println("Membership Level: VIP")
                print("Special Requests (Enter to skip ): ")
                val request = scanner.nextLine()
                if (request.isNotBlank()) {
                    customer.addSpecialRequest(request)
                } else {
                    println("Request createBooking null ")
                }
            } else {
                println("Membership Level: ${customer.getMembershipLevel}")

            }
            customer
        } ?: run {
            println("Not found customer!!!!")
        }
        print("Input Id Room: ")
        val roomId = scanner.nextLine()
        val room = HotelDB.getRoom(roomId)?.takeIf { it.isAvailable } ?: run {
            println("Not found room!!!!")
            return
        }
        println("Information Room: $room ")

        print("Input nights : ")
        val nights = scanner.nextLine().toIntOrNull() ?: run {
            println("Invalid number of nights!!!!")
            return
        }
        if (nights < 0) {
            println("Number of nights must be greater than 0!!!!")
            return
        }

        print("Method payment (CREDIT_CARD/CASH) or Enter to skip: ")
        val methodPayment = scanner.nextLine()

        print("Date booking room or Enter to skip: ")
        val dateCreated = scanner.nextLine()

        //Tao phong
        val booking =
            Booking("B004", customerId, roomId, dateCreated, nights, methodPayment, "CASH")
        if (HotelDB.addBooking(booking)) {
            println("Booking Room successfully!!!")
            println("Booking created: $booking")
        } else (println("Booking Room Fail!!!"))
    }

    //3. cancelBooking
    private fun cancelBooking() {
        println("-------Cancel Booking------: ")
        print("Input id booking: ")
        val bookingId = scanner.nextLine()

        val booking = HotelDB.getBooking(bookingId)?.let {
            if (it.canCancel()) {
                it.status = BookingStatus.CANCELLED
                it.totalAmount = 0.0
                HotelDB.updateBooking(it)
                println("Update booking successfully!!!")
            } else {
                println("Can't cancel booking!!!")
                return
            }
        } ?: run {
            println("Not found booking!!!!!!!!!")
            return
        }
        println("Booking after update: $booking")

    }

    //4. View All Rooms
    private fun viewAllRooms() {
        println("View All Rooms: ")
        println(HotelDB.getRoomList())
    }

    //5. View All Bookings
    private fun viewAllBookings() {
        println("View All Bookings: ")
        println(HotelDB.getBookingList())
    }

    //6. Add Room
    private fun addRoom() {
        println("-------Add Room-------: ")

        print("Input Id room: ")
        val idRoom = scanner.nextLine().trim()

        print("Input type room: ")
        val typeRoom = scanner.nextLine().trim().uppercase()

        print("Input price room: ")
        val priceRoom = scanner.nextLine().toDoubleOrNull() ?: run {
            println("Invalid price!")
            return
        }
        println("Is Available? (true/false): ")
        val isAvailable = scanner.nextLine().toBooleanStrictOrNull() ?: true

        val newRoom = when (typeRoom) {
            "STANDARD" -> StandardRoom(idRoom, priceRoom, isAvailable)
            "DELUXE" -> {
                println("Has Ocean View? (true/false): ")
                val hasOceanView = scanner.nextLine().toBooleanStrictOrNull() ?: false
                DeluxeRoom(idRoom, priceRoom, isAvailable, hasOceanView)
            }

            "SUITE" -> SuiteRoom(idRoom, priceRoom, isAvailable)
            else -> {
                println("Invalid room type!!!!!!!")
                return
            }
        }
        if (HotelDB.addRoom(newRoom)) {
            println("Room added successfully: $newRoom")
        } else {
            println("Failed to add room!!")
        }
    }

    //7. Update Room
    private fun updateRoom() {
        println("-------Update Room-------")
        print("Input Id Room: ")
        val idRoom = scanner.nextLine().trim()

        //Tim phong theo Id
        val room = HotelDB.getRoom(idRoom)?: run {
            println("Not found room!!!")
            return
        }
        //thong tin phong truoc khi update
        println("Room before update: $room")

        //Nhap thong tin phong moi
        print("Input new price: ")
        scanner.nextLine().toDoubleOrNull()?.let { newPrice ->
            room.price = newPrice
        } ?: run {
            println("Invalid price!")
            return
        }

        print("Input new Is available? (true/false): ")
        scanner.nextLine().toBooleanStrictOrNull()?.let { newIsAvailble ->
            room.isAvailable = newIsAvailble
        }?: run {
            println("Invalid!!")
            return
        }

        if (room.type == "DELUXE") {
            print("Has Ocean View? (true/false): ")
            scanner.nextLine().toBooleanStrictOrNull()?.let { newHasOceanView ->
                room.isAvailable = newHasOceanView
            }
        }

        //Thong tin phong sau khi update
        println("Room updated successfully: $room")
    }

    //8. Delete Room
    private fun deleteRoom(){
        println("-------Delete Room-------")
        print("Input Id Room: ")
        val idRoom = scanner.nextLine().trim()

        //Tim phong theo Id
        val room = HotelDB.getRoom(idRoom)?: run {
            println("Not found room!!!")
            return
        }

        if(HotelDB.deleteRoom(room.id)){
            println("Delete room successfully!!!!")
        }else {
            println("Delete room fail!!!")
        }
    }

    //9. View All Customer
    private fun viewAllCustomer(){
        println("View All Customers: ")
        val customerList = HotelDB.getCustomerList()
        for(customer in customerList){
            customer.getDisplayInfo()
        }
    }

    //Show Menu
    fun showMenu() {
        while (true) {
            println("\n" + "=".repeat(50))
            println("MANAGER MENU")
            println("1. Search Room")
            println("2. Create Booking")
            println("3. Cancel Room")
            println("4. View All Rooms")
            println("5. View All Bookings")
            println("6. Add Room")
            println("7. Update Room")
            println("8. Delete Room")
            println("9. View All Customers")
            println("0. Exit ")
            println("\n" + "=".repeat(50))
            print("Choose function (0-10): ")

            when (scanner.nextLine()) {
                "1" -> searchRooms()
                "2" -> createBooking()
                "3" -> cancelBooking()
                "4" -> viewAllRooms()
                "5" -> viewAllBookings()
                "6" -> addRoom()
                "7" -> updateRoom()
                "8" -> deleteRoom()
                "9" -> viewAllCustomer()
                "0" -> {
                    println("Exit!! Thank you!!")
                    break
                }
                else -> println("Invalid selection! Please try again.")
            }

        }
    }
}