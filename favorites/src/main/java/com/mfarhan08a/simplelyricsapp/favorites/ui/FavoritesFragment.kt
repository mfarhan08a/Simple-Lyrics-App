package com.mfarhan08a.simplelyricsapp.favorites.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mfarhan08a.simplelyricsapp.core.ui.TrackAdapter
import com.mfarhan08a.simplelyricsapp.favorites.databinding.FragmentFavoritesBinding
import com.mfarhan08a.simplelyricsapp.favorites.di.favoritesModule
import com.mfarhan08a.simplelyricsapp.ui.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoritesFragment : Fragment() {

    private val favoritesViewModel: FavoritesViewModel by viewModel()

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        loadKoinModules(favoritesModule)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val trackAdapter = TrackAdapter()
            trackAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_DATA, selectedData)
                startActivity(intent)
            }

            favoritesViewModel.favoritesTrack.observe(viewLifecycleOwner) { favoritesTrack ->
                trackAdapter.setData(favoritesTrack)
                binding.viewEmpty.root.visibility = if (favoritesTrack.isNotEmpty()) View.GONE else View.VISIBLE
            }

            with(binding.rvFavoriteTrack) {
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