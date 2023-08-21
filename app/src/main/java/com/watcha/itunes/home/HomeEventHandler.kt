package com.watcha.itunes.home

// 뷰모델 이외의 이벤트 처리
interface HomeEventHandler {
    fun favoriteClickEvent(questionId: Int)
}