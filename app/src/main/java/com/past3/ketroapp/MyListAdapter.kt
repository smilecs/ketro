package com.past3.ketroapp

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item.view.*

class MyListAdapter : ListAdapter<ResponseModel.Items, MyListAdapter.MyViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(model: ResponseModel.Items) {
            itemView.textView.text = model.login
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ResponseModel.Items>() {

        override fun areItemsTheSame(oldItem: ResponseModel.Items, newItem: ResponseModel.Items): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ResponseModel.Items, newItem: ResponseModel.Items): Boolean {
            return oldItem == newItem
        }
    }
}