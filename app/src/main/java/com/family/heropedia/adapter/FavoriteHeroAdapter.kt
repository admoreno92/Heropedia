package com.family.heropedia.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.family.heropedia.R
import com.family.heropedia.models.localmodels.localFavoriteHeroes

class FavoriteHeroAdapter : RecyclerView.Adapter<FavoriteHeroAdapter.FavViewHolder>() {

    private lateinit var mListener : FavoriteHeroAdapter.onItemClickListener
    inner class FavViewHolder(itemView : View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView)
    lateinit var data : MutableList<localFavoriteHeroes>
    lateinit var context : Context

    fun RecyclerAdapter(heroes : MutableList<localFavoriteHeroes>, context: Context){
        this.data = heroes
        this.context = context
    }

    interface onItemClickListener {
        fun onItemClick (hero: localFavoriteHeroes)
    }

    fun setOnItemClickListener(listener : onItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.hero_fav_item,parent,false)
        return FavViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        val hero = data[position]
        val favThumb = holder.itemView.findViewById<ImageView>(R.id.favHeroThumb)
        val txtFavFullName = holder.itemView.findViewById<TextView>(R.id.txtFavFullName)
        val txtFavDetails = holder.itemView.findViewById<TextView>(R.id.txtFavDetails)
        holder.apply {
            Glide.with(itemView).load(hero.thumbUrl).into(favThumb)
            txtFavFullName.text = hero.fullname
            txtFavDetails.text = hero.heroname
            itemView.setOnClickListener { mListener.onItemClick(hero) }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

}