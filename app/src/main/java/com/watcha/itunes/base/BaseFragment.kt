package com.watcha.itunes.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

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

    /**
     * Loading Dialog 관련해서 사용할 변수
     */
    private val mLoadingDialog: LoadingDialogFragment by lazy { LoadingDialogFragment() }

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

        viewModel.loadingEvent.observe(viewLifecycleOwner) {
            if (it) showLoadingDialog()
            else dismissLoadingDialog()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        toast?.cancel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // 로딩 다이얼로그, 즉 로딩창을 띄워줌.
    // 네트워크가 시작될 때 사용자가 무작정 기다리게 하지 않기 위해 작성.
    private fun showLoadingDialog() {
        if (!mLoadingDialog.isAdded) mLoadingDialog.show(childFragmentManager, mLoadingDialog.tag)
    }

    // 띄워 놓은 로딩 다이얼로그를 없앰.
    private fun dismissLoadingDialog() {
        if (mLoadingDialog.isAdded) mLoadingDialog.dismiss()
    }

//    // Toast Message 관련 함수
//    fun errorToastMessage(e: Throwable?) {
//        toast?.cancel()
//        toast =
//            CustomToast.makeText(requireContext(), e?.cause?.message ?: "알 수 없는 에러가 발생했습니다.")?.apply { show() }
//    }
//
    // Toast Message 관련 함수
    fun toastMessage(message: String) {
        toast?.cancel()
        toast = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).apply { show() }
    }

    // 통신 에러 다이얼로그 생성
    fun showErrorDialog(
        dialog: Dialog,
        lifecycleOwner: LifecycleOwner?,
        cancelable: Boolean = true,
        dismissHandler: (() -> Unit)? = null,
    ) {
        val targetEvent = if (cancelable) Lifecycle.Event.ON_STOP else Lifecycle.Event.ON_DESTROY
        val observer = LifecycleEventObserver { _: LifecycleOwner, event: Lifecycle.Event ->
            if (event == targetEvent && dialog.isShowing) {
                dialog.dismiss()
                dismissHandler?.invoke()
            }
        }
        dialog.show()
        lifecycleOwner?.lifecycle?.addObserver(observer)
        dialog.setOnDismissListener { lifecycleOwner?.lifecycle?.removeObserver(observer) }
    }
}