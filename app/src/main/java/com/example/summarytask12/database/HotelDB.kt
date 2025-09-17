package com.example.summarytask12.database

import android.util.Log
import com.example.summarytask12.model.Booking
import com.example.summarytask12.model.accommodation.DeluxeRoom
import com.example.summarytask12.model.accommodation.Room
import com.example.summarytask12.model.accommodation.StandardRoom
import com.example.summarytask12.model.accommodation.SuiteRoom
import com.example.summarytask12.model.users.Customer
import com.example.summarytask12.model.users.VIPCustomer
import com.example.summarytask12.utils.BookingStatus

object HotelDB {
    private val customerList = mutableListOf<Customer>()
    private val roomList = mutableListOf<Room>()
    private val bookingList = mutableListOf<Booking>()

    init {
        initializeSampleData()
    }
    private fun initializeSampleData(){
        //list room
        addRoom( StandardRoom("R001", 800000.0, true))
        addRoom(StandardRoom("R002", 850000.0, true))
        addRoom(DeluxeRoom("R003", 1200000.0, isAvailable = true, hasOceanView = true)) // with ocean view
        addRoom(DeluxeRoom("R004", 1100000.0, isAvailable = false, hasOceanView = false))
        addRoom(SuiteRoom("R005", 2000000.0, false))
        addRoom(StandardRoom("R006", 850000.0, false))

        //list customer
        addCustomer(Customer("C001", "Nguyễn Văn Hải", "vana@email.com", "0901234567", "BASIC"))
        addCustomer(VIPCustomer("C002", "Trần Thị Nga", "thib@vip.com", "0907654321", 0))
        addCustomer(Customer("C003", "Lê Ngọc Linh", "linh@email.com", "0905634567", "BASIC"))
        addCustomer(VIPCustomer("C004", "Phan Đức Thanh Duy", "duy@vip.com", "0907654321", 0))
        addCustomer(Customer("C005", "Trần Đình Hưng", "hung@email.com", "0945632507", "PREMIUM"))
        addCustomer(Customer("C006", "Võ Tiến Khoa", "khoa@email.com", "0925634169", "PREMIUM"))

        //list Booking
        addBooking(Booking("B001", "COO1", "R004", "2025-09-17", 2, "CONFIRMED", "CARD" ))
        addBooking(Booking("B002", "C006", "R005", "2025-09-16", 3, "CONFIRMED", "CARD"))
        addBooking(Booking("B003", "COO2", "R006", "2025-09-16", 4, "CONFIRMED", "CARD"))



    }

    //CRUD
    private fun addCustomer(customer: Customer?):Boolean
    {
        return if (customer != null  ) {
            if(!customerList.any { it.id == customer.id })
            {
                customerList.add(customer) // add() trả về Boolean luôn
            }else
            {
                //Log.d("Error", "addCustomer: Already exists" )
                false
            }
        } else {
            //Log.d("Error", "addCustomer: Customer Null")
            false
        }
    }

    fun getCustomer(id : String): Customer? {
        return customerList.find { it.id == id }
    }

    private fun addRoom(room: Room?): Boolean{
        return if (room != null  ) {
            if(!roomList.any { it.id == room.id })
            {
                roomList.add(room) // add() trả về Boolean luôn
            }else
            {
                //Log.d("Error", "addCRoom: Already exists" )
                false
            }
        } else {
            //Log.d("Error", "addRoom: Room Null")
            false
        }
    }

    fun getRoom(id: String): Room?{
        return roomList.find { it.id == id }
    }

    private fun addBooking(booking: Booking?): Boolean{
        return if (booking != null  ) {
            if(!bookingList.any { it.id == booking.id })
            {
                bookingList.add(booking) // add() trả về Boolean luôn
            }else
            {
                //Log.d("Error", "addBooking: Already exists" )
                return false
            }
        } else {
            //Log.d("Error", "addBooking: Booking Null")
            false
        }
    }

    fun getBooking(id: String): Booking?{
        return bookingList.find { it.id == id }
    }

    fun getCustomerList(): MutableList<Customer>{
        return customerList
    }

    fun getRoomList() : MutableList<Room>{
        return roomList
    }

    fun getBookingList() : MutableList<Booking>{
        return bookingList
    }
}