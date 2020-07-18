package com.zonar.snackapp.model

import java.util.*

data class Snack(var name: String, var isVeggie: Boolean) {
    val id: UUID = UUID.randomUUID()
    var selected = false
}