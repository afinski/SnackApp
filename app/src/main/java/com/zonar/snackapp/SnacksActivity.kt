package com.zonar.snackapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.zonar.snackapp.ui.main.AddNewSnackDialog
import com.zonar.snackapp.ui.main.SnackViewModel
import com.zonar.snackapp.ui.main.SnacksListFragment

class SnacksActivity : AppCompatActivity() {

    private val viewModel: SnackViewModel by lazy {
        ViewModelProviders.of(this).get(SnackViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SnacksListFragment.newInstance(), "SnacksListFragment")
                .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_new_snack -> {
                showNewSnackDialog()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }

    private fun showNewSnackDialog() {
        val dialogFragment = AddNewSnackDialog.newInstance()
        dialogFragment.setTargetFragment(
            supportFragmentManager.findFragmentByTag("SnacksListFragment"),
            1
        )
        dialogFragment.show(
            this.supportFragmentManager,
            "AddNewSnackDialog"
        )

    }
}