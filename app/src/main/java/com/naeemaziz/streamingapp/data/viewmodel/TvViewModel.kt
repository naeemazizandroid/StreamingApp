package com.naeemaziz.streamingapp.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.naeemaziz.streamingapp.data.model.TvLink
import com.naeemaziz.streamingapp.data.repository.TvRepository
import kotlinx.coroutines.launch

class TvViewModel(private val repository: TvRepository) : ViewModel() {

    private val _tvLinks = MutableLiveData<List<TvLink>>()
    val tvLinks: LiveData<List<TvLink>> = _tvLinks

    fun fetchTvLinks() {
        viewModelScope.launch {
            _tvLinks.value = repository.getTvLinks()
        }
    }
}

class TvViewModelFactory(private val repository: TvRepository) : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        if (modelClass.isAssignableFrom(TvViewModel::class.java))
            return TvViewModel(repository) as T

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}