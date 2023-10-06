package com.mfarhan08a.simplelyricsapp.ui.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.mfarhan08a.simplelyricsapp.R
import com.mfarhan08a.simplelyricsapp.core.data.Resource
import com.mfarhan08a.simplelyricsapp.core.domain.model.Track
import com.mfarhan08a.simplelyricsapp.databinding.ActivityDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private val detailViewModel: DetailViewModel by viewModel()
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        @Suppress("DEPRECATION") val detailTrack = intent.getParcelableExtra<Track>(EXTRA_DATA)
        detailViewModel.setSelectedTrack(detailTrack!!)
        detailViewModel.trackItem.observe(this) { showDetailTrack(it) }
        detailViewModel.isFavorite.observe(this) { setFavoriteState(it) }
    }

    private fun showDetailTrack(detailTrack: Track?) {
        binding.apply {
            tvTrackName.text = detailTrack?.trackName
            tvTrackArtist.text = detailTrack?.artistName
            tvAlbumName.text = detailTrack?.albumName
            tvRating.text = "Rating: ${detailTrack?.trackRating}"
            tvGenre.text = if (detailTrack?.primaryGenres?.isNotEmpty()!!) "Genre: ${
                detailTrack.primaryGenres[0].musicGenre.musicGenreName
            }" else "Genre: -"

            fabShare.setOnClickListener {
                @Suppress("DEPRECATION")
                ShareCompat.IntentBuilder.from(this@DetailActivity).apply {
                    setType("text/plain")
                    setChooserTitle(detailTrack.trackName)
                    setText(detailTrack.trackShareUrl)
                    startChooser()
                }
            }

            if (detailTrack.hasLyrics != 0) {
                detailViewModel.getTrackLyric(detailTrack.trackId)
                    .observe(this@DetailActivity) { lyric ->
                        if (lyric != null) {
                            when (lyric) {
                                is Resource.Loading -> progressBar.visibility = View.VISIBLE
                                is Resource.Success -> {
                                    progressBar.visibility = View.GONE
                                    tvLyric.text = lyric.data?.lyricsBody
                                }
                                is Resource.Error -> {
                                    progressBar.visibility = View.GONE
                                    tvLyric.text =
                                        lyric.message ?: getString(R.string.something_wrong)
                                }
                            }
                        }
                    }
            } else {
                tvLyric.text = getString(R.string.no_lyric)
            }
        }
    }

    private fun setFavoriteState(isFav: Boolean) {
        binding.apply {
            fabFavorite.setOnClickListener {
                detailViewModel.setFavoriteTrack(isFav)

                Snackbar.make(
                    constrainLayout,
                    if (isFav) getString(R.string.deleted) else getString(R.string.added),
                    Snackbar.LENGTH_LONG
                )
                    .setBackgroundTint(
                        ContextCompat.getColor(
                            this@DetailActivity,
                            if (isFav) android.R.color.holo_red_dark else android.R.color.holo_green_light
                        )
                    )
                    .show()
            }

            fabFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this@DetailActivity,
                    if (isFav) R.drawable.ic_favorite else R.drawable.ic_favorite_border
                )
            )
        }
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}