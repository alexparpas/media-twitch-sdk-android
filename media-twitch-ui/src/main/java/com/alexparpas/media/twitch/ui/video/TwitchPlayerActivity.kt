package com.alexparpas.media.twitch.ui.video

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alexparpas.media.twitch.ui.MediaTwitchUi
import com.alexparpas.media.twitch.ui.R
import kotlinx.android.synthetic.main.mt_activity_twitch_player.*

class TwitchPlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mt_activity_twitch_player)

        val link = intent.getStringExtra(MediaTwitchUi.ARG_LINK)
        embedPlayer(link)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun embedPlayer(link: String?) {
        web_view.apply {
            settings.apply {
                setBackgroundColor(Color.TRANSPARENT)
                javaScriptEnabled = true
                mediaPlaybackRequiresUserGesture = false
            }
            loadUrl(link)
        }
    }
}