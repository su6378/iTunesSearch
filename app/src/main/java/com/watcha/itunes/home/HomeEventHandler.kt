package com.watcha.itunes.home

import com.watcha.domain.model.Track

// 뷰모델 이외의 이벤트 처리
interface HomeEventHandler {
    fun favoriteClickEvent(track: Track)
}