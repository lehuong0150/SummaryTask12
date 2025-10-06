package com.example.summarytask12.controller

import com.example.summarytask12.extensions.availableOnly
import com.example.summarytask12.extensions.filterByPriceRange
import com.example.summarytask12.extensions.filterByType
import com.example.summarytask12.extensions.sortByPrice
import com.example.summarytask12.model.accommodation.DeluxeRoom
import com.example.summarytask12.model.accommodation.StandardRoom
import com.example.summarytask12.model.accommodation.SuiteRoom
import com.example.summarytask12.services.RoomService
import com.example.summarytask12.utils.InputHandler
import com.example.summarytask12.utils.OutputHandler

class RoomController(
    private val roomService: RoomService,
    private val input: InputHandler = InputHandler(),
    private val output: OutputHandler = OutputHandler()
) {

    fun showMenu() {
        while (true) {
            output.printHeader("ROOM MANAGEMENT")
            output.printMessage("1. View all rooms")
            output.printMessage("2. Add new room")
            output.printMessage("3. Find room by ID")
            output.printMessage("4. Search room")
            output.printMessage("5. Update room status")
            output.printMessage("6. Delete room")
            output.printMessage("0. Back")
            val choice = input.prompt("Choose")

            when (choice) {
                "1" -> handleViewAllRooms()
                "2" -> handleCreateRoom()
                "3" -> handleFindRoomById()
                "4" -> handleSearchRooms()
                "5" -> handleUpdateStatusRoom()
                "6" -> handleDeleteRoom()
                "0" -> return
                else -> output.printError("Invalid choice!")
            }
        }
    }

    private fun handleViewAllRooms() {
        val rooms = roomService.getAllRooms()
        if (rooms.isEmpty()) output.printMessage("No rooms found.")
        else output.printList("ALL ROOMS", rooms) { it.toString() }
    }

    private fun handleCreateRoom() {
        output.printSubHeader("Add Room")

        val idRoom = input.prompt("Room ID")
        val numberRoom = input.prompt("Room Number")
        val typeRoom = input.prompt("Room Type (STANDARD/DELUXE/SUITE)").uppercase()
        val priceRoom = input.promptDouble("Room Price") ?: run {
            output.printError("Invalid price!")
            return
        }
        val isAvailable = input.promptBoolean("Is Available? (true/false)") ?: true

        val newRoom = when (typeRoom) {
            "STANDARD" -> StandardRoom(idRoom, numberRoom, priceRoom, isAvailable)
            "DELUXE" -> {
                val hasOceanView = input.promptBoolean("Has Ocean View? (true/false)") ?: false
                DeluxeRoom(idRoom, numberRoom, priceRoom, isAvailable, hasOceanView)
            }

            "SUITE" -> SuiteRoom(idRoom, numberRoom, priceRoom, isAvailable)
            else -> {
                output.printError("Invalid room type!")
                return
            }
        }

        if (roomService.addRoom(newRoom))
            output.printSuccess("Room added successfully: $newRoom")
        else
            output.printError("Failed to add room!")
    }

    private fun handleFindRoomById() {
        val id = input.prompt("Enter Room ID")
        val room = roomService.getRoom(id)
        if (room != null)
            output.printMessage(room.toString())
        else
            output.printError("Room not found!")
    }

    private fun handleSearchRooms() {
        output.printSubHeader("Search Rooms")

        val typeRoom = input.prompt("Room Type (STANDARD/DELUXE/SUITE, Enter to skip)")
            .takeIf { it.isNotBlank() }?.uppercase()
        val minPrice = input.promptDouble("Min Price (VND)") ?: 0.0
        val maxPrice = input.promptDouble("Max Price (VND)") ?: Double.MAX_VALUE

        val result = roomService.getAllRooms()
            .availableOnly()
            .filterByPriceRange(minPrice, maxPrice)
            .let { if (typeRoom != null) it.filterByType(typeRoom) else it }
            .sortByPrice()

        if (result.isEmpty()) output.printMessage("No rooms found.")
        else output.printList("SEARCH RESULTS", result) { it.toString() }
    }

    private fun handleUpdateStatusRoom() {
        val id = input.prompt("Enter Room ID")
        val isAvailable = input.promptBoolean("Is Available? (true/false)")
        if (isAvailable == null) {
            output.printError("Invalid input! Please enter true or false.")
            return
        }

        if (roomService.updateRoomAvailability(id, isAvailable))
            output.printSuccess("Room status updated successfully.")
        else
            output.printError("Room not found!")
    }

    private fun handleDeleteRoom() {
        val id = input.prompt("Enter Room ID")
        if (roomService.deleteRoom(id))
            output.printSuccess("Room deleted successfully.")
        else
            output.printError("Room not found!")
    }
}
