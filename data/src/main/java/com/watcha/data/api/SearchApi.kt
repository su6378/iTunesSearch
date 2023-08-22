package com.watcha.data.api

import com.watcha.data.model.SearchResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface SearchApi {
    // Search API
    @GET("search/")
    suspend fun getTrackList(
        @Query("term") term: String,
        @Query("entity") entity: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<SearchResponseDto>
}