package com.naeemaziz.streamingapp.data.model

import com.google.gson.annotations.SerializedName

data class TvLinksResponse(@SerializedName("TvLinks") val tvLinks: List<TvLink>)
