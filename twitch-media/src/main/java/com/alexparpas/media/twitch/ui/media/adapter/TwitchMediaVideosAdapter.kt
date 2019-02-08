package com.alexparpas.media.twitch.ui.media.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alexparpas.media.twitch.R
import com.alexparpas.media.twitch.data.CategoryItem
import com.alexparpas.media.twitch.data.VideoBinding
import kotlinx.android.synthetic.main.layout_twitch_stream.view.*

class TwitchMediaVideosAdapter(private val callback: Callback) : RecyclerView.Adapter<MainViewHolder>() {
    var streams: List<VideoBinding> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_twitch_stream, parent, false))
    }

    override fun getItemCount(): Int = streams.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(streams[position], callback)
    }

    interface Callback {
        fun onStreamClicked(channelName: String)

        fun onCategoryClicked(category: CategoryItem)
    }
}

class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(stream: VideoBinding, callback: TwitchMediaVideosAdapter.Callback) {
        itemView.stream_tv.apply {
            text = stream.toString()
            setOnClickListener { callback.onStreamClicked(stream.title) }
        }
    }
}