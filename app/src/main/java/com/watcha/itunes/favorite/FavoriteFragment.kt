package com.watcha.itunes.favorite

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.watcha.itunes.R
import com.watcha.itunes.base.BaseFragment
import com.watcha.itunes.databinding.FragmentFavoriteBinding
import kotlinx.coroutines.Job
import com.watcha.domain.Result
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "FavoriteFragment_싸피"
@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding, FavoriteViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_favorite

    override val viewModel: FavoriteViewModel by viewModels()
    private lateinit var job : Job
    private val favoriteAdapter = FavoriteAdapter()

    override fun initStartView() {
        collectFavoriteList()
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
        initAdapter()
    }

    private fun initAdapter(){
        binding.apply {
            rvFavorite.adapter = favoriteAdapter
        }
    }

    private fun jobUpdate(logic: () -> Unit){
        job.cancel()
        logic()
        collectFavoriteList()
    }

    private fun collectFavoriteList(){
        job = lifecycleScope.launchWhenStarted {
            viewModel.favoriteList.collect {
                if(it is Result.Success){
                    favoriteAdapter.submitList(it.data)
                    Log.d(TAG, "collectFavoriteList: ${it.data}")
                }else{

                }
            }
        }
    }
}