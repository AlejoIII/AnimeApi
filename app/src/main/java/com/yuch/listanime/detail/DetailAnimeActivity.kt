package com.yuch.listanime.detail

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.yuch.listanime.R
import com.yuch.listanime.core.domain.model.Anime
import com.yuch.listanime.core.utils.formatDate
import com.yuch.listanime.core.utils.formatNumberWithLocale
import com.yuch.listanime.databinding.ActivityDetailAnimeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailAnimeActivity : AppCompatActivity() {
    // Inicializamos el ViewBinding
    private lateinit var binding: ActivityDetailAnimeBinding
    // Inyectamos el ViewModel
    private val detailAnimeViewModel: DetailAnimeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAnimeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Obtenemos el anime seleccionado
        val detailAnime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_DATA, Anime::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_DATA)
        }
        // Mostramos el detalle del anime
        showDetailAnime(detailAnime)
    }
    // Mostramos el detalle del anime
    private fun showDetailAnime(detailAnime: Anime?) {
        detailAnime?.let {
            supportActionBar?.title = detailAnime.title
            with(binding){
                Glide.with(this@DetailAnimeActivity)
                    .load(detailAnime.imageUrl)
                    .into(ivDetailImage)
                // Verificamos si el anime es favorito
                var statusFavorite: Boolean = detailAnime.isFavorite
                setStatusFavorite(statusFavorite)
                Log.d("DetailAnimeActivity", "Setting favorite: ${detailAnime.title}, state: $statusFavorite")
                // Cambiamos el estado del anime favorito
                fab.setOnClickListener {
                    statusFavorite = !statusFavorite
                    detailAnimeViewModel.setFavoriteAnime(detailAnime, statusFavorite)
                    setStatusFavorite(statusFavorite)
                }
                // Mostramos la información del anime
                content.tvScoreValue.text = detailAnime.score.toString()

                val rank = "Rank ${detailAnime.rank}"
                val popularity = "Popularity #${formatNumberWithLocale(detailAnime.popularity)}"
                val members = "Members ${formatNumberWithLocale(detailAnime.members)}"
                val season = "${detailAnime.season?.replaceFirstChar { it.uppercase() }} ${detailAnime.year}"

                content.tvRanked.text = rank
                content.tvPopularity.text = popularity
                content.tvMembers.text = members
                content.tvSeason.text = season
                content.tvType.text = detailAnime.type
                content.tvStudio.text = detailAnime.studio

                content.tvAnimeInfo.text = buildString {
                    append("Episodes: ${detailAnime.episodes ?: "N/A"}\n")
                    append("Status: ${detailAnime.status ?: "N/A"}\n")
                    append("Source: ${detailAnime.source ?: "N/A"}\n")
                    append("Aired: ${formatDate(detailAnime.airedFrom ?: "?")} to ${formatDate(detailAnime.airedTo ?: "?")}\n")
                    append("Duration: ${detailAnime.duration ?: "N/A"}\n")
                    append("Genre: ${detailAnime.genre ?: "N/A"}\n")
                    append("Theme: ${detailAnime.theme ?: "N/A"}\n")
                    append("Demographics: ${detailAnime.demographics ?: "N/A"}\n")
                    append("Rating: ${detailAnime.rating ?: "N/A"}\n")
                }

                content.tvSynopsis.text = detailAnime.synopsis
                content.tvBackground.text = detailAnime.background
            }
        }
    }
    // Cambiamos el estado del anime favorito
    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_white))
        } else {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_not_favorite_white))
        }
    }
    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}