package com.family.heropedia.ui.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.family.heropedia.R
import com.family.heropedia.adapter.HeroAdapter
import com.family.heropedia.models.Hero
import com.family.heropedia.ui.HeroViewModel
import com.family.heropedia.ui.MainActivity
import com.family.heropedia.util.Resource
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    lateinit var viewModel : HeroViewModel
    lateinit var heroAdapter : HeroAdapter
    lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        initRecyclerView()
        viewModel.heroData.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    if (response.data?.response == "error") {
                        setupErrorDialog("Personaje no encontrado","Ocurrió un error durante la búsqueda, intente de nuevo.")
                    }
                    response.data?.let { dataResponse ->
                        heroAdapter.differ.submitList(dataResponse.results)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message.let { message ->
                        setupErrorDialog("Error","Ocurrió un error durante la búsqueda, intente de nuevo.")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
        btnSearchHero.setOnClickListener {
            hideSoftKeyboard()
            val search = txtSearchHero.text.toString()
            if (search.isEmpty()) {
                setupErrorDialog("Error","Ocurrió un error durante la búsqueda, intente de nuevo.")
            } else {
                viewModel.getHeroByName(txtSearchHero.text.toString())
            }
        }
    }

    private fun initRecyclerView(){
        heroAdapter = HeroAdapter()
        heroAdapter.apply {
            setOnItemClickListener(object : HeroAdapter.onItemClickListener{
                override fun onItemClick(hero: Hero) {
                    val bundle = Bundle().apply {
                        putSerializable("heroArgs", hero)
                    }
                    findNavController().navigate(R.id.action_homeFragment_to_detailsFragment, bundle)
                }
            })
        }
        heroRecyclerView.adapter = heroAdapter
        heroRecyclerView.apply {
            adapter = heroAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
        heroRecyclerView.setHasFixedSize(true)
    }
    private fun setupErrorDialog(title : String, msg : String){
        val dialog : AlertDialog
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(msg)
        builder.setPositiveButton("Ok", DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.dismiss() })
        dialog = builder.create()
        dialog.show()
    }
    private fun showProgressBar(){
        searchProgressBar.visibility = View.VISIBLE
    }
    private fun hideProgressBar(){
        searchProgressBar.visibility = View.GONE
    }
    private fun hideSoftKeyboard (){
        val keyboard = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        keyboard.hideSoftInputFromWindow(requireView().windowToken,0)
    }
}