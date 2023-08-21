package com.watcha.itunes.home

import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.watcha.itunes.R
import com.watcha.itunes.base.BaseFragment
import com.watcha.itunes.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
    }

    override fun initAfterBinding() {
        viewModel.getTrackList(1)
    }

    private fun initHomeAdapter() {
        binding.apply {
            rvHome.apply {
                adapter = trackAdapter.withLoadStateHeaderAndFooter(
                    header = TrackPagingLoadStateAdapter { trackAdapter.retry() },
                    footer = TrackPagingLoadStateAdapter { trackAdapter.retry() })
                //원래의 목록위치로 돌아오게함
                trackAdapter.stateRestorationPolicy =
                    RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
                setHasFixedSize(true)
            }

            trackAdapter.addLoadStateListener { combinedLoadStates ->
                binding.apply {

//                    lvHomeLoading.isVisible = combinedLoadStates.source.refresh is LoadState.Loading

                    //로딩 중이지 않을 때 (활성 로드 작업이 없고 에러가 없음)
//                    rvHome.isVisible = combinedLoadStates.source.refresh is LoadState.NotLoading

                    // 로딩 에러 발생 시
//                retryButton.isVisible = combinedLoadStates.source.refresh is LoadState.Error
//                errorText.isVisible = combinedLoadStates.source.refresh is LoadState.Error

//                    // 활성 로드 작업이 없고 에러가 없음 & 로드할 수 없음 & 개수 1 미만 (empty)
//                    if (combinedLoadStates.source.refresh is LoadState.NotLoading
//                        && combinedLoadStates.append.endOfPaginationReached
//                        && trackAdapter.itemCount < 1
//                    ) {
//                        tvNoData.isVisible = true
////                    rvHome.isVisible = true
//                    } else {
//                        tvNoData.isVisible = false
//                    }
                }
            }
        }
    } // End of initHomeAdapter
}