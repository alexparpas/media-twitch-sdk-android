package com.alexparpas.media.twitch.ui.media.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexparpas.media.twitch.core.data.model.CategoryItem
import com.alexparpas.media.twitch.core.data.model.VideoItem
import com.alexparpas.media.twitch.ui.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.mt_layout_twitch_stream.view.*

class TwitchMediaVideosAdapter(private val callback: Callback) : RecyclerView.Adapter<MainViewHolder>() {
    var streams: List<VideoItem> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.mt_layout_twitch_stream, parent, false))
    }

    override fun getItemCount(): Int = streams.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(streams[position], callback)
    }

    interface Callback {
        fun onStreamClicked(link: String)

        fun onCategoryClicked(category: CategoryItem)
    }
}

class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: VideoItem, callback: TwitchMediaVideosAdapter.Callback) {
        if (item.thumbnailUrl.isNotBlank()) {
            Picasso.with(itemView.context)
                    .load(item.thumbnailUrl)
                    .placeholder(R.drawable.mt_video_placeholder)
                    .into(itemView.thumbnail_iv)
        }
        itemView.video_title_tv.text = item.title
        itemView.subtitle_tv.text = item.subtitle
        itemView.views_tv.text = item.viewerCount
        itemView.duration_tv.apply {
            visibility = if (item.shouldShowDuration) View.VISIBLE else View.GONE
            itemView.duration_tv.text = item.duration
        }
        itemView.live_tv.visibility = if (item.isLive) View.VISIBLE else View.GONE
        itemView.setOnClickListener { callback.onStreamClicked(item.link) }
    }
}