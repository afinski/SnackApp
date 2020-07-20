package com.zonar.snackapp.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zonar.snackapp.model.Snack
import java.util.*

private const val TAG = "SnackRepository"

class SnackRepository private constructor(context: Context) {

    private var snacksOfRepo = mutableListOf<Snack>()

    fun getSnacks(): LiveData<List<Snack>> = getSnacksDummy()

    fun getSnack(id: UUID): LiveData<Snack?> = getSnackDummy(id)

    //TODO here should be an implementation of a network service for download/upload data
    // inastead of 'getSnacksDummy()'
    // something like this:

//    val retrofit: Retrofit = Retrofit.Builder()
//        .baseUrl("https://www.snack.com/")
//        .addConverterFactory(ScalarsConverterFactory.create())
//        .build()
//
//    val snackApi: SnackApi = retrofit.create(SnackApi::class.java)
//    val snackHomePageRequest: Call<String> = snackApi.fetchContents()
//
//    snackHomePageRequest.enqueue(object: Callback<String> {
//        override fun onFailure(call: Call<String>, t: Throwable) {
//            Log.e(TAG,"Failed to fetch snacks ", t)
//        }
//
//        override fun onResponse(call: Call<String>, response: Response<String>) {
//            Log.d(TAG, "Response received: ${response.body()}")
//        }
//
//    })

    fun updateSnack(snack: Snack) {
        // TODO
    }

    fun addSnack(snack: Snack) {
        snacksOfRepo.add(snack)
    }

    companion object {
        private var INSTANCE: SnackRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = SnackRepository(context)
            }
        }

        fun get(): SnackRepository {
            return INSTANCE ?: throw IllegalStateException("SnackRepository must be initialized")
        }
    }

    private fun getSnackDummy(id: UUID): LiveData<Snack?> {
        val snack: MutableLiveData<Snack> by lazy {
            MutableLiveData<Snack>()
        }

        snack.value = getSnackById(id)

        return snack
    }

    private fun getSnackById(id: UUID): Snack? {
        val snacks = getListOfSnack()
        val size = snacks.size - 1
        for (i in 0..size) {
            if (id == snacks[i].id) {
                return snacks[i]
            }
        }
        return null
    }


    private fun getSnacksDummy(): LiveData<List<Snack>> {
        val snacks: MutableLiveData<List<Snack>> by lazy {
            MutableLiveData<List<Snack>>()
        }
        snacks.value = getListOfSnack()
        return snacks
    }

    private fun getListOfSnack(): List<Snack> {
        if (snacksOfRepo.size < 1) {
            val listOfSnack = mutableListOf<Snack>()
            listOfSnack.add(Snack("French fries", true))
            listOfSnack.add(Snack("Veggieburger", true))
            listOfSnack.add(Snack("Carrots", true))
            listOfSnack.add(Snack("Apple", true))
            listOfSnack.add(Snack("Banana", true))
            listOfSnack.add(Snack("Milkshake", true))

            listOfSnack.add(Snack("Cheeseburger", false))
            listOfSnack.add(Snack("Hamburger", false))
            listOfSnack.add(Snack("Hot dog", false))
            snacksOfRepo = listOfSnack
        }
        return snacksOfRepo
    }

    fun deselectAll() {
        snacksOfRepo.forEach { it.selected = false }
    }

    fun somethingSelected(): Boolean {
        return snacksOfRepo.any { it.selected }
    }

}