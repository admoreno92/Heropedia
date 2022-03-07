package com.family.heropedia.ui.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.family.heropedia.R
import com.family.heropedia.models.Hero
import com.family.heropedia.models.localmodels.localFavoriteHeroes
import com.family.heropedia.ui.HeroViewModel
import com.family.heropedia.ui.MainActivity
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.dialog_appearance.*
import kotlinx.android.synthetic.main.dialog_appearance.view.*
import kotlinx.android.synthetic.main.dialog_biography.*
import kotlinx.android.synthetic.main.dialog_biography.view.*
import kotlinx.android.synthetic.main.dialog_connections.*
import kotlinx.android.synthetic.main.dialog_connections.view.*
import kotlinx.android.synthetic.main.dialog_powerstats.view.*
import kotlinx.android.synthetic.main.dialog_work.*
import kotlinx.android.synthetic.main.dialog_work.view.*
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment(R.layout.fragment_details) {

    lateinit var viewModel : HeroViewModel
    val args : DetailsFragmentArgs by navArgs()
    lateinit var sharedPreferences : SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        val heroArgs = args.heroArgs
        sharedPreferences = activity?.getSharedPreferences(getString(R.string.sharedpreferences_key), Context.MODE_PRIVATE)!!
        initHeroHeader(heroArgs)
        initHeroDetails()
        btnAddToFav.setOnClickListener {
            val currList = sharedPreferences.getString("FavHeroes","")
            if (currList?.contains(heroArgs.id,ignoreCase = true)!!) {
                Snackbar.make(view,"Ya se encuentra en favoritos",Snackbar.LENGTH_SHORT).show()
            } else {
                val favHero = localFavoriteHeroes(
                    heroArgs.biography.fullName,
                    heroArgs.name,
                    heroArgs.image.url,
                    heroArgs.id.toLong()
                )
                viewModel.saveFavHero(favHero)
                sharedPreferences.edit().putString("FavHeroes","$currList${heroArgs.id},").apply()
                Snackbar.make(view,"Agregado a favoritos",Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun initHeroDetails(){
        btnShowStats.apply {
            setOnClickListener {
                setupDialog("powerstats")
            }
        }
        btnBio.apply {
            setOnClickListener {
                setupDialog("bio")
            }
        }
        btnAppearance.apply {
            setOnClickListener {
                setupDialog("appearance")
            }
        }
        btnWork.apply {
            setOnClickListener {
                setupDialog("work")
            }
        }
        btnConnections.apply {
            setOnClickListener {
                setupDialog("connections")
            }
        }
    }
    private fun initHeroHeader(heroArg: Hero) {
        txtFullName.text = heroArg.biography.fullName
        txtHero.text = heroArg.name
        txtAlterEgos.text = heroArg.biography.alterEgos
        txtBirthPlace.text = heroArg.biography.birthPlace
        txtHeight.text = "${heroArg.appearance.height[0]} / ${heroArg.appearance.height[1]}"
        txtWeight.text = "${heroArg.appearance.weight[0]} / ${heroArg.appearance.weight[1]}"
        txtAffiliation.apply {
            if (heroArg.biography.alignment == "good") {
                txtAffiliation.text = resources.getString(R.string.heroAlignment_good)
                    setTextColor(ContextCompat.getColor(context,R.color.HeroGood))
            } else if (heroArg.biography.alignment == "bad") {
                txtAffiliation.text = resources.getString(R.string.heroAlignment_evil)
                setTextColor(ContextCompat.getColor(context,R.color.HeroEvil))
            } else {
                txtAffiliation.text = resources.getString(R.string.heroAlignment_neutral)
                setTextColor(ContextCompat.getColor(context,R.color.HeroNeutral))
            }
        }
        Glide.with(this).load(heroArg.image.url).into(imgThumbHero)
    }
    private fun setupDialog(type : String) {
        var resourceView : Int = 0
        val dialog : AlertDialog
        val builder = AlertDialog.Builder(context)
        if (type == "powerstats") {
            resourceView = R.layout.dialog_powerstats
        } else if (type == "bio") {
            resourceView = R.layout.dialog_biography
        } else if (type == "appearance") {
            resourceView = R.layout.dialog_appearance
        } else if (type == "work") {
            resourceView = R.layout.dialog_work
        } else if (type == "connections") {
            resourceView = R.layout.dialog_connections
        }
        val viewContent = layoutInflater.inflate(resourceView,null)
        builder.setView(viewContent)
        setDetailsInViewContent(viewContent, resourceView)
        builder.setPositiveButton("Ok", DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.dismiss() })
        dialog = builder.create()
        dialog.show()
    }
    private fun setDetailsInViewContent(view : View, resourceView : Int){
        if (resourceView == R.layout.dialog_powerstats) {
            setHeroPowerStats(view)
        } else if (resourceView == R.layout.dialog_biography) {
            setHeroBio(view)
        } else if (resourceView == R.layout.dialog_appearance) {
            setHeroAppearance(view)
        } else if (resourceView == R.layout.dialog_work) {
            setHeroWork(view)
        } else if (resourceView == R.layout.dialog_connections) {
            setHeroConnections(view)
        }
    }
    private fun setHeroPowerStats(view : View){
        view.txtHeroIntelligence.text = args.heroArgs.powerstats.intelligence
        view.txtHeroStrenght.text = args.heroArgs.powerstats.strength
        view.txtHeroSpeed.text = args.heroArgs.powerstats.combat
        view.txtHeroDurability.text = args.heroArgs.powerstats.durability
        view.txtHeroPower.text = args.heroArgs.powerstats.power
        view.txtHeroCombat.text = args.heroArgs.powerstats.combat
    }
    private fun setHeroBio(view : View){
        val heroAlignment : String
        if (args.heroArgs.biography.alignment == "good") {
            heroAlignment = resources.getString(R.string.heroAlignment_good)
        } else if (args.heroArgs.biography.alignment == "bad") {
            heroAlignment = resources.getString(R.string.heroAlignment_evil)
        } else {
            heroAlignment = resources.getString(R.string.heroAlignment_neutral)
        }
        view.txtHeroDetails.text = "$heroAlignment / ${args.heroArgs.biography.publisher}"
        view.txtHeroAlteregos.text = args.heroArgs.biography.alterEgos
        view.txtHeroAlias.text = args.heroArgs.biography.aliases.toString()
        view.txtHeroBirthplace.text = args.heroArgs.biography.birthPlace
        view.txtHeroFirstApp.text = args.heroArgs.biography.firstAppearance
    }
    private fun setHeroAppearance(view : View){
        if (args.heroArgs.appearance.gender == "Female") {
            view.imgHeroGender.apply {
                setImageResource(R.drawable.ic_baseline_genderwoman)
            }
        } else if (args.heroArgs.appearance.gender == "Male") {
            view.imgHeroGender.apply {
                setImageResource(R.drawable.ic_baseline_genderman)
            }
        }
        view.txtHeroRace.text = args.heroArgs.appearance.race
        view.txtHeroHeight.text = "${args.heroArgs.appearance.height[0]} / ${args.heroArgs.appearance.height[0]}"
        view.txtHeroWeight.text = "${args.heroArgs.appearance.weight[0]} / ${args.heroArgs.appearance.weight[0]}"
        view.txtHeroEyesColor.text = args.heroArgs.appearance.eyeColor
        view.txtHeroHairColor.text = args.heroArgs.appearance.hairColor
    }
    private fun setHeroWork(view : View){
        view.txtHeroOcupation.text = args.heroArgs.work.occupation
        view.txtHeroBase.text = args.heroArgs.work.base
    }
    private fun setHeroConnections(view : View){
        view.txtHeroGroupAffiliation.text = args.heroArgs.connections.affiliation
        view.txtHeroRelatives.text = args.heroArgs.connections.relatives
    }
}