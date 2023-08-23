package com.watcha.itunes.home

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.watcha.domain.Result
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

    private val homeAdapter by lazy { HomeAdapter(viewModel) }

    override fun initStartView() {
        binding.apply {
            initHomeAdapter()
        }
    }

    override fun initDataBinding() {
        viewModel.homeSideEffects.observe(viewLifecycleOwner) {
            when (it) {
                is HomeSideEffects.ClickFavoriteTrack -> {

                }
            }
        }

        viewModel.getTrackListResult.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Loading -> {
                    Log.d(TAG, "데이터를 로드중")
                }
                is Result.Success -> {
                    viewModel.insertTrack(it.data)
                    Log.d(TAG, "데이터 로딩 완료")
                    Log.d(TAG, "${it.data}")
                }
                is Result.Empty -> {
                    Log.d(TAG, "데이터가 없습니다.")
                }
                is Result.Error -> {
                    Log.d(TAG, "오류 발생: ${it.exception}")
                }
                else -> {}
            }
        }

        viewModel.trackList.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    homeAdapter.submitList(it.data)
                }
                is Result.Empty -> {
                    homeAdapter.submitList(emptyList())
                }
                is Result.Error -> {
                    toastMessage(resources.getString(R.string.content_error))
                }
                else -> {}
            }
        }
    }

    override fun initAfterBinding() {
        viewModel.getTrackList()
    }

    private fun initHomeAdapter() {
        binding.apply {
            rvHome.apply {
                adapter = homeAdapter

                // 페이징 기능
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)

                        if (!this@apply.canScrollVertically(1)) {   //최하단에 오면`
                            //원하는 동작
                            viewModel.nextOffset()
                            viewModel.getTrackList()
                        }

                    }
                })
            }
        }
    }
}