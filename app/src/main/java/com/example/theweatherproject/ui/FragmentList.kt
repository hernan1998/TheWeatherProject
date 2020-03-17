package com.example.theweatherproject.ui

import android.os.AsyncTask
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.theweatherproject.R
import com.example.theweatherproject.data.User
import kotlinx.android.synthetic.main.fragment_list.view.*
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*


class FragmentList : Fragment(), UserAdapter.onListInteraction {

    val cities = mutableListOf<User>()
    private var adapter: UserAdapter? = null
    val API: String = "0fcfed172e3e096549c445cab418490f"
    lateinit var navControler: NavController

    companion object {
        fun newInstance() = FragmentList()
    }

    private lateinit var viewModel: FragmentListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        weatherTask("barranquilla,co").execute()
        weatherTask("cartagena,co").execute()
        weatherTask("santa marta,co").execute()
        weatherTask("bogota,co").execute()
        weatherTask("medellin,co").execute()
        weatherTask("cali,co").execute()
        weatherTask("bucaramanga,co").execute()
        weatherTask("valledupar,co").execute()
        weatherTask("cucuta,co").execute()
        weatherTask("soacha,co").execute()
        adapter = UserAdapter(cities, this)
        view.list.layoutManager = LinearLayoutManager(context)
        view.list.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navControler = Navigation.findNavController(view)
        viewModel = ViewModelProviders.of(this).get(FragmentListViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onListItemInteracion(item: User?) {
        Log.d("KRecycleView", "onListInteraction "+ item!!.nombre)
        navControler!!.navigate(R.id.action_fragmentList_to_city)
    }

    inner class weatherTask(var CITY: String) : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg params: String?): String? {
            var response:String?
            try{
                response = URL("https://api.openweathermap.org/data/2.5/weather?q=$CITY&units=metric&appid=$API").readText(
                    Charsets.UTF_8
                )
            }catch (e: Exception){
                response = null
            }
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                /* Extracting JSON returns from the API */
                val jsonObj = JSONObject(result)
                val main = jsonObj.getJSONObject("main")
                val sys = jsonObj.getJSONObject("sys")
                val wind = jsonObj.getJSONObject("wind")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)

                val updatedAt:Long = jsonObj.getLong("dt")
                val updatedAtText = "Updated at: "+ SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(
                    Date(updatedAt*1000)
                )
                val temp = main.getString("temp")+"°C"
                val tempMin = "Min Temp: " + main.getString("temp_min")+"°C"
                val tempMax = "Max Temp: " + main.getString("temp_max")+"°C"
                val pressure = main.getString("pressure")
                val humidity = main.getString("humidity")

                val sunrise:Long = sys.getLong("sunrise")
                val sunset:Long = sys.getLong("sunset")
                var windSpeed = wind.getString("speed")
                var weatherDescription = weather.getString("description")

                var address = jsonObj.getString("name")+", "+sys.getString("country")


                cities.add(User(address,temp))
                adapter!!.UpdateData()


            } catch (e: Exception) {

            }

        }
    }

}
