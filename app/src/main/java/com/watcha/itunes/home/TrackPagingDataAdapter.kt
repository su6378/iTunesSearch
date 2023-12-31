package com.watcha.itunes.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.watcha.domain.model.Track
import com.watcha.itunes.R
import com.watcha.itunes.databinding.ItemListTrackBinding

class TrackPagingDataAdapter(private val viewModel: HomeViewModel) :
    PagingDataAdapter<Track, TrackPagingDataAdapter.TrackViewHolder>(
        HomeDiffUtil
    ) {
    inner class TrackViewHolder(private val binding: ItemListTrackBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(track: Track) {
            binding.apply {
                this.track = track
                onClickListener = viewModel
            }
        }
    }

    // 어떤 xml 으로 뷰 홀더를 생성할지 지정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        return TrackViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_list_track,
                parent,
                false
            )
        )
    }

    // 뷰 홀더에 데이터 바인딩
    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    object HomeDiffUtil : DiffUtil.ItemCallback<Track>() {
        override fun areItemsTheSame(oldItem: Track, newItem: Track) =
            oldItem.offset == newItem.offset

        override fun areContentsTheSame(
            oldItem: Track,
            newItem: Track
        ) =
            oldItem == newItem
    }
}
