package com.example.bus_api_client_xml.framework.line

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bus_api_client_xml.R
import com.example.bus_api_client_xml.databinding.StopViewBinding
import com.example.bus_api_client_xml.domain.model.BusStop

class StopAdapter(val context: Context, val actions: StopActions) :
    ListAdapter<BusStop, StopAdapter.ItemViewHolder>(DiffCallback()) {

    fun interface StopActions {
        fun itemWasClicked(stop: BusStop)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.stop_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)
    }


    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = StopViewBinding.bind(itemView)

        fun bind(item: BusStop) {
            with(binding) {
                textName.text = item.name
                itemView.setOnClickListener {
                    actions.itemWasClicked(item)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<BusStop>() {
        override fun areItemsTheSame(oldItem: BusStop, newItem: BusStop): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BusStop, newItem: BusStop): Boolean {
            return oldItem == newItem
        }
    }
}
