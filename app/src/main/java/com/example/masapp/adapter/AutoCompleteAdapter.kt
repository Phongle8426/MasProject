package com.example.masapp.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.example.masapp.R

/*
///
/// Project: MasApp
/// Created by pc on 12/06/2021.
///
*/class AutoCompleteAdapter(context: Activity, resourceId: Int, private val wards: List<String>) :
    ArrayAdapter<String>(context, resourceId, wards) {
    private var inflater: LayoutInflater = context.layoutInflater
    private lateinit var view: View

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        if(convertView == null)
            view = inflater.inflate(R.layout.drop_item, parent, false)
        view.findViewById<TextView>(R.id.textViewWard).apply {
            this.text = getItem(position)
        }
        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val listWard = mutableListOf<String>()
                listWard.addAll(wards)
                return FilterResults().apply {
                    values = listWard
                    count = listWard.size
                }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                clear()
                addAll(results?.values as List<String>)
                notifyDataSetChanged()
            }

            override fun convertResultToString(resultValue: Any?): CharSequence {
                return (resultValue as String)
            }
        }
    }
}