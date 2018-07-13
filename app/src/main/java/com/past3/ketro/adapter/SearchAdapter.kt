package com.past3.ketro.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import test.smile.lobby.R
import test.smile.lobby.model.GenericVehicleContainer


class SearchAdapter(context: Context, private val textViewResourceId: Int, val mData: ArrayList<GenericVehicleContainer>) : ArrayAdapter<GenericVehicleContainer>(context, textViewResourceId), Filterable {

    override fun getCount(): Int {
        return mData.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return LayoutInflater.from(parent?.getContext()).inflate(textViewResourceId, null).apply {
            val fontTextView = this.findViewById(R.id.text_title) as TextView
            fontTextView.text = getItem(position)!!.valueItem
        }
    }

    override fun getItem(index: Int): GenericVehicleContainer? {
        return mData[index]
    }

    override fun getFilter(): Filter {
        return object : Filter() {

            override fun convertResultToString(resultValue: Any?): CharSequence? {
                return ((resultValue) as GenericVehicleContainer?)?.valueItem
            }

            override fun performFiltering(constraint: CharSequence?): FilterResults? {
                val filterResults = FilterResults()
                val newSuggestions = mutableListOf<GenericVehicleContainer>()
                constraint?.let {
                    for (result in mData) {
                        if (result.valueItem.toLowerCase().contains(it.toString().toLowerCase())) {
                            newSuggestions.add(result)
                        }
                    }
                    filterResults.values = newSuggestions
                    filterResults.count = newSuggestions.size
                }
                return filterResults
            }

            override fun publishResults(contraint: CharSequence?, results: FilterResults?) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged()
                } else {
                    notifyDataSetInvalidated()
                }
            }
        }
    }
}