package com.example.edistynytmobiiliohjelmointi

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.edistynytmobiiliohjelmointi.databinding.RecyclerviewItemRowBinding

class CommentAdapter(private val comments: List<Comment>) : RecyclerView.Adapter<CommentAdapter.CommentHolder>() {

    // binding layerin muututjien alustaminen
    private var _binding: RecyclerviewItemRowBinding? = null
    private val binding get() = _binding!!

    // ViewHolderin onCreate-metodi. käytännössä tässä kytketään binding layer
    // osaksi CommentHolder-luokkaan (adapterin sisäinen luokka)
    // koska CommentAdapter pohjautuu RecyclerViewin perusadapteriin, täytyy tästä
    // luokasta löytyä metodi nimeltä onCreateViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {
        // binding layerina toimii yksitätinen recyclerview_item_row.xml -instanssi
        _binding = RecyclerviewItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentHolder(binding)
    }

    // tämä metodi kytkee yksittäisen Comment-objektin yksittäisen CommentHolder-instanssiin
    // koska CommentAdapter pohjautuu RecyclerViewin perusadapteriin, täytyy tästä
    // luokasta löytyä metodi nimeltä onBindViewHolder
    override fun onBindViewHolder(holder: CommentHolder, position: Int) {
        val itemComment = comments[position]
        holder.bindComment(itemComment)
    }

    // Adapterin täytyy pysty tietämään sisältämänsä datan koko tämän metodin avulla
    // koska CommentAdapter pohjautuu RecyclerViewin perusadapteriin, täytyy tästä
    // luokasta löytyä metodi nimeltä getItemCount
    override fun getItemCount(): Int {
        return comments.size
    }

    // CommentHolder, joka määritettiin oman CommentAdapterin perusmäärityksessä (ks. luokan yläosa)
    // Holder-luokka sisältää logiikan, jolla data ja ulkoasu kytketään toisiinsa
    class CommentHolder(v: RecyclerviewItemRowBinding) : RecyclerView.ViewHolder(v.root), View.OnClickListener {

        // tämän kommentin ulkoasu ja varsinainen data
        private var view: RecyclerviewItemRowBinding = v
        private var comment: Comment? = null

        // mahdollistetaan yksittäisen itemin klikkaaminen tässä luokassa
        init {
            v.root.setOnClickListener(this)
        }

        // metodi, joka kytkee datan yksityiskohdat ulkoasun yksityiskohtiin
        fun bindComment(comment : Comment)
        {
            this.comment = comment


            var commentName : String = comment.name.toString()

            if (commentName.length > 20) {
                commentName = commentName.substring(0,20) + "..."
            }

            view.textViewCommentName.text = commentName
            view.textViewCommentBody.text = comment.body.toString()
            view.textViewCommentEmail.text = comment.email.toString()

            // aseta loput textViewit haluamallasi tavalla tähän
        }

        // jos itemiä klikataan käyttöliittymässä, ajetaan tämä koodio
        override fun onClick(v: View) {

            Log.d("TESTI", "RecyclerView CLICK " + comment?.id.toString())

            val action = apiFragmentDirections.actionApiFragmentToCommentDetailFragment(comment?.id as Int)
            v.findNavController().navigate(action)

            // tässä esimerkiksi actionilla ja navControllerin avulla
            // navigoidaan toiseen fragmentiin, jonka tarkoitus on näyttää
            // yksityiskohdat tästä kommentista.

            // klikatun kommentin id:n saat haettua näin:
            // comment.id

            // parametrina tulee lähettää joko:
            // 1. pelkkä kommentin id argumenttina
            // 2. kaikki kommentin datat omina argumentteina
            // 3. kaikki data JSON-muodossa yhtenä argumenttina (GSON)

            // TOISESSA FRAGMENTISSA, voit ottaa navArgs / args-muuttujan
            // avulla lähetetyn id-parametrin, ja muodostaa sen avulla esim. uuden URLin:

            // JSON_URL = "https://jsonplaceholder.typicode.com/comments/" + args.id.toString()
        }
    }
}