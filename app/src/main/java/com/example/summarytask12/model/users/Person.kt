package com.example.summarytask12.model.users

abstract class Person(
    val id: String,
    val name: String,
    val email: String,
    val phone: String? = null
) {
    abstract fun getRole(): String
    abstract fun getDisplayInfo()

    protected abstract fun validateInfo():Boolean
}