package com.example.theweatherproject.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.theweatherproject.R
import com.example.theweatherproject.data.User
import com.example.theweatherproject.databinding.RowBinding
import kotlinx.android.synthetic.main.row.view.*

class UserAdapter (
    private val mValues: List<User>,
    private val mListener: onListInteraction
    ) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserAdapter.ViewHolder {
        //val view = LayoutInflater.from(parent.context).inflate(R.layout.row,parent,false)
        //return ViewHolder(view)
        var binder : RowBinding
        binder = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.row, parent, false)
        return ViewHolder(binder)
    }

    override fun getItemCount(): Int = mValues.size

    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mView.user = item
        holder.mView.executePendingBindings()

        holder.mView.theLayout.textViewName.setOnClickListener{
            mListener?.onListItemInteracion(item)
        }
        
    }

    public fun UpdateData(){
        notifyDataSetChanged()
    }

    inner class ViewHolder (val mView: RowBinding) : RecyclerView.ViewHolder(mView.root){
    }

    interface onListInteraction{
        fun onListItemInteracion(item: User?)
    }
}