package com.watcha.itunes.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.watcha.domain.model.Track
import com.watcha.itunes.R
import com.watcha.itunes.databinding.ItemListFavoriteBinding

class FavoriteAdapter(private val viewModel: FavoriteViewModel) : ListAdapter<Track, FavoriteAdapter.FavoriteHolder>(HomeDiffUtil) {

    inner class FavoriteHolder(private val binding: ItemListFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Track) {
            with(binding) {
                this.track = data
                onClickListener = viewModel
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        return FavoriteHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_list_favorite,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    object HomeDiffUtil : DiffUtil.ItemCallback<Track>() {
        override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
            return oldItem.offset == newItem.offset
        }

        override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
            return oldItem == newItem
        }
    }
}