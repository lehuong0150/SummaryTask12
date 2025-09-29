package com.example.summarytask12.controller

import com.example.summarytask12.extensions.availableOnly
import com.example.summarytask12.extensions.filterByPriceRange
import com.example.summarytask12.extensions.filterByType
import com.example.summarytask12.extensions.sortByPrice
import com.example.summarytask12.model.accommodation.DeluxeRoom
import com.example.summarytask12.model.accommodation.Room
import com.example.summarytask12.model.accommodation.StandardRoom
import com.example.summarytask12.model.accommodation.SuiteRoom
import com.example.summarytask12.services.RoomService
import java.util.*

class RoomController(
    private val roomService: RoomService,
) {
    private val scanner: Scanner = Scanner(System.`in`)
    fun showMenu() {
        while (true) {
            println("\n--- ROOM MANAGEMENT ---")
            println("1. View all rooms")
            println("2. Add new room")
            println("3. Find room by ID")
            println("4. Search room")
            println("5. Update status room")
            println("6. Delete room")
            println("0. Back")
            print("Choose: ")

            when (scanner.nextLine()) {
                "1" -> handleViewAllRooms()
                "2" -> handleCreateRoom()
                "3" -> handleFindRoomById()
                "4" -> handSearchRooms()
                "5" -> handUpdateStatusRoom()
                "6" -> handleDeleteRoom()
                "0" -> return
                else -> println("Invalid choice")
            }
        }
    }

    private fun handleViewAllRooms() {
        val rooms = roomService.getAllRooms()
        if (rooms.isEmpty()) println("No rooms found.")
        else rooms.forEach { println(it) }
    }

    private fun handleCreateRoom() {
        println("-------Add Room-------: ")

        print("Input Id room: ")
        val idRoom = scanner.nextLine().trim()

        print("Input number room: ")
        val numberRoom = scanner.nextLine().trim()

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
            "STANDARD" -> StandardRoom(idRoom, numberRoom, priceRoom, isAvailable)
            "DELUXE" -> {
                println("Has Ocean View? (true/false): ")
                val hasOceanView = scanner.nextLine().toBooleanStrictOrNull() ?: false
                DeluxeRoom(idRoom, numberRoom, priceRoom, isAvailable, hasOceanView)
            }

            "SUITE" -> SuiteRoom(idRoom, numberRoom, priceRoom, isAvailable)
            else -> {
                println("Invalid room type!!!!!!!")
                return
            }
        }
        if (roomService.addRoom(newRoom)) {
            println("Room added successfully: $newRoom")
        } else {
            println("Failed to add room!!")
        }
    }

    private fun handleFindRoomById() {
        print("Enter Room ID: ")
        val id = scanner.nextLine()
        val room = roomService.getRoom(id)
        println(room ?: "Room not found")
    }

    private fun handSearchRooms() {
        println("-------Search room...------- ")

        print("Type room (STANDARD/DELUXE/SUITE): ")
        val typeRoom = scanner.nextLine().takeIf { it.isNotBlank() }?.uppercase()

        print("Min price (VND): ")
        val minPrice = scanner.nextLine().toDoubleOrNull() ?: 0.0

        print("Max price (VND): ")
        val maxPrice = scanner.nextLine().toDoubleOrNull() ?: Double.MAX_VALUE

        val availableRooms = roomService.getAllRooms()
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

    private fun handUpdateStatusRoom() {
        println("------- Update Room Status -------")

        print("Enter Room ID: ")
        val id = scanner.nextLine().trim()

        print("Is Available? (true/false): ")
        val isAvailable = scanner.nextLine().toBooleanStrictOrNull()
        if (isAvailable == null) {
            println("Invalid input! Please enter true or false.")
            return
        }

        val updated = roomService.updateRoomAvailability(id, isAvailable)
        if (updated) {
            println("Room status updated successfully.")
        } else {
            println("Failed! Room with ID [$id] not found.")
        }
    }

    private fun handleDeleteRoom() {
        print("Enter Room ID: ")
        val id = scanner.nextLine().trim()
        if (roomService.deleteRoom(id)) {
            println("Deleted!")
        } else {
            println("Room not found!")
        }
    }
}
