package com.alexparpas.media.twitch.ui.media

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alexparpas.media.twitch.R
import com.alexparpas.media.twitch.data.GameStreamMinimal
import kotlinx.android.synthetic.main.layout_twitch_stream.view.*

class TwitchMediaAdapter(private val callback: Callback) : RecyclerView.Adapter<MainViewHolder>() {
    var streams: List<GameStreamMinimal> = mutableListOf()
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
    }
}

class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(stream: GameStreamMinimal, callback: TwitchMediaAdapter.Callback) {
        itemView.stream_tv.apply {
            text = stream.toString()
            setOnClickListener { callback.onStreamClicked(stream.userName) }
        }
    }
}