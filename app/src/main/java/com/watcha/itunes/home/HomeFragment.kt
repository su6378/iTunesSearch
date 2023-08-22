package com.watcha.itunes.home

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.filter
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.watcha.itunes.R
import com.watcha.itunes.base.BaseFragment
import com.watcha.itunes.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import com.watcha.domain.Result
import com.watcha.domain.model.Track
import kotlinx.coroutines.Job

private const val TAG = "HomeFragment_싸피"

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_home

    override val viewModel: HomeViewModel by viewModels()

    private var favoriteList = emptyList<Track>()
    private val trackAdapter by lazy { TrackPagingDataAdapter(viewModel, favoriteList) }

    override fun initStartView() {
        binding.apply {
            initHomeAdapter()
        }
    }

    override fun initDataBinding() {
        viewModel.trackList.observe(viewLifecycleOwner) {
            trackAdapter.submitData(this.lifecycle, it)
        }

        viewModel.homeSideEffects.observe(viewLifecycleOwner) {
            when (it) {
                is HomeSideEffects.ClickFavoriteTrack -> {
                    viewModel.insertTrack()
                }
            }
        }
    }

    override fun initAfterBinding() {
        viewModel.getTrackList(1)
    }

    // Adapter init
    private fun initHomeAdapter() {
        binding.apply {
            rvHome.apply {
                adapter = trackAdapter.withLoadStateHeaderAndFooter(
                    header = TrackPagingLoadStateAdapter { trackAdapter.retry() },
                    footer = TrackPagingLoadStateAdapter { trackAdapter.retry() })

                    addOnScrollListener(object : RecyclerView.OnScrollListener() {
                        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                            super.onScrolled(recyclerView, dx, dy)

                            if (!this@apply.canScrollVertically(1)) {   //최하단에 오면`
                                //원하는 동작
                                Log.d(TAG, "onScrolled: 3")
                            }

                        }
                    })
            }

            trackAdapter.addLoadStateListener { state ->
                when (val result = state.source.refresh) {
                    is LoadState.Error -> {
//                        hidLoading()
                        val error = result.error
//                        if( error is HttpException){
//                            viewModel.handleError(error.code())
//                        }
                    }

                    is LoadState.Loading -> {
//                        showLoading()
                    }

                    else -> {
//                        hidLoading()
                    }
                }
            }
        }
    }
}