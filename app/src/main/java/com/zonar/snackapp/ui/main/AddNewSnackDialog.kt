package com.zonar.snackapp.ui.main

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.ToggleButton
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.zonar.snackapp.R
import com.zonar.snackapp.model.Snack

private const val TAG = "AddNewSnackDialog"

class AddNewSnackDialog : DialogFragment(), CompoundButton.OnCheckedChangeListener {

    private var callback: AddSnackDialogClickListener? = null

    companion object {
        fun newInstance() = AddNewSnackDialog()
    }

    private val viewModel: SnackViewModel by lazy {
        ViewModelProviders.of(this).get(SnackViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        val deviceWidth = context!!.resources.displayMetrics.widthPixels
        dialog?.window?.setLayout(deviceWidth, ViewGroup.LayoutParams.WRAP_CONTENT)

        callback = try {
            targetFragment as AddSnackDialogClickListener?
        } catch (e: ClassCastException) {
            throw ClassCastException("Calling fragment must implement AddSnackDialogClickListener interface")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.add_new_snack_dialog, container, false)
        val newSnackNameView = view.findViewById<TextView>(R.id.new_snack_name)
        val isVeggieView = view.findViewById<ToggleButton>(R.id.veggie_non_veggie)

        isVeggieView.setOnCheckedChangeListener(this)

        view.findViewById<Button>(R.id.new_snack_save).setOnClickListener {
            val name = newSnackNameView.text.toString()
            val isVeggie = isVeggieView.isChecked

            val snack = Snack(name, isVeggie)
            callback?.onSaveNewSnackClick(snack)
            dialog!!.dismiss()
        }

        view.findViewById<Button>(R.id.dialog_cancel_button).setOnClickListener {
            callback?.onCancelNewSnackClick()
            dialog!!.dismiss()
        }

        return view
    }

    override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
        when (p0?.id) {
            R.id.veggie_non_veggie -> changeTextColor(p0)
            else -> Log.i(TAG, "Toggle Button State is checked? $p1")
        }
    }

    private fun changeTextColor(compoundButton: CompoundButton) {
        if (compoundButton.isChecked) {
            compoundButton.setTextColor(resources.getColor(R.color.dark_green))
        } else {
            compoundButton.setTextColor(Color.RED)
        }
    }

}