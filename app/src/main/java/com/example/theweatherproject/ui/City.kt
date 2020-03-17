package com.example.theweatherproject.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation

import com.example.theweatherproject.R
import com.example.theweatherproject.R.layout.fragment_city
import com.example.theweatherproject.data.User
import kotlinx.android.synthetic.main.fragment_city.view.*
import kotlinx.android.synthetic.main.fragment_list.view.*
import androidx.recyclerview.widget.LinearLayoutManager as LinearLayoutManager1

/**
 * A simple [Fragment] subclass.
 */
class City : Fragment() {

    val days = mutableListOf<User>()
    private var adapter: CityAdapter? = null

    companion object {
        fun newInstance() = City()
    }

    private lateinit var viewModel: FragmentListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(fragment_city, container, false)
        days.add(User("Dia 1", "45 F"))
        adapter = CityAdapter(days)
        view.citylist.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        view.citylist.adapter = adapter
        adapter!!.UpdateData()
        return view
    }

    override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FragmentListViewModel::class.java)
    }

}
