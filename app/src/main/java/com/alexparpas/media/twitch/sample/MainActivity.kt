package com.alexparpas.media.twitch.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alexparpas.media.MediaTwitch
import com.alexparpas.media.twitch.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MediaTwitch.init(application)
    }
}