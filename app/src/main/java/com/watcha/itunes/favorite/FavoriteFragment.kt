package com.watcha.itunes.favorite

import androidx.fragment.app.viewModels
import com.watcha.itunes.R
import com.watcha.itunes.base.BaseFragment
import com.watcha.itunes.databinding.FragmentFavoriteBinding

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding, FavoriteViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_favorite

    override val viewModel: FavoriteViewModel by viewModels()

    override fun initStartView() {
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }
}