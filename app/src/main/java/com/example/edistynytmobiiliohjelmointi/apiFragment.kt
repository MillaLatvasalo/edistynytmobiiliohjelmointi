package com.example.edistynytmobiiliohjelmointi

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.edistynytmobiiliohjelmointi.databinding.FragmentApiBinding
import com.google.gson.GsonBuilder

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [apiFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class apiFragment : Fragment() {
    // change this to match your fragment name
    private var _binding: FragmentApiBinding? = null

    // alustetaan viittaus adapteriin sekä luodaan LinearLayoutManager
    // RecyclerView tarvitsee jonkin LayoutManagerin, joista yksinkertaisin on Linear
    private lateinit var adapter: CommentAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentApiBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // asetetaan RecyclerViewille linearlayout manager
        linearLayoutManager = LinearLayoutManager(context)
        binding.recyclerViewComments.layoutManager = linearLayoutManager

        binding.buttonGetComments.setOnClickListener {
            Log.d("TESTI", "Nappi toimii!")
            getComments()
        }

        // the binding -object allows you to access views in the layout, textviews etc.

        return root
    }

    // hakee Volleyllä dataa
    fun getComments()
    {
        // this is the url where we want to get our data from
        val JSON_URL = "https://jsonplaceholder.typicode.com/comments"

        // alustetaan gson
        val gson = GsonBuilder().setPrettyPrinting().create()

        // Request a string response from the provided URL.
        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.GET, JSON_URL,
            Response.Listener { response ->

                // print the response as a whole
                // we can use GSON to modify this response into something more usable
                // Log.d("ADVTECH", response)
                var rows : List<Comment> = gson.fromJson(response, Array<Comment>::class.java).toList()

                adapter = CommentAdapter(rows)
                binding.recyclerViewComments.adapter = adapter

                for (item in rows) {
                    Log.d("ADVTECH", item.email.toString())
                }

            },
            Response.ErrorListener {
                // typically this is a connection error
                Log.d("ADVTECH", it.toString())
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