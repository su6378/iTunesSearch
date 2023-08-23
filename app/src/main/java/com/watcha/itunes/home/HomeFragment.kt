package com.watcha.itunes.home

import android.os.Build
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.watcha.domain.Result
import com.watcha.domain.model.Track
import com.watcha.itunes.R
import com.watcha.itunes.base.BaseFragment
import com.watcha.itunes.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

private const val TAG = "HomeFragment_싸피"

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_home

    override val viewModel: HomeViewModel by viewModels()

    private val trackList = arrayListOf<Track>()
    private val homeAdapter by lazy { HomeAdapter(viewModel) }
    private var isEnd = false
    private var isRequest = false

    override fun initStartView() {
        binding.apply {
            initHomeAdapter()
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

        initTrackListObserver()
    }

    override fun initAfterBinding() {
        viewModel.getLocalTrackList()
    }

    private fun initTrackListObserver() {
        viewModel.trackList.observe(viewLifecycleOwner) {
            Log.d(TAG, "상태: $it ${viewModel.offset.value}")
            when (it) {
                is Result.Success -> {
                    if (trackList.isEmpty()) {
                        addData(it.data)
                        Log.d(TAG, "삽입 완료-------------------")
                    } else {
                        if (trackList.last().offset < it.data.first().offset) {
                            addData(it.data)
                            Log.d(TAG, "삽입 완료-------------------")
                        }
                    }
                }
                is Result.Empty -> { // DB가 비어있으면 API 통신을 해서 데이터를 받아온다.
                    viewModel.getTrackList()
                    touchOn()
                    stopLoading()
                }
                is Result.Error -> {
                    toastMessage(resources.getString(R.string.content_error))
                    touchOn()
                    stopLoading()
                }
                is Result.Loading -> {
//                    startLoading()
                }
                else -> {}
            }
        }
    }

    // init adapter
    private fun initHomeAdapter() {
        binding.apply {
            rvHome.apply {
                adapter = homeAdapter
                // 페이징 기능
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)

                        if (!this@apply.canScrollVertically(1)) { // 최하단을 감지하면 다음 offset -> API 요청
                            if (!isEnd) {
                                Log.d(TAG, "onScrolled: 호출 ${trackList.last().offset}")
                                touchOff()
//                                viewModel.trackList.removeObservers(viewLifecycleOwner) // SingLiveEvent 처리 실패 -> 옵저버를 제거하고 재생성해서 getLocalTrackList() 함수 재호출
//                                initTrackListObserver()
                                viewModel.getLocalTrackList()
                            }else toastMessage(resources.getString(R.string.content_last_data))
                        }
                    }
                })
            }
        }
    }

    // Adapter 리스트에 데이터 추가
    private fun addData(list: List<Track>) {
        trackList.addAll(list)
        homeAdapter.submitList(trackList)
        homeAdapter.notifyItemInserted(trackList.size - 1)
        viewModel.nextOffset(trackList.last().offset + 1)
        stopLoading()
        touchOn()
    }

    // 로딩 애니메이션 제어
    private fun startLoading() {
        binding.lvHomeTrackLoading.visibility = View.VISIBLE
    }

    private fun stopLoading() {
        binding.lvHomeTrackLoading.visibility = View.GONE
    }

    // 터치 제어 함수
    private fun touchOn() {
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    private fun touchOff() {
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }
}