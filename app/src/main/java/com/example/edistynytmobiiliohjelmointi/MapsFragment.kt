package com.example.edistynytmobiiliohjelmointi

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.edistynytmobiiliohjelmointi.databinding.FragmentMapsBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment(), GoogleMap.OnMarkerClickListener {

    // change this to match your fragment name
    private var _binding: FragmentMapsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var gMap : GoogleMap


    // tämä callback ajetaan silloin, kun Google Maps on ladattu
    // fragmenttiin onnistuneesti
    private val callback = OnMapReadyCallback { googleMap ->

        gMap = googleMap

        val sydney = LatLng(-34.0, 151.0)
        var marker1 : Marker? = googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        marker1?.tag = "Sydney"

        val rovaniemi = LatLng(66.50336579596632, 25.727513206740678)
        var marker2 : Marker? = googleMap.addMarker(MarkerOptions().position(rovaniemi).title("Rovaniemi!"))
        marker2?.tag = "Rovaniemi"

        // siirretään alkunäkymä
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(rovaniemi, 15f))

        // asetetaan että tämä luokka (MapsFragment) hoitaa onlicklistenerin
        googleMap.setOnMarkerClickListener(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // checkboxin event handler -> zoom-kontrollit päälle tai pois
        binding.zoomControls.setOnCheckedChangeListener { compoundButton, b ->
            gMap.uiSettings.isZoomControlsEnabled = b
        }

        binding.normalButton.setOnCheckedChangeListener { compoundButton, b ->
            if (compoundButton.isChecked) {
                gMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            }
        }

        binding.hybridButton.setOnCheckedChangeListener { compoundButton, b ->
            if (compoundButton.isChecked) {
                gMap.mapType = GoogleMap.MAP_TYPE_HYBRID
            }
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // OnMarkerClickiä kutsutaan jos mitä tahansa markeria kartalla klikataan
    override fun onMarkerClick(p0: Marker): Boolean {

        Log.d("TESTI", "MARKKERI CLICK! JES!")

        Log.d("TESTI", p0.tag.toString())

        Log.d("TESTI", p0.position.latitude.toString())
        Log.d("TESTI", p0.position.longitude.toString())

        val latitude : Float = p0.position.latitude.toFloat()
        val longitude : Float = p0.position.latitude.toFloat()

        val action = MapsFragmentDirections.actionMapsFragmentToCityWeatherFragment(latitude, longitude)
        findNavController().navigate(action)

        // OnMarkerlick vaatii että lopuksi palautetaan boolean
        return false
    }
}