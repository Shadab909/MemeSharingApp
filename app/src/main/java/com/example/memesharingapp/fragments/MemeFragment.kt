package com.example.memesharingapp.fragments

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.memesharingapp.viewmodel.MemeViewModel
import com.example.memesharingapp.R
import com.example.memesharingapp.databinding.MemeFragmentBinding
import com.example.memesharingapp.util.MySingleton

class MemeFragment : Fragment() {
    private lateinit var binding: MemeFragmentBinding
    private lateinit var viewModel: MemeViewModel
    private var memeUrl : String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.meme_fragment,container,false)

        viewModel = ViewModelProvider(this)[MemeViewModel::class.java]



        loadMeme()

        binding.memeNextBtn.setOnClickListener {
            loadMeme()
        }

        binding.memeShareBtn.setOnClickListener {
            shareMeme()
        }

        return binding.root
    }

    private fun loadMeme(){
        val url = "https://meme-api.herokuapp.com/gimme"
        binding.memeProgress.visibility = View.VISIBLE
        binding.upVotes.visibility = View.INVISIBLE
        binding.author.visibility = View.INVISIBLE

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
               memeUrl = response.getString("url")
                val author = response.getString("author")
                val upVotes = response.getString("ups")

                binding.author.text = "Author : $author"
                binding.upVotes.text = "UpVotes : $upVotes"
                context?.let {
                    Glide.with(it).load(memeUrl).listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {

                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            binding.memeProgress.visibility = View.GONE
                            binding.upVotes.visibility = View.VISIBLE
                            binding.author.visibility = View.VISIBLE
                            return false
                        }

                    }).into(binding.memeImage)
                }
            },
            { error ->
                Toast.makeText(context, error.localizedMessage,Toast.LENGTH_SHORT).show()
            }
        )
        context?.let { MySingleton.getInstance(it).addToRequestQueue(jsonObjectRequest) }
    }

    private fun shareMeme(){
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, memeUrl)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

}