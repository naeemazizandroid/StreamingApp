package com.naeemaziz.streamingapp.ui.view

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.naeemaziz.streamingapp.R
import com.naeemaziz.streamingapp.data.networking.RetrofitClient
import com.naeemaziz.streamingapp.data.repository.TvRepository
import com.naeemaziz.streamingapp.data.viewmodel.ExoPlayerViewModel
import com.naeemaziz.streamingapp.data.viewmodel.TvViewModel
import com.naeemaziz.streamingapp.data.viewmodel.TvViewModelFactory
import com.naeemaziz.streamingapp.ui.adapter.ListAdapter

class TvListFragment:Fragment() {
    private lateinit var viewModel: TvViewModel
    private val exoPlayerViewModel: ExoPlayerViewModel by activityViewModels()
    private val apiService = RetrofitClient.apiInterface
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
       val mView = inflater.inflate(R.layout.fragment_list, container, false)
        setupRecyclerView(mView)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       val repository = TvRepository(apiService)
        viewModel = ViewModelProvider(this, TvViewModelFactory(repository)).get(TvViewModel::class.java)

        viewModel.fetchTvLinks()
        viewModel.tvLinks.observe(viewLifecycleOwner, { links ->
            adapter.setData(links)
        })
    }

    private fun setupRecyclerView(mView:View) {
        recyclerView = mView.findViewById(R.id.recyclerView)
        adapter = ListAdapter(emptyList()) { tvLink ->
            exoPlayerViewModel.setTvLink(tvLink)
            findNavController().navigate(R.id.action_tvListFragment_to_tvExoPlayerFragment)

        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}