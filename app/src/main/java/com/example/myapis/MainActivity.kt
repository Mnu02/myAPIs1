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

    var avatarURL = ""
    var characterName = ""
    var id = ""
    var physDescription = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getHeroImageURL()
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
            t3.text = characterName

            try {
                Glide.with(this)
                    .load(avatarURL)
                    .fitCenter()
                    .into(imageView)
            } catch (e: Exception) {
                Log.d("Hello", "$e")
                getNextImage(button, imageView, t1, t2, t3)
            }
        }
    }

    private fun getHeroImageURL() {
        val client = AsyncHttpClient()

        client["https://api.sampleapis.com/avatar/characters", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                Log.d("NationalFlags", "response successful$json")

                val randomNumber = Random.nextInt(json.jsonArray.length())

                avatarURL = json.jsonArray.getJSONObject(randomNumber).getString("image")
                Log.d("AvatarURL", "URL set to -->$avatarURL")
                if (!json.jsonArray.getJSONObject(randomNumber).isNull("name")){
                    characterName = json.jsonArray.getJSONObject(randomNumber).getString("name")
            }
                Log.d("AvatarName", "Name set to -->$characterName")
                id = json.jsonArray.getJSONObject(randomNumber).getString("id")
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