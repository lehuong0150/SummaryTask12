package com.example.summarytask12.repository

open class BaseRepository<T>(
    val data: MutableList<T> = mutableListOf()
) {
    fun add(item: T): Boolean = data.add(item)

    fun getAll(): List<T> = data.toList()

    fun findBy(predicate: (T) -> Boolean): T? = data.find(predicate)
}