package org.m0skit0.android.ikeachallenge.data.repository.mock

internal object MockCart {

    private val items: MutableMap<String, Int> = mutableMapOf()

    fun addItem(id: String) {
        items.getOrPut(id, { 0 }).inc().let { items.put(id, it) }
    }
}