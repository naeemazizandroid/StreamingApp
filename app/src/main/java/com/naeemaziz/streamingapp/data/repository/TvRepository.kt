package com.naeemaziz.streamingapp.data.repository

import com.naeemaziz.streamingapp.data.model.TvLink
import com.naeemaziz.streamingapp.data.networking.ApiService

class TvRepository(private val apiService: ApiService) {

    suspend fun getTvLinks(): List<TvLink> {
        return apiService.getTvLinks().tvLinks
    }
}