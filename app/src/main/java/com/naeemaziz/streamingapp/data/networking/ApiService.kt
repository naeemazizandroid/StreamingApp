package com.naeemaziz.streamingapp.data.networking


import com.naeemaziz.streamingapp.data.model.TvLinksResponse
import retrofit2.http.GET

interface ApiService {
    @GET("tvlinks.json")
    suspend fun getTvLinks(): TvLinksResponse


}