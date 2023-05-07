package com.example.edistynytmobiiliohjelmointi

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.edistynytmobiiliohjelmointi.databinding.FragmentFeedbackSendBinding

// change this to match your fragment name
class FeedbackSendFragment : Fragment() {
    // TODO: Rename and change types of parameters
    // change this to match your fragment name
    private var _binding: FragmentFeedbackSendBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeedbackSendBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.buttonSendFeedback.setOnClickListener {
            var testing = "EditText, arvot: \n"

            // testataan että saadaan seuraavalla luennolla haettua EditTextien
            // datat Volleytä varten
            testing += binding.editTextFeedbackName.text.toString()
            testing += "\n"
            testing += binding.editTextFeedbackLocation.text.toString()
            testing += "\n"
            testing += binding.editTextFeedbackValue.text.toString()

            Log.d("TESTI", testing)
        }

        // the binding -object allows you to access views in the layout, textviews etc.

        return root
    }

    fun sendFeedback() {
        // tähän koodi, joka käynnistetään napin kautta (Submit)
        // lähetetään POST-kysely Volleylla Directusiin
        // bodyna uusi data JSON-muodossa ilman id:tä (käytetään GSONia
        // muuntamaan data JSONIksi)

        // haetaan uuden feedbackin tiedot EditTexteistä (3 kpl)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}