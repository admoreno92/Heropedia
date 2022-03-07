package com.family.heropedia.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import com.family.heropedia.R
import com.family.heropedia.adapter.FavHeroAdapter
import com.family.heropedia.adapter.FavoriteHeroAdapter
import com.family.heropedia.models.localmodels.localFavoriteHeroes
import com.family.heropedia.ui.HeroViewModel
import com.family.heropedia.ui.MainActivity
import com.family.heropedia.util.Resource
import kotlinx.android.synthetic.main.fragment_favheroes.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class FavoriteHeroes : Fragment(R.layout.fragment_favheroes) {

    lateinit var viewModel : HeroViewModel
    val favAdapter = FavoriteHeroAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        Thread(Runnable {
            val favHero = viewModel.getFavHeroes()
            (activity as MainActivity).runOnUiThread(Runnable {
                loadingFavHeroes.visibility = View.GONE
                rvFavHeroes.visibility = View.VISIBLE
                favAdapter.apply {
                    setOnItemClickListener(object : FavoriteHeroAdapter.onItemClickListener{
                        override fun onItemClick(hero: localFavoriteHeroes) {
                            runBlocking {
                                viewModel.getHeroById(hero.id.toString())
                                launch {
                                    viewModel.svcData.observe(viewLifecycleOwner, Observer {
                                        when (it) {
                                            is Resource.Success -> {
                                                val bundle = Bundle().apply {
                                                    putSerializable("heroArgs", it.data)
                                                }
                                                viewModel.svcData.postValue(null)
                                                val sharedPreferences = activity?.getSharedPreferences(getString(R.string.sharedpreferences_key), Context.MODE_PRIVATE)!!
                                                sharedPreferences.edit().putString("favHeroIds","${hero.id},").apply()
                                                findNavController().navigate(R.id.action_favoriteHeroes_to_detailsFragment, bundle)
                                            }
                                        }
                                    })
                                }
                            }
                        }
                    })
                }
                rvFavHeroes.apply {
                    setHasFixedSize(true)
                    layoutManager = GridLayoutManager(context, 2)
                    favAdapter.RecyclerAdapter(favHero,context)
                    adapter = favAdapter
                }
            })
        }).start()
    }
}