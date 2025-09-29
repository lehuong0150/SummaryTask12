package com.example.summarytask12.model.users

abstract class Person(
    val id: String,
    var name: String,
    var email: String,
    var phone: String? = null
) {
    abstract fun getRole(): String
    abstract fun getDisplayInfo()
    abstract fun validateInfo():Boolean
}