package com.alexparpas.media.twitch.ui.video

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alexparpas.media.MediaTwitch
import com.alexparpas.media.twitch.R
import kotlinx.android.synthetic.main.activity_twitch_player.*

class TwitchPlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twitch_player)

        val link = intent.getStringExtra(MediaTwitch.ARG_LINK)
        embedPlayer(link)
    }

    @SuppressLint("SetJavaScriptEnabled")
        private fun embedPlayer(link: String?) {
        web_view.apply {
            settings.apply {
                javaScriptEnabled = true
                mediaPlaybackRequiresUserGesture = false
            }
            loadUrl(link)
        }
    }
}