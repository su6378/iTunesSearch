package com.watcha.itunes.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    private var _binding: T? = null
    val binding get() = requireNotNull(_binding)

    abstract val layoutResourceId: Int

    /**
     * 레이아웃을 띄운 직후 호출.
     * 뷰모델 초기화
     * 뷰나 액티비티의 속성 등을 초기화.
     * ex) 리사이클러뷰, 툴바, 드로어뷰..
     */
    abstract fun initStartView()

    /**
     * 두번째로 호출.
     * 데이터 바인딩 및 Coroutine 설정.
     * ex) lifecyelScope.launch{}, lifecycleScope.launchWhenStarted{] ..
     */
    abstract fun initDataBinding()

    /**
     * 바인딩 이후에 할 일을 여기에 구현.
     * 그 외에 설정할 것이 있으면 이곳에서 설정.
     * 클릭 리스너도 이곳에서 설정.
     */
    abstract fun initAfterBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = DataBindingUtil.setContentView(this, layoutResourceId)
        binding.lifecycleOwner = this

        initStartView()
        initDataBinding()
        initAfterBinding()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
