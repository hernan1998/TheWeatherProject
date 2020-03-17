package com.example.theweatherproject.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.theweatherproject.R
import com.example.theweatherproject.data.User
import com.example.theweatherproject.data.forecast
import com.example.theweatherproject.databinding.RowBinding

class CityAdapter (private val mValues: List<User>) : RecyclerView.Adapter<CityAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityAdapter.ViewHolder {
        var binder : RowBinding
        binder = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.row, parent, false)
        return ViewHolder(binder)
    }

    override fun getItemCount(): Int = mValues.size

    override fun onBindViewHolder(holder: CityAdapter.ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mView.user = item
        holder.mView.executePendingBindings()
    }

    inner class ViewHolder (val mView: RowBinding) : RecyclerView.ViewHolder(mView.root){
    }
}