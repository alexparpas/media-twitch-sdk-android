package com.alexparpas.media.twitch.ui.media.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.alexparpas.media.twitch.core.CategoryItem
import com.alexparpas.media.twitch.core.MediaType
import com.alexparpas.media.twitch.ui.MediaTwitchUi
import com.alexparpas.media.twitch.ui.R
import com.alexparpas.media.twitch.ui.media.adapter.TwitchMediaVideosAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.mt_fragment_youtube_more_media.*

class TwitchMediaMoreFragment : BottomSheetDialogFragment(), TwitchMediaVideosAdapter.Callback {
    private lateinit var viewModel: TwitchMediaMoreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.mt_fragment_youtube_more_media, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = TwitchMediaVideosAdapter(this)

        initRecyclerView(adapter)
        observeVideos(adapter)
        observeCategoryName()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(
                this,
                MediaTwitchUi.Injection.provideMediaMoreViewModelFactory(
                        gameId = requireNotNull(arguments?.getString(ARG_GAME_ID)),
                        categoryName = requireNotNull(arguments?.getString(ARG_CATEGORY_NAME)),
                        mediaType = requireNotNull(arguments?.getSerializable(ARG_MEDIA_TYPE) as MediaType)
                )).get(TwitchMediaMoreViewModel::class.java)
    }

    private fun initRecyclerView(adapter: TwitchMediaVideosAdapter) {
        recycler_view.apply {
            this.adapter = adapter
            layoutManager = GridLayoutManager(context, 2)
        }
    }

    private fun observeVideos(adapter: TwitchMediaVideosAdapter) {
        viewModel.videosLiveData.observe(viewLifecycleOwner, Observer {
            adapter.streams = it
        })
    }

    private fun observeCategoryName() {
        viewModel.categoryNameLiveData.observe(viewLifecycleOwner, Observer {
            category_tv.text = it
        })
    }

    override fun onStreamClicked(link: String) {
        MediaTwitchUi.playVideo(requireContext(), link)
    }

    override fun onCategoryClicked(category: CategoryItem) {
        //no-op
    }

    companion object {
        const val ARG_GAME_ID = "ARG_GAME_ID"
        const val ARG_CATEGORY_NAME = "ARG_CATEGORY_NAME"
        const val ARG_MEDIA_TYPE = "ARG_MEDIA_TYPE"

        fun newInstance(gameId: String, categoryName: String, mediaType: MediaType): TwitchMediaMoreFragment =
                TwitchMediaMoreFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_GAME_ID, gameId)
                        putString(ARG_CATEGORY_NAME, categoryName)
                        putSerializable(ARG_MEDIA_TYPE, mediaType)
                    }
                }
    }
}