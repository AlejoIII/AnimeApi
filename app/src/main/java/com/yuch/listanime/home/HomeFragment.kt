package com.yuch.listanime.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.yuch.listanime.R
import com.yuch.listanime.core.Resource
import com.yuch.listanime.core.ui.AnimeAdapter
import com.yuch.listanime.databinding.FragmentHomeBinding
import com.yuch.listanime.detail.DetailAnimeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    // Inyectamos el ViewModel
    private val homeViewModel: HomeViewModel by viewModel()
    // Inicializamos el ViewBinding
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    // Mostramos el Fragment Home
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    // Mostramos la lista de anime
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null){
            val animeAdapter = AnimeAdapter()
            animeAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, DetailAnimeActivity::class.java)
                intent.putExtra(DetailAnimeActivity.EXTRA_DATA, selectedData)
                startActivity(intent)
            }
            // Observamos el estado del anime
            homeViewModel.anime.observe(viewLifecycleOwner) { anime ->
                if (anime != null) {
                    when (anime) {
                        is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                        is Resource.Success -> {
                            binding.progressBar.visibility = View.GONE
                            animeAdapter.submitList(anime.data)
                        }
                        is Resource.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.viewError.root.visibility = View.VISIBLE
                            binding.viewError.tvError.text = anime.message ?: getString(R.string.something_wrong)
                        }
                    }
                }
            }

            with(binding.rvAnime) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = animeAdapter
            }
        }
    }
    // Destruimos la vista
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}