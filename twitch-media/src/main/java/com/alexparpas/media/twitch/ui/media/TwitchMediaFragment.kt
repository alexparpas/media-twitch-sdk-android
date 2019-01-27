package com.alexparpas.media.twitch.ui.media

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alexparpas.media.MediaTwitch
import com.alexparpas.media.twitch.R
import kotlinx.android.synthetic.main.fragment_twitch_media.*
import timber.log.Timber

class TwitchMediaFragment : Fragment(), TwitchMediaAdapter.Callback {
    private lateinit var viewModel: TwitchMediaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_twitch_media, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = TwitchMediaAdapter(this)

        initRecyclerView(adapter)

        observeStreams(adapter)
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(
                this,
                MediaTwitch.Injection.provideMediaViewModelFactory(gameId = requireNotNull(arguments?.getString(MediaTwitch.ARG_GAME_ID)))
        ).get(TwitchMediaViewModel::class.java)
    }

    private fun initRecyclerView(adapter: TwitchMediaAdapter) {
        recycler_view.apply {
            this.adapter = adapter
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
        }
    }

    private fun observeStreams(adapter: TwitchMediaAdapter) {
        viewModel.streamsLiveData.observe(viewLifecycleOwner, Observer { streams ->
            if (streams != null) {
                adapter.streams = streams
            }
        })
    }

    override fun onStreamClicked(channelName: String) {
        MediaTwitch.playVideo(requireContext(), channelName)
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