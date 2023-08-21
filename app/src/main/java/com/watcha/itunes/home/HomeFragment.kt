package com.watcha.itunes.home

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.watcha.itunes.R
import com.watcha.itunes.base.BaseFragment
import com.watcha.itunes.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "HomeFragment_싸피"

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_home

    override val viewModel: HomeViewModel by viewModels()

    private val trackAdapter by lazy { TrackPagingDataAdapter(viewModel) }

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
                    Log.d(TAG, "initDataBinding: 클릭됨 ${it.track.trackName}")
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