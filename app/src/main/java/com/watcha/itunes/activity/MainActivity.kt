package com.watcha.itunes.activity

import androidx.fragment.app.Fragment
import com.watcha.itunes.R
import com.watcha.itunes.base.BaseActivity
import com.watcha.itunes.databinding.ActivityMainBinding
import com.watcha.itunes.favorite.FavoriteFragment
import com.watcha.itunes.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_main

    override fun initStartView() {
        supportFragmentManager.beginTransaction().add(R.id.fcv_main, HomeFragment()).commit() // 바텀 네비게이션 DEFAULT 설정
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
        binding.apply {
            bnMain.setOnItemSelectedListener { // 바텀 네이게이션 화면 이동
                when (it.itemId) {
                    R.id.menu_home -> replaceFragment(HomeFragment())
                    R.id.menu_favorites -> replaceFragment(FavoriteFragment())
                }
                true
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().replace(R.id.fcv_main, fragment).commit()
}