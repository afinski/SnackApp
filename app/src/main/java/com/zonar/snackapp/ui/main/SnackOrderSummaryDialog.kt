package com.zonar.snackapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zonar.snackapp.R
import com.zonar.snackapp.model.Snack

class SnackOrderSummaryDialog : DialogFragment() {

    private var callback: DialogClickListener? = null

    companion object {
        fun newInstance() = SnackOrderSummaryDialog()
    }

    private val viewModel: SnackViewModel by lazy {
        ViewModelProviders.of(this).get(SnackViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        val deviceWidth = context!!.resources.displayMetrics.widthPixels
        dialog?.window?.setLayout(deviceWidth, ViewGroup.LayoutParams.WRAP_CONTENT)

        callback = try {
            targetFragment as DialogClickListener?
        } catch (e: ClassCastException) {
            throw ClassCastException("Calling fragment must implement DialogClickListener interface")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.order_recycler_view, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.order_list_recycler_view)

        recyclerView.layoutManager = LinearLayoutManager(context)

        val snacks = getSelectedSnacks(viewModel.snackListLiveData.value)

        val adapter = OrderSnackAdapter(snacks)
        recyclerView.adapter = adapter

        view.findViewById<Button>(R.id.dialog_submit_button).setOnClickListener {
            callback?.onSubmitClick()
            dialog!!.dismiss()
        }

        view.findViewById<Button>(R.id.dialog_cancel_button).setOnClickListener {
            callback?.onCancelClick()
            dialog!!.dismiss()
        }

        return view
    }

    private fun getSelectedSnacks(snacks: List<Snack>?): List<Snack>? {
        if (snacks != null && snacks.isNotEmpty()) {
            var snacksMutable = snacks.toMutableList()
            snacksMutable.removeAll { !it.selected }
            return snacksMutable
        }
        return null
    }

    private inner class OrderSnackHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var snack: Snack
        private val snackTitle: TextView = itemView.findViewById(R.id.ordered_snack)

        fun bind(snack: Snack) {
            this.snack = snack
            snackTitle.text = this.snack.name
        }
    }

    private inner class OrderSnackAdapter(var snacks: List<Snack>?) :
        RecyclerView.Adapter<SnackOrderSummaryDialog.OrderSnackHolder>() {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): SnackOrderSummaryDialog.OrderSnackHolder {
            val view = layoutInflater.inflate(R.layout.snack_order_list_item, parent, false)
            return OrderSnackHolder(view)
        }

        override fun getItemCount(): Int {
            return snacks!!.size
        }

        override fun onBindViewHolder(
            holder: SnackOrderSummaryDialog.OrderSnackHolder,
            position: Int
        ) {
            val snack = snacks!![position]
            holder.bind(snack)
        }

    }

}