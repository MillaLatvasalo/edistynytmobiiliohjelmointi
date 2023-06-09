package com.example.edistynytmobiiliohjelmointi

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.edistynytmobiiliohjelmointi.databinding.FragmentCityWeatherBinding
import com.example.edistynytmobiiliohjelmointi.datatypes.cityweather.CityWeather

import com.google.gson.GsonBuilder

// change this to match your fragment name
class CityWeatherFragment : Fragment() {
    // change this to match your fragment name
    private var _binding: FragmentCityWeatherBinding? = null

    // Parameters from navigation
    val args: CityWeatherFragmentArgs by navArgs()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCityWeatherBinding.inflate(inflater, container, false)
        val root: View = binding.root

        getWeather()

        // the binding -object allows you to access views in the layout, textviews etc.
        return root
    }

    fun getWeather() {

        val API_KEY : String = BuildConfig.OPENWEATHER_API_KEY

        // URL where we want to get data from
        val JSON_URL : String = "https://api.openweathermap.org/data/2.5/weather?lat=${args.latitude}&lon=${args.longitude}&units=metric&appid=${API_KEY}"

        val gson = GsonBuilder().setPrettyPrinting().create()

        // Request a string response from the provided URL.
        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.GET, JSON_URL,
            Response.Listener { response ->

                var item : CityWeather = gson.fromJson(response, CityWeather::class.java)

                Log.d("CityWeatherFragment", "Lämpötila: ${item.main?.temp}" + "°C")

                binding.temperatureText.text = item.main?.temp.toString() + "°C"

                // print the response as a whole
                // we can use GSON to modify this response into something more usable
                Log.d("CityWeatherFragment", response)

            },
            Response.ErrorListener {
                // typically this is a connection error
                Log.d("CityWeatherFragment", it.toString())
            })
        {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {

                // basic headers for the data
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                headers["Content-Type"] = "application/json; charset=utf-8"
                return headers
            }
        }

        // Add the request to the RequestQueue. This has to be done in both getting and sending new data.
        // if using this in an activity, use "this" instead of "context"
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(stringRequest)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}