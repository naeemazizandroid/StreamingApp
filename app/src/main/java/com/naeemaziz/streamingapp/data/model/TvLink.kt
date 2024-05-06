package com.naeemaziz.streamingapp.data.model

import java.io.Serializable


data class TvLink(val Id: Int,
                  val Name: String,
                  val ImageUrl: String,
                  val Title: String,
                  val Description: String,
                  val Type: String? = null,
                  val Url: String,
                  val Host: String? = null,
                  val Origin: String? = null,
                  val Referer: String? = null,
                  val Authority: String? = null) : Serializable

