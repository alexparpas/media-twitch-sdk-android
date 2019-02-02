package com.alexparpas.media.twitch.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alexparpas.media.MediaTwitch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MediaTwitch.init(application, getString(R.string.twitch_client_id))

        supportFragmentManager
                .beginTransaction()
                .replace(
                        R.id.frag_container,
                        MediaTwitch.getTwitchMediaFragment(getString(R.string.twitch_game_id))
                ).commit()
    }
}