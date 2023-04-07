package com.example.memesharingapp.viewmodel

import android.app.Application
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MemeViewModel(application: Application) : ViewModel() {

    private val _memeUrl = MutableLiveData<String?>()
    val memeUrl : LiveData<String?>
    get() = _memeUrl

    private val _author = MutableLiveData<String?>()
    val author : LiveData<String?>
        get() = _author

    private val _upVotes = MutableLiveData<String?>()
    val upVotes : LiveData<String?>
        get() = _upVotes

    private val _gettingJson = MutableLiveData<Boolean?>()
    val gettingJson : LiveData<Boolean?>
    get() = _gettingJson



    init {
        _gettingJson.value = false
    }


    private fun load(){
        val url = "https://meme-api.herokuapp.com/gimme"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                _memeUrl.value = response.getString("url")
                _author.value = response.getString("author")
                _upVotes.value = response.getString("ups")
                _gettingJson.value = true
            },
            { error ->

            }
        )

    }

}