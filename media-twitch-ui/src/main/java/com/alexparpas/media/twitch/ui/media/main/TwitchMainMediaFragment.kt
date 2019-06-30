package com.alexparpas.media.twitch.ui.media.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexparpas.media.twitch.core.data.model.CategoryItem
import com.alexparpas.media.twitch.ui.MediaTwitchUi
import com.alexparpas.media.twitch.ui.R
import com.alexparpas.media.twitch.ui.media.adapter.TwitchMediaOuterAdapter
import com.alexparpas.media.twitch.ui.media.adapter.TwitchMediaVideosAdapter
import kotlinx.android.synthetic.main.mt_fragment_twitch_media.*

class TwitchMainMediaFragment : androidx.fragment.app.Fragment(), TwitchMediaVideosAdapter.Callback {
    private lateinit var viewModel: TwitchMainMediaViewModel
    private val gameId: String? by lazy { arguments?.getString(MediaTwitchUi.ARG_GAME_ID) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.mt_fragment_twitch_media, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = TwitchMediaOuterAdapter(
                callback = this,
                viewPool = RecyclerView.RecycledViewPool()
        )

        initRecyclerView(adapter)

        observeStreams(adapter)
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(
                this,
                MediaTwitchUi.Injection.provideMediaViewModelFactory(gameId = requireNotNull(gameId))
        ).get(TwitchMainMediaViewModel::class.java)
    }

    private fun initRecyclerView(adapter: TwitchMediaOuterAdapter) {
        recycler_view.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    private fun observeStreams(adapter: TwitchMediaOuterAdapter) {
        viewModel.streamsLiveData.observe(viewLifecycleOwner, Observer { streams ->
            if (streams != null) {
                adapter.streams = streams
            }
        })
    }

    override fun onCategoryClicked(category: CategoryItem) {
        MediaTwitchUi.getTwitchMediaMoreFragment(
                gameId = requireNotNull(gameId),
                categoryName = category.categoryName,
                mediaType = category.mediaType
        ).show(childFragmentManager, "frag")
    }

    override fun onStreamClicked(link: String) {
        MediaTwitchUi.playVideo(requireContext(), link)
    }

    companion object {
        fun newInstance(gameId: String): TwitchMainMediaFragment {
            return TwitchMainMediaFragment().apply {
                arguments = Bundle().apply {
                    putString(MediaTwitchUi.ARG_GAME_ID, gameId)
                }
            }
        }
    }
}