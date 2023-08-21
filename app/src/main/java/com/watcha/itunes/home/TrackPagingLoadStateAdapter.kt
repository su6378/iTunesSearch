package com.watcha.itunes.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.watcha.itunes.databinding.ItemLoadStateBinding

class TrackPagingLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<TrackPagingLoadStateAdapter.PagingLoadStateViewHolder>() {
    inner class PagingLoadStateViewHolder(
        private val binding: ItemLoadStateBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(state: LoadState) {
            binding.lvHomeTrackLoading.isVisible = state is LoadState.Loading
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): PagingLoadStateViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PagingLoadStateViewHolder(ItemLoadStateBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: PagingLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }
}