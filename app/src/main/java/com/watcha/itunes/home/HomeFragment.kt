package com.watcha.itunes.home

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.watcha.itunes.R
import com.watcha.itunes.base.BaseFragment
import com.watcha.itunes.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_home

    override val viewModel: HomeViewModel by viewModels()

    private val homeAdapter by lazy { TrackPagingDataAdapter(viewModel) }

    override fun initStartView() {
        binding.apply {
            initHomeAdapter()
            vm = viewModel
        }
    }

    override fun initDataBinding() {
        viewModel.homeSideEffects.observe(viewLifecycleOwner) {
            when (it) {
                is HomeSideEffects.ClickFavoriteTrack -> {
                    viewModel.updateTrack()
                    homeAdapter.notifyItemChanged(viewModel.track.value!!.offset)
                }
            }
        }

        viewModel.trackList.observe(viewLifecycleOwner) {
            CoroutineScope(Dispatchers.IO).launch {
                homeAdapter.submitData(it)
            }
        }
    }

    override fun initAfterBinding() {}

    // init adapter
    private fun initHomeAdapter() {
        binding.apply {
            rvHome.apply {
                adapter = homeAdapter.withLoadStateHeaderAndFooter(
                    header = TrackPagingLoadStateAdapter { homeAdapter.retry() },
                    footer = TrackPagingLoadStateAdapter { homeAdapter.retry() })
            }

            homeAdapter.addLoadStateListener { combinedLoadStates ->
                binding.apply {

                    // 로딩 중일때
                    lvHomeLoading.isVisible = combinedLoadStates.source.refresh is LoadState.Loading

                    //로딩 중이지 않을 때 (활성 로드 작업이 없고 에러가 없음)
                    rvHome.isVisible = combinedLoadStates.source.refresh is LoadState.NotLoading

                    // 요청할 데이터가 없는 경우
                    if (combinedLoadStates.source.append.endOfPaginationReached) {
                        toastMessage(resources.getString(R.string.content_last_data))
                    } else if (combinedLoadStates.source.refresh is LoadState.Error) { // 에러 발생시
                        toastMessage(resources.getString(R.string.content_error))
                    }
                }
            }
        }
    }
}