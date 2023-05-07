package com.example.edistynytmobiiliohjelmointi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.edistynytmobiiliohjelmointi.databinding.FragmentFeedbackReadBinding

// change this to match your fragment name
class FeedbackReadFragment : Fragment() {
    // TODO: Rename and change types of parameters
    // change this to match your fragment name
    private var _binding: FragmentFeedbackReadBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeedbackReadBinding.inflate(inflater, container, false)
        val root: View = binding.root


        // the binding -object allows you to access views in the layout, textviews etc.

        getFeedbacks()

        return root
    }

    fun getFeedbacks() {
        // tähän tulee lopuksi Volley-koodi, jolla haetaan dataa Directusista
        // Directusin data on JSONia, ja muunnamme sen käyttökelpoiseen muotoon
        // GSONilla. Tätä varten tarvitaan dataluokka json2kt.comin kautta.
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}