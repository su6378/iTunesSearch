package com.watcha.itunes.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.watcha.domain.model.Track
import com.watcha.itunes.R
import com.watcha.itunes.databinding.ItemListTrackBinding

private const val TAG = "HomeAdapter_싸피"
class HomeAdapter(private val viewModel: HomeViewModel) : ListAdapter<Track, HomeAdapter.TrackHolder>(HomeDiffUtil) {

    inner class TrackHolder(private val binding: ItemListTrackBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Track) {
            with(binding) {
                this.track = data
                onClickListener = viewModel
                Log.d(TAG, "bind: $layoutPosition")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackHolder {
        return TrackHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_list_track,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TrackHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    object HomeDiffUtil : DiffUtil.ItemCallback<Track>() {
        override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
            return oldItem.trackNumber == newItem.trackNumber
        }

        override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
            return oldItem == newItem
        }
    }
}