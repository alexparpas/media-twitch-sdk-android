package com.alexparpas.media.twitch.ui.media.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexparpas.media.twitch.core.data.model.CategoryItem
import com.alexparpas.media.twitch.core.data.model.MediaBindingItem
import com.alexparpas.media.twitch.core.data.model.MediaItem
import com.alexparpas.media.twitch.core.data.model.VideoItem
import com.alexparpas.media.twitch.ui.R
import kotlinx.android.synthetic.main.mt_layout_category_rv_item.view.*
import kotlinx.android.synthetic.main.mt_layout_video_rv_item.view.*

class TwitchMediaOuterAdapter(
        private val callback: TwitchMediaVideosAdapter.Callback,
        private val viewPool: RecyclerView.RecycledViewPool
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var
            streams: List<MediaItem> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_CATEGORY) {
            CategoryViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.mt_layout_category_rv_item, parent, false))
        } else {
            VideosViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.mt_layout_video_rv_item, parent, false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (streams[position] is CategoryItem) {
            TYPE_CATEGORY
        } else {
            TYPE_VIDEOS
        }
    }

    override fun getItemCount(): Int = streams.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == TYPE_CATEGORY) {
            (holder as CategoryViewHolder).bind((streams[position] as CategoryItem), callback)
        } else {
            (holder as VideosViewHolder).bind((streams[position] as MediaBindingItem).videos, callback, viewPool)
        }
    }

    companion object {
        const val TYPE_CATEGORY = 0
        const val TYPE_VIDEOS = 1
    }
}

class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(categoryItem: CategoryItem, callback: TwitchMediaVideosAdapter.Callback) {
        itemView.category_tv.apply {
            itemView.setOnClickListener { callback.onCategoryClicked(categoryItem) }
            text = categoryItem.categoryName
        }
    }
}

class VideosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(videos: List<VideoItem>, callback: TwitchMediaVideosAdapter.Callback, viewPool: RecyclerView.RecycledViewPool) {
        itemView.recycler_view?.apply {
            setRecycledViewPool(viewPool)
            adapter = TwitchMediaVideosAdapter(callback).apply { this.streams = videos }
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
        }
    }
}