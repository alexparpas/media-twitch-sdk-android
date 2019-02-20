package com.alexparpas.media.twitch.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alexparpas.media.twitch.ui.MediaTwitchUi
import com.alexparpas.media.twitch.core.MediaTwitch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MediaTwitch.init(application, getString(R.string.twitch_client_id))

        supportFragmentManager
                .beginTransaction()
                .replace(
                        R.id.frag_container,
                        MediaTwitchUi.getTwitchMediaFragment(getString(R.string.twitch_game_id))
                ).commit()
    }
}