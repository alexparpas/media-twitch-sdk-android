package com.alexparpas.media.twitch.ui.video

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alexparpas.media.MediaTwitch
import com.alexparpas.media.twitch.R
import kotlinx.android.synthetic.main.activity_twitch_player.*

class TwitchPlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twitch_player)

        val channelId = intent.getStringExtra(MediaTwitch.ARG_CHANNEL_ID)
        embedPlayer(channelId)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun embedPlayer(channelId: String?) {
        web_view.apply {
            settings.apply {
                javaScriptEnabled = true
                mediaPlaybackRequiresUserGesture = false
            }
            loadUrl("https://player.twitch.tv/?channel=$channelId")
        }
    }
}