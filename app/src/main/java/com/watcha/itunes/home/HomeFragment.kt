package com.watcha.itunes.home

import android.os.Build
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.watcha.domain.Result
import com.watcha.domain.model.Track
import com.watcha.itunes.R
import com.watcha.itunes.base.BaseFragment
import com.watcha.itunes.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private const val TAG = "HomeFragment_싸피"

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_home

    override val viewModel: HomeViewModel by viewModels()

    private val trackList = arrayListOf<Track>()
    private val homeAdapter by lazy { TrackPagingDataAdapter(viewModel) }
    private var isEnd = false
    private var isRequest = false

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

        viewModel.getTrackListResult.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    viewModel.insertTrack(it.data)
                }
                is Result.Empty -> {
                    isEnd = true
                }
                is Result.Error -> {
                    toastMessage(resources.getString(R.string.content_error))
                }
                else -> {}
            }
        }

        viewModel.temp.observe(viewLifecycleOwner) {
            CoroutineScope(Dispatchers.IO).launch {
                homeAdapter.submitData(it)
            }
        }

//        lifecycleScope.launchWhenStarted {
//            viewModel.getTrackByOffset().collect {
//                homeAdapter.submitData(it)
//            }
//        }
//        initTrackListObserver()
    }

    override fun initAfterBinding() {
//        viewModel.getLocalTrackList()
    }

//    private fun initTrackListObserver() {
//        viewModel.trackList.observe(viewLifecycleOwner) {
//            Log.d(TAG, "상태: $it ${viewModel.offset.value}")
//            when (it) {
//                is Result.Success -> {
//                    if (trackList.isEmpty()) {
//                        addData(it.data)
//                        Log.d(TAG, "삽입 완료-------------------")
//                    } else {
//                        if (trackList.last().offset < it.data.first().offset) {
//                            addData(it.data)
//                            Log.d(TAG, "삽입 완료-------------------")
//                        }
//                    }
//                    viewModel.changeLoadingState(0)
//                }
//                is Result.Empty -> { // DB가 비어있으면 API 통신을 해서 데이터를 받아온다.
//                    viewModel.getTrackList()
//                    touchOn()
//                    viewModel.changeLoadingState(0)
////                    stopLoading()
//                }
//                is Result.Error -> {
//                    toastMessage(resources.getString(R.string.content_error))
//                    touchOn()
//                    viewModel.changeLoadingState(0)
////                    stopLoading()
//                }
//                is Result.Loading -> {
//                    viewModel.changeLoadingState(1)
////                    startLoading()
//                }
//                else -> {}
//            }
//        }
//    }

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
                        Log.d(TAG, "initHomeAdapter: 마지막에 도달 데이터 요청 ${homeAdapter.itemCount}")
                        toastMessage(resources.getString(R.string.content_last_data))
                    } else if (combinedLoadStates.source.refresh is LoadState.Error) {
                        toastMessage(resources.getString(R.string.content_error))
                    }
                }
            }
        }
    }
}