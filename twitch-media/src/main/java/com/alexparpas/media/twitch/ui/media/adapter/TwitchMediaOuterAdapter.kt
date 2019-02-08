package com.alexparpas.media.twitch.ui.media.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alexparpas.media.twitch.R
import com.alexparpas.media.twitch.data.MediaBindingItem
import com.alexparpas.media.twitch.data.CategoryItem
import com.alexparpas.media.twitch.data.MediaItem
import com.alexparpas.media.twitch.data.VideoBinding
import kotlinx.android.synthetic.main.layout_category_rv_item.view.*
import kotlinx.android.synthetic.main.layout_video_rv_item.view.*

class TwitchMediaOuterAdapter(private val callback: TwitchMediaVideosAdapter.Callback) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var videos: List<MediaItem> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    init {

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
        return if (videos[position] is CategoryItem) {
            TYPE_CATEGORY
        } else {
            TYPE_VIDEOS
        }
    }

    override fun getItemCount(): Int = videos.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == TYPE_CATEGORY) {
            (holder as CategoryViewHolder).bind((videos[position] as CategoryItem), callback)
        } else {
            (holder as VideosViewHolder).bind((videos[position] as MediaBindingItem).videos, callback)
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
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }
    }
}