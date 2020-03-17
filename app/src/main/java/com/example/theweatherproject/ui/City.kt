package com.example.theweatherproject.ui

import android.os.AsyncTask
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
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import androidx.recyclerview.widget.LinearLayoutManager as LinearLayoutManager1

/**
 * A simple [Fragment] subclass.
 */
class City : Fragment() {

    val days = mutableListOf<User>()
    val API: String = "0fcfed172e3e096549c445cab418490f"
    private var adapter: CityAdapter? = null
    val CITY = arguments!!.getParcelable("data")

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

        return view
    }

    override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FragmentListViewModel::class.java)
    }

    inner class forecasTask(var CITY: String) : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg params: String?): String? {
            var response:String?
            try{
                response = URL("https://api.openweathermap.org/data/2.5/forecast?q=$CITY&units=metric&appid=$API").readText(
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
                val weather = jsonObj.getJSONArray("weather").getJSONObject(I)

                val updatedAt:Long = jsonObj.getLong("dt")
                val updatedAtText = "Forecast for: "+ SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(
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


                days.add(User(address,temp))
                adapter!!.UpdateData()


            } catch (e: Exception) {

            }

        }
    }

}
