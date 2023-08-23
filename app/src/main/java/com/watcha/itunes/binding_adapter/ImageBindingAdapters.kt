package com.watcha.itunes.binding_adapter

import android.content.res.ColorStateList
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.watcha.itunes.R

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

    @BindingAdapter("app:starTint")
    @JvmStatic
    fun ImageView.bindStarTint(state: String){
        // state -> 별 이미지 색상 지정
        imageTintList = if (state == "0") ColorStateList.valueOf(ContextCompat.getColor(this.context, R.color.white))
        else ColorStateList.valueOf(ContextCompat.getColor(this.context, R.color.yellow))
    }
}
