package com.watcha.itunes.binding_adapter

import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.watcha.itunes.home.TrackPagingDataAdapter

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

    @BindingAdapter("app:starVisible")
    @JvmStatic
    fun ImageView.bindStarVisible(state: String){
        this.isVisible = state == "1"
    }
}
