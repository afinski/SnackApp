package com.zonar.snackapp.ui.main

import androidx.lifecycle.ViewModel
import com.zonar.snackapp.model.Snack
import com.zonar.snackapp.repository.SnackRepository

class SnackViewModel : ViewModel() {
    private val snackRepository = SnackRepository.get()
    val snackListLiveData = snackRepository.getSnacks()
    var isVeggieSelected = true
    var isNonVeggieSelected = true

    fun deselectAll() {
        snackRepository.deselectAll()
    }

    fun somethingSelected(): Boolean {
        return snackRepository.somethingSelected()
    }

    fun addSnack(snack: Snack) {
        return snackRepository.addSnack(snack)
    }

}