package com.example.theweatherproject.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.theweatherproject.R
import com.example.theweatherproject.data.User
import kotlinx.android.synthetic.main.fragment_list.view.*


class FragmentList : Fragment(), UserAdapter.onListInteraction {

    val cities = mutableListOf<User>()
    private var adapter: UserAdapter? = null
    val CITY: String = "barranquilla,co"
    val API: String = "0fcfed172e3e096549c445cab418490f"

    companion object {
        fun newInstance() = FragmentList()
    }

    private lateinit var viewModel: FragmentListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        cities.add(User("Ciudad 1", "32 F"))
        cities.add(User("Ciudad 2", "26 F"))
        adapter = UserAdapter(cities, this)
        view.list.layoutManager = LinearLayoutManager(context)
        view.list.adapter = adapter
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FragmentListViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onListItemInteracion(item: User?) {
        Log.d("KRecycleView", "onListInteraction "+ item!!.nombre)
    }

}
