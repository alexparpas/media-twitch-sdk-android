package com.alexparpas.media.twitch.ui.media

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alexparpas.media.MediaTwitch
import com.alexparpas.media.twitch.R
import com.alexparpas.media.twitch.data.CategoryItem
import com.alexparpas.media.twitch.ui.media.adapter.TwitchMediaVideosAdapter
import kotlinx.android.synthetic.main.fragment_twitch_media.*

class TwitchMediaFragment : androidx.fragment.app.Fragment(), TwitchMediaVideosAdapter.Callback {
    private lateinit var viewModel: TwitchMediaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_twitch_media, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = TwitchMediaVideosAdapter(this)

        initRecyclerView(adapter)

        observeStreams(adapter)
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(
                this,
                MediaTwitch.Injection.provideMediaViewModelFactory(gameId = requireNotNull(arguments?.getString(MediaTwitch.ARG_GAME_ID)))
        ).get(TwitchMediaViewModel::class.java)
    }

    private fun initRecyclerView(adapter: TwitchMediaVideosAdapter) {
        recycler_view.apply {
            this.adapter = adapter
            layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, 2)
            setHasFixedSize(true)
        }
    }

    private fun observeStreams(adapter: TwitchMediaVideosAdapter) {
        viewModel.streamsLiveData.observe(viewLifecycleOwner, Observer { streams ->
            if (streams != null) {
                adapter.streams = streams
            }
        })
    }

    override fun onCategoryClicked(category: CategoryItem) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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