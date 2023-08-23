package com.watcha.itunes.binding_adapter

import android.view.View
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView

// 로티 관련 데이터 바인딩
object LottieBindingAdapters {
    @JvmStatic
    @BindingAdapter("app:loadingVisibility")
    fun LottieAnimationView.bindVisible(state: String) {
        visibility = if (state == "1") View.VISIBLE else View.GONE
    }
}
