package com.example.masapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.masapp.databinding.ItemFamilyBinding
import com.example.masapp.models.CivilianModel
import com.example.masapp.utils.ItemClick

class FamilyAdapter(private val members: List<CivilianModel>, val listener: ItemClick): RecyclerView.Adapter<FamilyAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemFamilyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(member: CivilianModel) {
            binding.member = member
            binding.layoutItem.setOnClickListener {
                listener.itemClick(member)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFamilyBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(members[position])
    }

    override fun getItemCount(): Int = members.size
}