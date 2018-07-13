package com.past3.ketro.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.vehicle_list_item.view.*
import test.smile.lobby.R
import test.smile.lobby.model.GenericVehicleContainer

class VehicleAdapter(val modelList: List<GenericVehicleContainer>,
                     val showKey: Boolean,
                     val clickListener: VehicleClickListener) : RecyclerView.Adapter<VehicleAdapter.Companion.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return LayoutInflater.from(parent.context).inflate(R.layout.vehicle_list_item, parent, false).run {
            ViewHolder(this, clickListener)
        }
    }

    override fun getItemCount() = modelList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = modelList[position]
        holder.valueItem.text = model.valueItem
        holder.proceedButton.tag = model
    }

    companion object {
        class ViewHolder(view: View, clickListener: VehicleClickListener) : RecyclerView.ViewHolder(view) {
            val valueItem: TextView by lazy {
                view.valueItem
            }

            val bottomContent: View by lazy {
                view.decisionContent
            }

            val cancelButton: View by lazy {
                view.cancel
            }

            val proceedButton: View by lazy {
                view.confirm
            }

            init {
                view.setOnClickListener {
                    bottomContent.visibility = View.VISIBLE
                }

                cancelButton.setOnClickListener {
                    bottomContent.visibility = View.GONE
                }

                proceedButton.setOnClickListener {
                    val items = it.tag as GenericVehicleContainer
                    clickListener.onClick(items)
                }
            }
        }

        interface VehicleClickListener {
            fun onClick(data: GenericVehicleContainer)
        }
    }
}