package com.example.summarytask12.utils

import java.util.*

class InputHandler {
    private val scanner = Scanner(System.`in`)

    fun prompt(message: String): String {
        print(message)
        return scanner.nextLine().trim()
    }

    fun promptInt(message: String): Int? {
        print(message)
        return scanner.nextLine().trim().toIntOrNull()
    }

    fun promptDouble(message: String): Double? {
        print(message)
        return scanner.nextLine().trim().toDoubleOrNull()
    }

    fun promptBoolean(message: String): Boolean? {
        print(message)
        return scanner.nextLine().trim().toBooleanStrictOrNull()
    }
}