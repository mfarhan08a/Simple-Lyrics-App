package com.mfarhan08a.simplelyricsapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mfarhan08a.simplelyricsapp.R
import com.mfarhan08a.simplelyricsapp.core.data.Resource
import com.mfarhan08a.simplelyricsapp.core.ui.TrackAdapter
import com.mfarhan08a.simplelyricsapp.databinding.FragmentHomeBinding
import com.mfarhan08a.simplelyricsapp.ui.detail.DetailActivity
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @OptIn(ObsoleteCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val trackAdapter = TrackAdapter()
            trackAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_DATA, selectedData)
                startActivity(intent)
            }

            binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    lifecycleScope.launch {
                        homeViewModel.queryChannel.send(p0.toString())
                    }
                    return true
                }
            })

            homeViewModel.track.observe(viewLifecycleOwner) { track ->
                if (track != null) {
                    when (track) {
                        is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                        is Resource.Success -> {
                            binding.progressBar.visibility = View.GONE
                            trackAdapter.setData(track.data)

                        }
                        is Resource.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.viewError.root.visibility = View.VISIBLE
                            binding.viewError.tvError.text =
                                track.message ?: getString(R.string.something_wrong)
                        }
                    }
                }
            }

            homeViewModel.search.observe(viewLifecycleOwner) { searchResult ->
                searchResult.asLiveData().observe(viewLifecycleOwner) {
                    if (it != null) {
                        when (it) {
                            is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                            is Resource.Success -> {
                                binding.progressBar.visibility = View.GONE
                                trackAdapter.setData(it.data)
                            }
                            is Resource.Error -> {
                                binding.progressBar.visibility = View.GONE
                                binding.viewError.root.visibility = View.VISIBLE
                                binding.viewError.tvError.text =
                                    it.message ?: "There is no track found.."
                            }

                        }
                    }
                }
            }

            with(binding.rvTrack) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = trackAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}