package com.watcha.itunes.favorite

import android.util.Log
import androidx.fragment.app.viewModels
import com.watcha.itunes.R
import com.watcha.itunes.base.BaseFragment
import com.watcha.itunes.databinding.FragmentFavoriteBinding
import com.watcha.domain.Result
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "FavoriteFragment_싸피"
@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding, FavoriteViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_favorite

    override val viewModel: FavoriteViewModel by viewModels()

    private val favoriteAdapter by lazy { FavoriteAdapter(viewModel) }

    override fun initStartView() {
        initAdapter()
    }

    override fun initDataBinding() {
        viewModel.favoriteSideEffect.observe(viewLifecycleOwner) {
            when (it) {
                is FavoriteSideEffects.ClickDeleteFavoriteTrack -> {
                    viewModel.updateTrack()
                }
            }
        }

        viewModel.favoriteList.observe(viewLifecycleOwner) {
            Log.d(TAG, "initDataBinding: $it")
            when (it) {
                is Result.Success -> { // 데이터가 갱신될 때마다 adapter에 갱신
                    favoriteAdapter.submitList(it.data)
                }
                is Result.Empty -> { // 데이터가 없으면 toast 메세지 출력
                    favoriteAdapter.submitList(emptyList())
                    toastMessage(resources.getString(R.string.content_no_data))
                }
                else -> {}
            }
        }
    }

    override fun initAfterBinding() {}

    private fun initAdapter() {
        binding.apply {
            rvFavorite.adapter = favoriteAdapter
        }
    }
}