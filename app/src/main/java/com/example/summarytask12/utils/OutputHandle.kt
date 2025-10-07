package com.example.summarytask12.utils

class OutputHandler {

    fun printHeader(title: String) {
        println("\n" + "=".repeat(60))
        println(title.uppercase())
        println("=".repeat(60))
    }

    fun printSubHeader(subTitle: String) {
        println("\n--- $subTitle ---")
    }

    fun printLine() = println("-".repeat(60))

    fun printMessage(message: String) = println(message)

    fun printError(message: String) = println("ERROR: $message")

    fun printSuccess(message: String) = println("$message")

    fun <T> printList(title: String, items: List<T>, mapper: (T) -> String) {
        printHeader(title)
        if (items.isEmpty()) {
            println("(No data found)")
        } else {
            items.map(mapper).forEach(::println)
        }
        printLine()
    }
}
