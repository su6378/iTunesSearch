package com.watcha.itunes.binding_adapter

import android.view.View
import androidx.databinding.BindingAdapter
import com.watcha.data.common.OnSingleClickListener

// 클릭 이벤트 관련 바인딩
object EventBindingAdapters {
    @JvmStatic
    @BindingAdapter("app:onSingleClick")
    fun View.bindOnSingleClickListener(clickListener: View.OnClickListener?) {
        clickListener?.also {
            // 클릭 중복을 막기위함, 정해진 interval마다 클릭
            setOnClickListener(OnSingleClickListener(it))
        } ?: setOnClickListener(null)
    }
}
