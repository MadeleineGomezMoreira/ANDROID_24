package com.example.bus_api_client_xml.framework.drivers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bus_api_client_xml.R
import com.example.bus_api_client_xml.databinding.ItemViewBinding
import com.example.bus_api_client_xml.databinding.LineViewBinding
import com.example.bus_api_client_xml.domain.model.BusDriver
import com.example.bus_api_client_xml.domain.model.BusLine

class DriverAdapter(val context: Context, val actions: LineActions) :
    ListAdapter<BusDriver, DriverAdapter.ItemViewHolder>(DiffCallback()) {

    fun interface LineActions {
        fun itemWasClicked(driver: BusDriver)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)
    }


    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemViewBinding.bind(itemView)

        fun bind(item: BusDriver) {
            with(binding) {
                textFirstName.text = item.firstName
                textLastName.text = item.lastName
                itemView.setOnClickListener {
                    actions.itemWasClicked(item)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<BusDriver>() {
        override fun areItemsTheSame(oldItem: BusDriver, newItem: BusDriver): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BusDriver, newItem: BusDriver): Boolean {
            return oldItem == newItem
        }
    }
}
