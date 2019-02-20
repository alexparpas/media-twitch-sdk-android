package com.alexparpas.media.twitch.ui.media.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexparpas.media.twitch.core.CategoryItem
import com.alexparpas.media.twitch.core.MediaBindingItem
import com.alexparpas.media.twitch.core.MediaItem
import com.alexparpas.media.twitch.core.VideoBinding
import com.alexparpas.media.twitch.ui.R
import kotlinx.android.synthetic.main.layout_category_rv_item.view.*
import kotlinx.android.synthetic.main.layout_video_rv_item.view.*

class TwitchMediaOuterAdapter(private val callback: TwitchMediaVideosAdapter.Callback) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var streams: List<MediaItem> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_CATEGORY) {
            CategoryViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_category_rv_item, parent, false))
        } else {
            VideosViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_video_rv_item, parent, false))
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
            (holder as VideosViewHolder).bind((streams[position] as MediaBindingItem).videos, callback)
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
            text = categoryItem.categoryName
            setOnClickListener { callback.onCategoryClicked(categoryItem) }
        }
    }
}

class VideosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(videos: List<VideoBinding>, callback: TwitchMediaVideosAdapter.Callback) {
        itemView.recycler_view?.apply {
            adapter = TwitchMediaVideosAdapter(callback).apply { this.streams = videos }
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }
    }
}