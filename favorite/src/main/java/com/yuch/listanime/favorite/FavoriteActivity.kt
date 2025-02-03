package com.yuch.listanime.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.yuch.listanime.core.ui.AnimeAdapter
import com.yuch.listanime.detail.DetailAnimeActivity
import com.yuch.listanime.favorite.databinding.ActivityFavoriteBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel: FavoriteViewModel by viewModel()
    private lateinit var animeAdapter: AnimeAdapter
    // Inicializamos el módulo de favoritos
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Cambiamos el título de la barra de acción
        supportActionBar?.title = "Favorite"
        // Cargamos el módulo de favoritos
        loadKoinModules(favoriteModule)
        // Inicializamos el adaptador
        animeAdapter = AnimeAdapter()
        animeAdapter.onItemClick = { selectedData ->
            val intent = Intent(this@FavoriteActivity, DetailAnimeActivity::class.java)
            intent.putExtra(DetailAnimeActivity.EXTRA_DATA, selectedData)
            startActivity(intent)
        }

        setupRecyclerView()
    }
    // Configuramos el RecyclerView
    private fun setupRecyclerView() {
        with(binding.rvAnime) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = animeAdapter
        }
    }
    // Actualizamos la lista de favoritos
    override fun onResume() {
        super.onResume()

        favoriteViewModel.favoriteAnime.removeObservers(this)
        favoriteViewModel.favoriteAnime.observe(this) { anime ->
            animeAdapter.submitList(anime)
            binding.viewEmpty.visibility =
                if (anime.isNotEmpty()) View.GONE else View.VISIBLE
        }
    }

}
