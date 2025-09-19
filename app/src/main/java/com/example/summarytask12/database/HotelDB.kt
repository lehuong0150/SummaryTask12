package com.example.summarytask12.database


import com.example.summarytask12.model.Booking
import com.example.summarytask12.model.accommodation.DeluxeRoom
import com.example.summarytask12.model.accommodation.Room
import com.example.summarytask12.model.accommodation.StandardRoom
import com.example.summarytask12.model.accommodation.SuiteRoom
import com.example.summarytask12.model.users.Customer
import com.example.summarytask12.model.users.VIPCustomer
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


object HotelDB {
    private val customerList = mutableListOf<Customer>()
    private val roomList = mutableListOf<Room>()
    private val bookingList = mutableListOf<Booking>()

    init {
        initializeSampleData()
    }

    private fun initializeSampleData() = runBlocking() {
        //list room
        initInfoRoom()

        //list customer
        initInfoCustomer()

        //list Booking
        initInfoBooking()


    }

    private suspend fun initInfoCustomer() {
        addCustomer(Customer("C001", "Nguyễn Văn Hải", "vana@email.com", "0901234567", "BASIC"))
        addCustomer(
            VIPCustomer(
                "C002", "Trần Thị Nga", "thib@vip.com", "0907654321", mutableListOf(
                    "Phòng tầng cao view biển",
                    "Late checkout 3PM",
                    "Bữa sáng phục vụ tại phòng",
                    "Xe đưa đón sân bay"
                )
            )
        )
        addCustomer(Customer("C003", "Lê Ngọc Linh", "linh@email.com", "0905634567", "BASIC"))
        addCustomer(
            VIPCustomer(
                "C004", "Phan Đức Thanh Duy", "duy@vip.com", "0907654321", mutableListOf(
                    "Late checkout 3PM",
                    "Bữa sáng phục vụ tại phòng",
                    "Xe đưa đón sân bay",
                    "Welcome drink champagne"
                )
            )
        )

        addCustomer(Customer("C005", "Trần Đình Hưng", "hung@email.com", "0945632507", "PREMIUM"))
        addCustomer(Customer("C006", "Võ Tiến Khoa", "khoa@email.com", "0925634169", "PREMIUM"))
    }

    private suspend fun initInfoRoom() {
        delay(500L)
        addRoom(StandardRoom("R001", 800000.0, true))
        addRoom(StandardRoom("R002", 850000.0, true))
        addRoom(
            DeluxeRoom(
                "R003",
                1200000.0,
                isAvailable = true,
                hasOceanView = true
            )
        ) // with ocean view
        addRoom(DeluxeRoom("R004", 1100000.0, isAvailable = false, hasOceanView = false))
        addRoom(SuiteRoom("R005", 2000000.0, false))
        addRoom(StandardRoom("R006", 850000.0, false))
    }

    private suspend fun initInfoBooking() {
        delay(500L)
        addBooking(Booking("B001", "C001", "R004", "2025-09-17", 2, "CONFIRMED", "CASH"))
        addBooking(Booking("B002", "C006", "R005", "2025-09-16", 3, "CONFIRMED", "CASH"))
        addBooking(Booking("B003", "COO2", "R006", "2025-09-16", 4, "CONFIRMED", "CASH"))
    }

    //CRUD
    private fun addCustomer(customer: Customer?): Boolean {
        return if (customer != null && customer.validateInfo()) {
            if (!customerList.any { it.id == customer.id }) {
                customerList.add(customer) // add() trả về Boolean luôn
            } else {
                false
            }
        } else {
            false
        }
    }

    fun getCustomer(id: String): Customer? {
        return customerList.find { it.id == id }
    }

    fun addRoom(room: Room?): Boolean {
        return if (room != null) {
            if (!roomList.any { it.id == room.id }) {
                roomList.add(room) // add() trả về Boolean luôn
            } else {
                false
            }
        } else {
            false
        }
    }

    fun getRoom(id: String): Room? {
        return roomList.find { it.id == id }
    }

    fun addBooking(booking: Booking?): Boolean {
        return if (booking != null) {
            if (!bookingList.any { it.id == booking.id }) {
                bookingList.add(booking)
            } else {
                return false
            }
        } else {
            false
        }
    }

    fun getBooking(id: String): Booking? {
        return bookingList.find { it.id == id }
    }

    fun getCustomerList(): MutableList<Customer> {
        return customerList
    }

    fun getRoomList(): MutableList<Room> {
        return roomList
    }

    fun getBookingList(): MutableList<Booking> {
        return bookingList
    }

    fun updateBooking(booking: Booking) {
        bookingList.indexOfFirst { it.id == booking.id }
            .takeIf { it != -1 }
            ?.let { index ->
                bookingList[index] = booking
                true
            } ?: false
    }

    fun deleteRoom(idRoom: String): Boolean {
        bookingList.indexOfFirst { it.id == idRoom }
            .takeIf { it != -1 }
            ?.let { index ->
                bookingList.removeAt(index)
                return true
            } ?: return false
    }
}