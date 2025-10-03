package com.example.summarytask12.utils

import java.util.Scanner

class InputHandle(val scanner: Scanner = Scanner(System.`in`)) {
    fun readString(prompt: String, allowEmpty: Boolean = false): String {
        print(prompt)
        val input = scanner.nextLine().trim()
        return if (!allowEmpty && input.isBlank()) {
            println("Input cannot be empty, please try again.")
            readString(prompt, allowEmpty)
        } else {
            input
        }
    }

    fun readStringOrDefault(prompt: String, default: String): String {
        print(prompt)
        val input = scanner.nextLine().trim()
        return input.ifBlank { default }
    }

    fun readInt(prompt: String, min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE): Int? {
        print(prompt)
        return scanner.nextLine().toIntOrNull()?.takeIf { it in min..max }
    }

    fun readDoubleOrDefault(
        prompt: String,
        default: Int,
        min: Int = Int.MIN_VALUE,
        max: Int = Int.MAX_VALUE
    ): Int {
        print(prompt)
        val input = scanner.nextLine().trim()
        return if (input.isBlank()) {
            default
        } else {
            input.toIntOrNull()?.takeIf { it in min..max } ?: default
        }
    }

    fun readDouble(prompt: String, min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE): Double? {
        print(prompt)
        return scanner.nextLine().toDoubleOrNull()
    }
}