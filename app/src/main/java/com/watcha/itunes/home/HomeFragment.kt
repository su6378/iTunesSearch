package com.watcha.itunes.home

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.watcha.itunes.R
import com.watcha.itunes.base.BaseFragment
import com.watcha.itunes.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.watcha.domain.Result
import kotlinx.coroutines.Job

private const val TAG = "HomeFragment_싸피"

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_home

    override val viewModel: HomeViewModel by viewModels()

    private val trackAdapter by lazy { TrackPagingDataAdapter(viewModel) }
    private lateinit var job: Job

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
                    Log.d(TAG, "initDataBinding: ${viewModel.trackNumber.value}")
                    jobUpdate { viewModel.existFavoriteTrack }

                }
            }
        }
    }

    override fun initAfterBinding() {
        collectFavoriteTrack()
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

    private fun jobUpdate(logic: () -> Unit) {
        job.cancel()
        logic()
        collectFavoriteTrack()
    }

    private fun collectFavoriteTrack() {
        job = lifecycleScope.launch {
            viewModel.existFavoriteTrack.collect {
                if (it is Result.Success) {
                    Log.d(TAG, "collectFavoriteTrack: $it 해당 데이터가 존재합니다.")
                } else Log.d(TAG, "collectFavoriteTrack: 실패 $it")
            }
        }
    }
}