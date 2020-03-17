package com.example.theweatherproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.example.theweatherproject.R
import com.example.theweatherproject.data.VolleySingleton
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        VolleySingleton.getInstance(this).addToRequestQueue(getJsonObjectRequest())
    }

    fun getJsonObjectRequest() : JsonObjectRequest {
        val url = "api.openweathermap.org/data/2.5/forecast?id=524901&APPID=0fcfed172e3e096549c445cab418490f"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener{ response ->
                parseObject(response)
                //Log.d("respuesta", "Respuesta: "+response.toString())
            },
            Response.ErrorListener{
                //Log.d("respuestaErronea", "error")
            }
        )
        return jsonObjectRequest
    }

    fun parseObject(response: JSONObject){
        val jsonArrayResults: JSONArray = response.getJSONArray("list")
        val size: Int = jsonArrayResults.length()
        val i: Int = 0
        for (i in 0.. size -1){
            val userObject = jsonArrayResults.getJSONObject(i)
            val dt = userObject.getString("dt")
            val mainObject = userObject.getJSONObject("main")
            val temp = mainObject.getString("temp")
            val weatherObject = userObject.getJSONObject("weather")
            val type = mainObject.getString("main")
            Log.d("JSONParsing", dt + " " + temp + " " + type)

        }
    }
}
