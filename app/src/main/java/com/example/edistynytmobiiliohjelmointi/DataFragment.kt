package com.example.edistynytmobiiliohjelmointi

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.edistynytmobiiliohjelmointi.databinding.FragmentDataBinding

/**
 * A simple [Fragment] subclass.
 * Use the [DataFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
// change this to match your fragment name
class DataFragment : Fragment() {
    // change this to match your fragment name
    private var _binding: FragmentDataBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDataBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.buttonNavigateDetail.setOnClickListener {
            Log.d("TESTI", "Nappi toimii!")

            val action = DataFragmentDirections.actionDataFragmentToDetailFragment(1337)
            it.findNavController().navigate(action)
        }
        // the binding -object allows you to access views in the layout, textviews etc.

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}