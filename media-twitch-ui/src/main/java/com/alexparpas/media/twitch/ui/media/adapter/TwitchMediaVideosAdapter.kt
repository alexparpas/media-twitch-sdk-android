package com.alexparpas.media.twitch.ui.media.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexparpas.media.twitch.core.CategoryItem
import com.alexparpas.media.twitch.core.VideoBinding
import com.alexparpas.media.twitch.ui.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.mt_layout_twitch_stream.view.*

class TwitchMediaVideosAdapter(private val callback: Callback) : RecyclerView.Adapter<MainViewHolder>() {
    var streams: List<VideoBinding> = mutableListOf()
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
    fun bind(item: VideoBinding, callback: TwitchMediaVideosAdapter.Callback) {
        if (item.thumbnailUrl.isNotBlank()) {
            Picasso.with(itemView.context).load(item.thumbnailUrl).into(itemView.thumbnail_iv)
        }
        itemView.video_title_tv.text = item.title
        itemView.subtitle_tv.text = item.subtitle
        itemView.views_tv.text = item.viewerCount
        itemView.duration_tv.text = item.duration
        itemView.setOnClickListener { callback.onStreamClicked(item.link) }
    }
}