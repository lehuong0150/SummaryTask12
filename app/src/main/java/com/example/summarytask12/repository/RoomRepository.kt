package com.example.summarytask12.repository

import com.example.summarytask12.model.accommodation.Room

class RoomRepository : BaseRepository<Room>() {

    fun addRoom(room: Room): Boolean =
        data.none { it.id == room.id }
            .also { canAdd ->
                if (canAdd) data.add(room)
            }

    fun getAllRooms(): List<Room> = getAll()

    fun getRoomById(id: String): Room? = findBy { it.id == id }

    fun updateRoomAvailability(roomId: String, isAvailable: Boolean): Boolean =
        findBy { it.id == roomId }?.let {
            it.isAvailable = isAvailable
            true
        } ?: false

    fun deleteRoom(roomId: String): Boolean =
        data.removeIf { it.id == roomId }
}
