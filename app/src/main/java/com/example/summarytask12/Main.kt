package com.example.summarytask12

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.summarytask12.utils.DataInitializer

@RequiresApi(Build.VERSION_CODES.O)
fun main() {
    DataInitializer.initialize()
    val app = HotelBookingConsole()
    app.showMainMenu()
}