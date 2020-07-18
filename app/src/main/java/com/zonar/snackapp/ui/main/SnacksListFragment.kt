package com.zonar.snackapp.ui.main

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zonar.snackapp.R
import com.zonar.snackapp.model.Snack

private const val TAG = "SnacksListFragment"

interface DialogClickListener {
    fun onSubmitClick()
    fun onCancelClick()
}

interface AddSnackDialogClickListener {
    fun onSaveNewSnackClick(snack: Snack)
    fun onCancelNewSnackClick()
}

class SnacksListFragment : Fragment(), View.OnClickListener, DialogClickListener,
    AddSnackDialogClickListener {

    private lateinit var snackRecyclerView: RecyclerView
    private lateinit var submitButton: Button
    private lateinit var veggieCheckBox: CheckBox
    private lateinit var nonVeggieCheckBox: CheckBox
    private var adapter: SnackAdapter? = SnackAdapter(emptyList())

    companion object {
        fun newInstance() = SnacksListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        snackRecyclerView = view.findViewById(R.id.snacks_recycler_view)
        snackRecyclerView.layoutManager = LinearLayoutManager(context)
        snackRecyclerView.adapter = adapter

        submitButton = view.findViewById(R.id.submit_button)
        veggieCheckBox = view.findViewById(R.id.veggie_checkbox)
        nonVeggieCheckBox = view.findViewById(R.id.non_veggie_checkbox)

        return view
    }

    override fun onStart() {
        super.onStart()

        veggieCheckBox.setOnCheckedChangeListener { _, isChecked ->
            viewModel.isVeggieSelected = isChecked
            Log.d(TAG, " veggieCheckBox  isChecked: $isChecked")
            updateUI(viewModel.snackListLiveData.value)
        }

        nonVeggieCheckBox.setOnCheckedChangeListener { _, isChecked ->
            viewModel.isNonVeggieSelected = isChecked
            Log.d(TAG, " nonVeggieCheckBox  isChecked: $isChecked")
            updateUI(viewModel.snackListLiveData.value)
        }

        submitButton.setOnClickListener(this)
    }

    private val viewModel: SnackViewModel by lazy {
        ViewModelProviders.of(this).get(SnackViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.snackListLiveData.observe(
            viewLifecycleOwner,
            Observer { snacks ->
                snacks?.let {
                    Log.i(TAG, "Got snacks ${snacks.size}")
                    updateUI(snacks)
                }
            }
        )

    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume ")
    }

    private fun updateUI(snacks: List<Snack>?) {
        adapter = if (snacks != null && snacks.isNotEmpty()) {
            var snacksMutable = snacks.toMutableList()

            if (!viewModel.isVeggieSelected) {
                snacksMutable.removeAll { it.isVeggie }
            }
            if (!viewModel.isNonVeggieSelected) {
                snacksMutable.removeAll { !it.isVeggie }
            }

            SnackAdapter(snacksMutable)
        } else {
            SnackAdapter(snacks)
        }
        snackRecyclerView.adapter = adapter
    }

    private inner class SnackHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        CompoundButton.OnCheckedChangeListener {
        private lateinit var snack: Snack
        private val snackCheckBox: CheckBox = itemView.findViewById(R.id.checked_snack)

        init {
            snackCheckBox.setOnCheckedChangeListener(this)
        }

        fun bind(snack: Snack) {
            this.snack = snack
            snackCheckBox.text = this.snack.name
            snackCheckBox.isChecked = this.snack.selected
            if (this.snack.isVeggie) {
                snackCheckBox.setTextColor(resources.getColor(R.color.dark_green))
            } else {
                snackCheckBox.setTextColor(Color.RED)
            }
        }

        override fun onCheckedChanged(itemSnack: CompoundButton?, checked: Boolean) {
            Log.d(TAG, "onCheckedChanged")

            this.snack.selected = checked
        }

    }

    private inner class SnackAdapter(var snacks: List<Snack>?) :
        RecyclerView.Adapter<SnackHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SnackHolder {
            val view = layoutInflater.inflate(R.layout.snack_list_item, parent, false)
            return SnackHolder(view)
        }

        override fun getItemCount(): Int {
            return snacks!!.size
        }

        override fun onBindViewHolder(holder: SnackHolder, position: Int) {
            val snack = snacks!![position]
            holder.bind(snack)
        }

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.submit_button -> {
                showOrderDialog()
            }
            else -> {
            }
        }
    }

    private fun showOrderDialog() {
        if (viewModel.somethingSelected()) {
            val dialogFragment = SnackOrderSummaryDialog.newInstance()
            dialogFragment.setTargetFragment(this, 1)
            dialogFragment.show(
                (context as FragmentActivity).supportFragmentManager,
                "SnackOrderSummaryDialog"
            )
        }
    }

    override fun onSubmitClick() {
        viewModel.deselectAll()
        updateUI(viewModel.snackListLiveData.value)
    }

    override fun onCancelClick() {
        viewModel.deselectAll()
        updateUI(viewModel.snackListLiveData.value)
    }

    override fun onSaveNewSnackClick(snack: Snack) {
        viewModel.addSnack(snack)
        updateUI(viewModel.snackListLiveData.value)
    }

    override fun onCancelNewSnackClick() {

    }

}


