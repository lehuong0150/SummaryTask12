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
import com.example.summarytask12.utils.Message

class RoomController(
    private val roomService: RoomService,
    private val input: InputHandler = InputHandler(),
    private val output: OutputHandler = OutputHandler()
) {

    fun showMenu() {
        while (true) {
            output.printHeader(Message.ROOM_HEADER)
            output.printMessage("1. View all rooms")
            output.printMessage("2. Add new room")
            output.printMessage("3. Find room by ID")
            output.printMessage("4. Search room")
            output.printMessage("5. Update room status")
            output.printMessage("6. Delete room")
            output.printMessage("0. Back")

            when (input.prompt("Choose: ")) {
                "1" -> handleViewAllRooms()
                "2" -> handleCreateRoom()
                "3" -> handleFindRoomById()
                "4" -> handleSearchRooms()
                "5" -> handleUpdateStatusRoom()
                "6" -> handleDeleteRoom()
                "0" -> return
                else -> output.printError(Message.INVALID_CHOICE)
            }
        }
    }

    private fun handleViewAllRooms() {
        val rooms = roomService.getAllRooms()
        if (rooms.isEmpty()) output.printMessage(Message.NO_DATA)
        else output.printList("ALL ROOMS: ", rooms) { it.toString() }
    }

    private fun handleCreateRoom() {
        output.printSubHeader("Add Room")

        val idRoom = input.prompt(Message.ROOM_ENTER_ID)
        val numberRoom = input.prompt(Message.ROOM_ENTER_NUMBER)
        val typeRoom = input.prompt(Message.ROOM_ENTER_TYPE).uppercase()
        val priceRoom = input.promptDouble(Message.ENTER_PRICE) ?: run {
            output.printError(Message.INPUT_ERROR)
            return
        }
        val isAvailable = input.promptBoolean(Message.ENTER_STATUS) ?: true

        val newRoom = when (typeRoom) {
            "STANDARD" -> StandardRoom(idRoom, numberRoom, priceRoom, isAvailable)
            "DELUXE" -> {
                val hasOceanView = input.promptBoolean("Has Ocean View? (true/false)") ?: false
                DeluxeRoom(idRoom, numberRoom, priceRoom, isAvailable, hasOceanView)
            }

            "SUITE" -> SuiteRoom(idRoom, numberRoom, priceRoom, isAvailable)
            else -> {
                output.printError(Message.INPUT_ERROR)
                return
            }
        }

        if (roomService.addRoom(newRoom))
            output.printSuccess(Message.ROOM_CREATED)
        else
            output.printError(Message.ACTION_FAILED)
    }

    private fun handleFindRoomById() {
        val id = input.prompt(Message.ENTER_ID)
        val room = roomService.getRoom(id)
        if (room != null)
            output.printMessage(room.toString())
        else
            output.printError(Message.ROOM_NOT_FOUND)
    }

    private fun handleSearchRooms() {
        output.printSubHeader("Search Rooms")

        val typeRoom = input.prompt(Message.ROOM_ENTER_TYPE)
            .takeIf { it.isNotBlank() }?.uppercase()
        val minPrice = input.promptDouble("Min Price (VND)") ?: 0.0
        val maxPrice = input.promptDouble("Max Price (VND)") ?: Double.MAX_VALUE

        val result = roomService.getAllRooms()
            .availableOnly()
            .filterByPriceRange(minPrice, maxPrice)
            .let { if (typeRoom != null) it.filterByType(typeRoom) else it }
            .sortByPrice()

        if (result.isEmpty()) output.printMessage(Message.NO_DATA)
        else output.printList("SEARCH RESULTS", result) { it.toString() }
    }

    private fun handleUpdateStatusRoom() {
        val id = input.prompt(Message.ENTER_ID)
        val isAvailable = input.promptBoolean(Message.ENTER_STATUS)
        if (isAvailable == null) {
            output.printError(Message.INPUT_ERROR)
            return
        }

        if (roomService.updateRoomAvailability(id, isAvailable))
            output.printSuccess(Message.ROOM_UPDATED)
        else
            output.printError(Message.ROOM_NOT_FOUND)
    }

    private fun handleDeleteRoom() {
        val id = input.prompt(Message.ENTER_ID)
        if (roomService.deleteRoom(id))
            output.printSuccess(Message.ROOM_DELETED)
        else
            output.printError(Message.ROOM_NOT_FOUND)
    }
}
