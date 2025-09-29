package com.example.summarytask12.services

import com.example.summarytask12.model.accommodation.Room
import com.example.summarytask12.repository.RoomRepository

class RoomService(private val roomRepo: RoomRepository) {

    fun addRoom(room: Room): Boolean {
        if (roomRepo.addRoom(room)) {
            println("Room added: ${room.id}")
            return true
        }
        return false
    }

    fun getRoom(id: String): Room? =
        roomRepo.getRoomById(id)

    fun getAllRooms(): List<Room> =
        roomRepo.getAllRooms()

    fun updateRoomAvailability(id: String, isAvailable: Boolean): Boolean =
        roomRepo.updateRoomAvailability(id, isAvailable)

    fun deleteRoom(id: String): Boolean =
        roomRepo.deleteRoom(id)
}