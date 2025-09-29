package com.example.summarytask12.repository

import com.example.summarytask12.model.accommodation.Room

class RoomRepository {
    private val rooms = mutableListOf<Room>()

    fun addRoom(room: Room): Boolean =
        rooms.none { it.id == room.id }
            .also { canAdd -> if (canAdd) rooms.add(room) }

    fun getAllRooms(): List<Room> = rooms.toList()

    fun getRoomById(id: String): Room? =
        rooms.find { it.id == id }

    fun updateRoomAvailability(roomId: String, isAvailable: Boolean): Boolean =
        getRoomById(roomId)?.apply {
            this.isAvailable = isAvailable
        }?.let { true } ?: false

    fun deleteRoom(roomId: String): Boolean =
        rooms.removeIf { it.id == roomId }
}

