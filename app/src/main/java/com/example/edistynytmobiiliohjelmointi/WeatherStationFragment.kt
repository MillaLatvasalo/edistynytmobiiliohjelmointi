package com.example.edistynytmobiiliohjelmointi

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.edistynytmobiiliohjelmointi.databinding.FragmentWeatherStationBinding
import com.example.edistynytmobiiliohjelmointi.datatypes.weatherstation.WeatherStation
import com.github.anastr.speedviewlib.SpeedView
import com.google.gson.GsonBuilder
import com.hivemq.client.mqtt.MqttClient
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient
import com.hivemq.client.mqtt.mqtt3.message.connect.connack.Mqtt3ConnAck
import java.util.*

class WeatherStationFragment : Fragment() {
    private var _binding: FragmentWeatherStationBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // var MQTT_CLIENT_ID = BuildConfig.MQTT_CLIENT_ID + UUID.randomUUID().toString()

    private lateinit var client: Mqtt3AsyncClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWeatherStationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // version 3, IBM Cloud, weather station
        client = MqttClient.builder()
            .useMqttVersion3()
            .sslWithDefaultConfig()
            .identifier(BuildConfig.WEATHERSTATION_CLIENT_ID + UUID.randomUUID().toString())
            .serverHost(BuildConfig.WEATHERSTATION_URL)
            .serverPort(8883)
            .buildAsync()

        client.connectWith()
            .simpleAuth()
            .username(BuildConfig.WEATHERSTATION_USERNAME)
            .password(BuildConfig.WEATHERSTATION_PASSWORD.toByteArray())
            .applySimpleAuth()
            .send()
            .whenComplete { connAck: Mqtt3ConnAck?, throwable: Throwable? ->
                if (throwable != null) {
                    Log.d("ADVTECH", "Connection failure.")
                } else {
                    // Setup subscribes or start publishing
                    subscribeToTopic()
                }
            }

        return root
    }

    fun subscribeToTopic()
    {
        val gson = GsonBuilder().setPrettyPrinting().create()

        client.subscribeWith()
            .topicFilter(BuildConfig.WEATHERSTATION_TOPIC)
            .callback { publish ->
                // this callback runs everytime your code receives new data payload
                var result = String(publish.getPayloadAsBytes())
                Log.d("ADVTECH", result)

                // try-catchin avulla voimme huolehtia ettei koodi tilttaa
                // kun sääasema lähettää diagnostiikka-payloadin (joka ei sisällä säädataa)
                try {

                    var item : WeatherStation = gson.fromJson(result, WeatherStation::class.java)

                    // haetaan lämpötila WeatherStation-objektin kautta
                    // haetaan myös ilmanpaine
                    var temperature = item.d.get1().v.toString()
                    var pressure = item.d.get2().v.toString()

                    var output = "Temperature: ${temperature} C\nPressure: ${pressure} hPa"
                    Log.d("TEST", output)

                    // ajetaan ulkoasuun liittyvät asiat UI-säikeessä
                    // koska MQTT toimii itsessään taustasäikeessä
                    // ilman tätä, ohjelma kaatuu
                    activity?.runOnUiThread(java.lang.Runnable {
                        binding.weatherstationTemperatureText.text = output
                        binding.speedViewTemperature.speedTo(item.d.get1().v)
                        binding.speedViewPressure.speedTo(item.d.get2().v)
                        binding.spinKit.visibility = View.GONE
                    })
                }

                catch (e: Exception) {
                    Log.d("ADVTECH", "Skipped diagnostics payload.")
                }

            }
            .send()
            .whenComplete { subAck, throwable ->
                if (throwable != null) {
                    // Handle failure to subscribe
                    Log.d("ADVTECH", "Subscribe failed.")
                } else {
                    // Handle successful subscription, e.g. logging or incrementing a metric
                    Log.d("ADVTECH", "Subscribed!")
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

        // hyvä tapa sammuttaa MQTT-client kun poistutaan fragmentista
        client.disconnect()
    }
}