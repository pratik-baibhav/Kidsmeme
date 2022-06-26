package com.example.kidsmeme

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var currentImageURL: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()
    }
    private fun loadMeme() {
        progressbar.visibility = View.VISIBLE
        val imageView = findViewById<ImageView>(R.id.memeimageView)
        // Instantiate the RequestQueue.

        val url = "https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
             currentImageURL = response.getString("url")
             Glide.with(this).load(currentImageURL).listener(object : RequestListener<Drawable> {
                 override fun onLoadFailed(
                     e: GlideException?,
                     model: Any?,
                     target: Target<Drawable>?,
                     isFirstResource: Boolean
                 ): Boolean {
                     progressbar.visibility = View.GONE
                     return false
                 }

                 override fun onResourceReady(
                     resource: Drawable?,
                     model: Any?,
                     target: Target<Drawable>?,
                     dataSource: DataSource?,
                     isFirstResource: Boolean
                 ): Boolean {
                     progressbar.visibility = View.GONE
                     return false
                 }

             }).into(imageView)
            },
            {

            })

// Add the request to the RequestQueue.
        MySingleton.getInstance( this).addToRequestQueue(jsonObjectRequest)
    }


    fun ShareMeme(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,  "Hey Check this out kid $currentImageURL")
        val chooser = Intent.createChooser(intent, "Share this meme using.." )
        startActivity(chooser)

    }
    fun NextMeme(view: View) {
        loadMeme()
    }
}