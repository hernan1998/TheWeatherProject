package com.example.theweatherproject.ui

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation

import com.example.theweatherproject.R
import com.example.theweatherproject.R.layout.fragment_city
import com.example.theweatherproject.data.CityModel
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
    lateinit var cityModel : CityModel
    var CITY = ""

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

        adapter = CityAdapter(days)
        view.citylist.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        view.citylist.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cityModel = arguments!!.getParcelable("data")!!
        view.findViewById<TextView>(R.id.textView).text = cityModel.name
        CITY = cityModel.name
        forecasTask().execute()
        Log.d("flavio", CITY)

        viewModel = ViewModelProviders.of(this).get(FragmentListViewModel::class.java)
    }

    inner class forecasTask() : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg params: String?): String? {
            var response:String?
            try{
                Log.d("nombreciu",CITY)
                response = URL("https://api.openweathermap.org/data/2.5/forecast?q="+CITY+"&units=metric&appid=$API").readText(
                    Charsets.UTF_8
                )
            }catch (e: Exception){
                Log.d("esepcion", e.toString())
                response = null
            }
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                var j = 0
                for (i in 0 until 39 step 8 ) {
                    j += 1
                    /* Extracting JSON returns from the API */
                    val jsonObj = JSONObject(result)
                    val list = jsonObj.getJSONArray("list").getJSONObject(i)
                    val main = list.getJSONObject("main")
                    val ciudad = jsonObj.getJSONObject("city")
                    val wind = list.getJSONObject("wind")
                    val weather = list.getJSONArray("weather").getJSONObject(0)

                    val updatedAt: Long = list.getLong("dt")
                    val updatedAtText = "Forecast for: " + SimpleDateFormat(
                        "dd/MM/yyyy hh:mm a",
                        Locale.ENGLISH
                    ).format(
                        Date(updatedAt * 1000)
                    )
                    val temp = main.getString("temp") + "°C"
                    val tempMin = "Min Temp: " + main.getString("temp_min") + "°C"
                    val tempMax = "Max Temp: " + main.getString("temp_max") + "°C"
                    val pressure = main.getString("pressure")
                    val humidity = main.getString("humidity")

                    val sunrise: Long = ciudad.getLong("sunrise")
                    val sunset: Long = ciudad.getLong("sunset")
                    var windSpeed = wind.getString("speed")
                    var weatherDescription = weather.getString("description")

                    var address = ciudad.getString("name") + ", " + ciudad.getString("country")


                    Log.d("foretask", "entro forecast final")
                    days.add(User("Dia "+j, temp))
                    adapter!!.UpdateData()
                }

            } catch (e: Exception) {
                Log.d("foretask", "entro forecast catch")
            }

        }
    }

}
