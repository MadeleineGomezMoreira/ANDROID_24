package com.example.bus_api_client_xml.framework.stop

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bus_api_client_xml.R
import com.example.bus_api_client_xml.databinding.LineViewBinding
import com.example.bus_api_client_xml.domain.model.BusLine

class LineAdapter(val context: Context, val actions: LineActions) :
    ListAdapter<BusLine, LineAdapter.ItemViewHolder>(DiffCallback()) {

    fun interface LineActions {
        fun itemWasClicked(line: BusLine)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.line_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)
    }


    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = LineViewBinding.bind(itemView)

        fun bind(item: BusLine) {
            with(binding) {
                textName.text = item.id.toString()
                itemView.setOnClickListener {
                    actions.itemWasClicked(item)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<BusLine>() {
        override fun areItemsTheSame(oldItem: BusLine, newItem: BusLine): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BusLine, newItem: BusLine): Boolean {
            return oldItem == newItem
        }
    }
}
