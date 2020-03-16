package com.example.theweatherproject.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.theweatherproject.R
import com.example.theweatherproject.data.User
import kotlinx.android.synthetic.main.row.view.*

class MyUserRecyclerViewAdapter (
    private val mValues: List<User>) : RecyclerView.Adapter<MyUserRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyUserRecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = mValues.size

    override fun onBindViewHolder(holder: MyUserRecyclerViewAdapter.ViewHolder, position: Int) {
        val item = mValues[position]
        holder.textViewN.text = item.nombre
        holder.textViewC.text = item.clima
    }

    inner class ViewHolder (val mView: View) : RecyclerView.ViewHolder(mView){
        val textViewN : TextView = mView.textViewName
        val textViewC : TextView = mView.textViewClimate
    }
}