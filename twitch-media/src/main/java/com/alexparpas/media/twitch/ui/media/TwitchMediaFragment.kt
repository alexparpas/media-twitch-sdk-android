package com.alexparpas.media.twitch.ui.media

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alexparpas.media.Injection
import com.alexparpas.media.MediaTwitch
import com.alexparpas.media.twitch.R

class TwitchMediaFragment : Fragment() {
    private lateinit var viewModel: TwitchMediaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_twitch_media, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(
                this,
                Injection.provideMediaViewModelFactory(gameId = requireNotNull(arguments?.getString(MediaTwitch.ARG_GAME_ID)))
        ).get(TwitchMediaViewModel::class.java)
    }

    companion object {
        fun newInstance(gameId: String): TwitchMediaFragment {
            return TwitchMediaFragment().apply {
                arguments = Bundle().apply {
                    putString(MediaTwitch.ARG_GAME_ID, gameId)
                }
            }
        }
    }
}