package com.example.summarytask12.model.users

abstract class Person (
    open val id: String,
    open val name: String,
    open val email : String,
    open val phone: String?= null
){
    abstract fun getRole(): String
    abstract fun getDisplayInfo()

    protected abstract fun validateInfo()
}