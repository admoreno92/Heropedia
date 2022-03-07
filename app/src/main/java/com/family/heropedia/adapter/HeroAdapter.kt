package com.family.heropedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.family.heropedia.R
import com.family.heropedia.models.Hero
import kotlinx.android.synthetic.main.dialog_appearance.view.*
import kotlinx.android.synthetic.main.hero_item.view.*

class HeroAdapter (

) : RecyclerView.Adapter<HeroAdapter.HeroViewHolder>() {

    private lateinit var mListener : onItemClickListener
    inner class HeroViewHolder(itemView : View, listener : onItemClickListener) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Hero>() {
        override fun areItemsTheSame(oldItem: Hero, newItem: Hero): Boolean {
            return oldItem.id== newItem.id
        }

        override fun areContentsTheSame(oldItem: Hero, newItem: Hero): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    interface onItemClickListener {
        fun onItemClick (hero: Hero)
    }

    fun setOnItemClickListener(listener : onItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hero_item, parent,false)
        return HeroViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        val hero = differ.currentList[position]
        val thumbNail = holder.itemView.findViewById<ImageView>(R.id.heroThumb)
        val txtName = holder.itemView.findViewById<TextView>(R.id.txtHeroName)
        val txtFullName = holder.itemView.findViewById<TextView>(R.id.txtHeroFullName)
        val vwGender = holder.itemView.findViewById<View>(R.id.vwHeroGender)
        val imgPublisher = holder.itemView.findViewById<ImageView>(R.id.imgPublisher)
        holder.itemView.apply {
            Glide.with(holder.itemView).load(hero.image.url).into(thumbNail)
            txtFullName.text = hero.biography.fullName
            val algo = hero.biography.fullName
            val heroDetails : String
            if (hero.biography.alignment == "good") {
                heroDetails = "${hero.name} / ${context.getString(R.string.heroAlignment_good)}"
            } else if (hero.biography.alignment == "bad") {
                heroDetails = "${hero.name} / ${context.getString(R.string.heroAlignment_evil)}"
            } else {
                heroDetails = "${hero.name} / ${context.getString(R.string.heroAlignment_neutral)}"
            }
            txtName.text = heroDetails
            if (hero.appearance.gender == "Male") {
                vwGender.apply {
                    setBackgroundColor(ContextCompat.getColor(context,R.color.ManHeroBg))
                }
            } else if (hero.appearance.gender == "Female") {
                vwGender.apply {
                    setBackgroundColor(ContextCompat.getColor(context,R.color.WomanHeroBg))
                }
            } else {
                vwGender.apply {
                    setBackgroundColor(ContextCompat.getColor(context,R.color.HeroNeutral))
                }
            }
            if (hero.biography.publisher == "Marvel Comics") {
                imgPublisher.apply {
                    setImageResource(R.drawable.ic_marvel)
                }
            } else if (hero.biography.publisher == "DC Comics") {
                imgPublisher.apply {
                    setImageResource(R.drawable.ic_dccomics)
                }
            } else {
                imgPublisher.apply {
                    visibility = View.INVISIBLE
                }
            }
            setOnClickListener {
                mListener.onItemClick(hero)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}