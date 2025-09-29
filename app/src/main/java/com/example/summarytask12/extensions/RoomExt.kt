package com.example.summarytask12.extensions

import com.example.summarytask12.model.accommodation.Room

fun List<Room>.filterByType(type: String): List<Room> =
    filter { it.type.equals(type, ignoreCase = true) }

fun List<Room>.filterByPriceRange(min: Double, max: Double): List<Room> =
    filter { it.price in min..max }

fun List<Room>.availableOnly(): List<Room> =
    filter { it.isAvailable }

fun List<Room>.sortByPrice(): List<Room> =
    sortedBy { it.price }
