package com.example.summarytask12.extensions

import com.example.summarytask12.model.accommodation.Room

fun MutableList<Room>.fillterByType(type: String): List<Room> =
    filter {it.type.equals(type,ignoreCase = true )}