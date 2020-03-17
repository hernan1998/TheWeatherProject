package com.example.theweatherproject.data

import android.os.AsyncTask
import com.example.theweatherproject.ui.UserAdapter
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class forecast2 {
    private var adapter: UserAdapter? = null
    val cities = mutableListOf<User>()
    val API: String = "0fcfed172e3e096549c445cab418490f"
    //val CITY: String = "barranquilla,co"
    var temper = ""
    var I: Int = 0

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


                cities.add(User(address,temp))
                adapter!!.UpdateData()


            } catch (e: Exception) {

            }

        }
    }
}