package com.watcha.itunes.binding_adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

// 이미지 관련 데이터 바인딩
object ImageBindingAdapters {
    @JvmStatic
    @BindingAdapter("app:imgByUrl")
    fun ImageView.bindImageViewByUrl(url: String?) {
        // Glide를 통한 이미지 적용
        Glide.with(this.context)
            .load(url)
            .into(this)
    }
}
