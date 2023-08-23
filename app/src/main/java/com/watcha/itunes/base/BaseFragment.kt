package com.watcha.itunes.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<T : ViewDataBinding, R : BaseViewModel> : Fragment() {

    private var _binding: T? = null
    val binding get() = requireNotNull(_binding)

    abstract val layoutResourceId: Int

    abstract val viewModel: R

    /**
     * 첫번째로 호출.
     * 데이터 바인딩 및 Coroutine 설정.
     * ex) lifecyelScope.launch{}, lifecycleScope.launchWhenStarted{] ..
     */
    abstract fun initStartView()

    /**
     * 두번째로 호출.
     * 데이터 바인딩
     * ex) databinding observe..
     */
    abstract fun initDataBinding()

    /**
     * 바인딩 후 작업
     * ex) viewmodel에서 함수실행
     */
    abstract fun initAfterBinding()

    private var isSetBackButtonValid = false

    private var toast: Toast? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        initStartView()
        initDataBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAfterBinding()
    }

    override fun onDestroy() {
        super.onDestroy()
        toast?.cancel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Toast Message 관련 함수
    fun toastMessage(message: String) {
        toast?.cancel()
        toast = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).apply { show() }
    }
}