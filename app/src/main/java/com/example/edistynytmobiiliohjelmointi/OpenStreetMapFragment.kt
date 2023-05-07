package com.example.edistynytmobiiliohjelmointi

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.edistynytmobiiliohjelmointi.databinding.FragmentOpenStreetMapBinding
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.config.Configuration.*
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker

// change this to match your fragment name
class OpenStreetMapFragment : Fragment() {
    // change this to match your fragment name
    private var _binding: FragmentOpenStreetMapBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOpenStreetMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // haetaan viittaus Androidin sisäiseen SharedPreferences-ominaisuuteen
        // jonne OpenStreetMap voi tallentaa tarvittaessa dataa (esim. kartta-tile-kuvat)
        getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context as Context))

        // the binding -object allows you to access views in the layout, textviews etc.

        // asetetaan TileSource, oletus MAPNIK
        binding.map.setTileSource(TileSourceFactory.MAPNIK)

        // asetetaan zoom-taso ja koordinatit Rovaniemelle
        // huom, setZoom olettaa floatin!
        val mapController = binding.map.controller
        mapController.setZoom(16.0)
        val startPoint = GeoPoint(66.50336579596632, 25.727513206740678);
        mapController.setCenter(startPoint);

        // lisätään marker Rovaniemielle
        val marker = Marker(binding.map)
        marker.position = startPoint
        marker.title = "Rovaniemi marker!"
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
        binding.map.overlays.add(marker)
        binding.map.invalidate()

        // markeria jos klikataan, ajetaan tämä
        marker.setOnMarkerClickListener { marker, mapView ->

            Log.d("TESTI", "Markeria klikattu!")

            return@setOnMarkerClickListener false
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}