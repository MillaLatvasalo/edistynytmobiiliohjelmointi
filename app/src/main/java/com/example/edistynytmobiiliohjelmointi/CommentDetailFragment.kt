package com.example.edistynytmobiiliohjelmointi

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.edistynytmobiiliohjelmointi.databinding.FragmentCommentDetailBinding

/**
 * A simple [Fragment] subclass.
 * Use the [CommentDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
// change this to match your fragment name
class CommentDetailFragment : Fragment() {
    // change this to match your fragment name
    private var _binding: FragmentCommentDetailBinding? = null

    val args: CommentDetailFragmentArgs by navArgs()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommentDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        Log.d("TESTI", "id:" + args.id.toString())
        // the binding -object allows you to access views in the layout, textviews etc.

        val JSON_URL = "https://jsonplaceholder.typicode.com/comments/" + args.id.toString()
        Log.d("TESTI", JSON_URL)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}