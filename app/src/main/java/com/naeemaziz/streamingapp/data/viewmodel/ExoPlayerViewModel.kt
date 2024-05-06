package com.naeemaziz.streamingapp.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.naeemaziz.streamingapp.data.model.TvLink

class ExoPlayerViewModel : ViewModel() {
    private val _tvLink = MutableLiveData<TvLink>()
    val tvLink: LiveData<TvLink> = _tvLink

    fun setTvLink(link: TvLink) {
        _tvLink.value = link
    }
}