package com.example.myapis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    var flagsURL = ""
    var countryName = ""
    var emoji = ""
    var code = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getHeroImageURL()
        Log.d("flagsURL", "flags URL set")
        var button = findViewById<Button>(R.id.button)
        var imageView = findViewById<ImageView>(R.id.nasaPic)
        var text1 = findViewById<TextView>(R.id.firstTextView)
        var text2 = findViewById<TextView>(R.id.secondTextView)
        var text3 = findViewById<TextView>(R.id.thirdTextView)
        getNextImage(button, imageView, text1, text2, text3)
    }

    private fun getNextImage(button: Button, imageView: ImageView, t1: TextView, t2: TextView, t3: TextView) {
        button.setOnClickListener {
            getHeroImageURL()

            Glide.with(this)
                .load(flagsURL)
                .fitCenter()
                .into(imageView)
        }
    }

    private fun getHeroImageURL() {
        val client = AsyncHttpClient()

        client["https://cdn.jsdelivr.net/npm/country-flag-emoji-json@2.0.0/dist/index.json", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                Log.d("NationalFlags", "response successful$json")

                val randomNumber = Random.nextInt(json.jsonArray.length())

                flagsURL = json.jsonArray.getJSONObject(randomNumber).getString("image")
                Log.d("LOGURL","your url $flagsURL")
                countryName = json.jsonArray.getJSONObject(randomNumber).getString("name")
                code = json.jsonArray.getJSONObject(randomNumber).getString("code")
                emoji = json.jsonArray.getJSONObject(randomNumber).getString("emoji")
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("NationalFlags Error", errorResponse)
            }
        }]

    }
}