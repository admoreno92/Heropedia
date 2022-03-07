package com.family.heropedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.family.heropedia.R
import com.family.heropedia.models.Hero
import com.family.heropedia.models.Image
import com.family.heropedia.models.localmodels.localFavoriteHeroes

class FavHeroAdapter : RecyclerView.Adapter<FavHeroAdapter.HeroViewHolder>() {

    private lateinit var mListener : FavHeroAdapter.onItemClickListener
    inner class HeroViewHolder(itemView : View, listener : FavHeroAdapter.onItemClickListener) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<localFavoriteHeroes>() {
        override fun areItemsTheSame(oldItem: localFavoriteHeroes, newItem: localFavoriteHeroes): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: localFavoriteHeroes, newItem: localFavoriteHeroes): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    interface onItemClickListener {
        fun onItemClick (hero: localFavoriteHeroes)
    }

    fun setOnItemClickListener(listener : onItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hero_fav_item, parent,false)
        return HeroViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        val hero = differ.currentList[position]
        val favThumb = holder.itemView.findViewById<ImageView>(R.id.favHeroThumb)
        val txtFavFullName = holder.itemView.findViewById<TextView>(R.id.txtFavFullName)
        val txtFavDetails = holder.itemView.findViewById<TextView>(R.id.txtFavDetails)
        holder.itemView.apply {
            Glide.with(holder.itemView).load(hero.thumbUrl).into(favThumb)
            txtFavFullName.text = hero.fullname
            txtFavDetails.text = hero.heroname
            setOnClickListener {
                mListener.onItemClick(hero)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}