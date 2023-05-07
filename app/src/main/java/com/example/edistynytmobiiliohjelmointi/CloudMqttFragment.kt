package com.example.edistynytmobiiliohjelmointi

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.edistynytmobiiliohjelmointi.databinding.FragmentCloudMqttBinding
import com.hivemq.client.mqtt.MqttClient
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient
import com.hivemq.client.mqtt.mqtt3.message.connect.connack.Mqtt3ConnAck
import java.util.*

class CloudMqttFragment : Fragment() {
        private var _binding: FragmentCloudMqttBinding? = null

        // This property is only valid between onCreateView and
        // onDestroyView.
        private val binding get() = _binding!!

        // var MQTT_CLIENT_ID = BuildConfig.MQTT_CLIENT_ID + UUID.randomUUID().toString()
        // var MQTT_CLIENT_ID = UUID.randomUUID().toString()

        private lateinit var client: Mqtt3AsyncClient

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            _binding = FragmentCloudMqttBinding.inflate(inflater, container, false)
            val root: View = binding.root

            // version 3, IBM Cloud, weather station
            client = MqttClient.builder()
                .useMqttVersion3()
                .sslWithDefaultConfig()
                .identifier(BuildConfig.HIVEMQ_CLIENT_ID + UUID.randomUUID().toString())
                .serverHost(BuildConfig.HIVEMQ_BROKER)
                .serverPort(8883)
                .buildAsync()

            client.connectWith()
                .simpleAuth()
                .username(BuildConfig.HIVEMQ_USERNAME)
                .password(BuildConfig.HIVEMQ_PASSWORD.toByteArray())
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

            binding.buttonSendRemoteMessage.setOnClickListener {

                var stringPayload = binding.editTextPublishMessage.text.toString()

                client.publishWith()
                    .topic(BuildConfig.HIVEMQ_TOPIC)
                    .payload(stringPayload.toByteArray())
                    .send()
            }

            return root



        }

    fun subscribeToTopic()
    {
        client.subscribeWith()
            .topicFilter(BuildConfig.HIVEMQ_TOPIC)
            .callback { publish ->
                // this callback runs everytime your code receives new data payload
                var result = String(publish.getPayloadAsBytes())
                Log.d("ADVTECH", result)

                // laitetaan raakadata TextViewiin, ei toimi suoraan
                // koska tämä callback ei pääse käyttöliittymään käsiksi (eri thread)
                // binding.textViewWeatherStationTemperature.text = result

                // ajetaan binding-layeria koskevat asia UI-threadissa erikseen
                activity?.runOnUiThread(java.lang.Runnable {
                    binding.cloudPublishText.text = result
                })


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
