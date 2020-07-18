package com.zonar.snackapp

import android.app.Application
import com.zonar.snackapp.repository.SnackRepository

class SnackApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        SnackRepository.initialize(this)
    }

}