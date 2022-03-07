package com.family.heropedia.repository

import com.family.heropedia.api.RetrofitInstance
import com.family.heropedia.db.FavHeroDB
import com.family.heropedia.models.Hero
import com.family.heropedia.models.localmodels.localFavoriteHeroes

class HeroRepository(
    val db : FavHeroDB
) {

    //Room
    suspend fun insertFavHero(localFavoriteHeroes: localFavoriteHeroes) = db.getFavHeroDAO().insert(localFavoriteHeroes)
    fun getFavHeroes() = db.getFavHeroDAO().getFavHeroes()
    suspend fun deleteFavHero(localFavoriteHeroes: localFavoriteHeroes) = db.getFavHeroDAO().delete(localFavoriteHeroes)
    suspend fun deleteAll() = db.getFavHeroDAO().deleteAll()

    //Network
    suspend fun getHeroByName(heroName : String) = RetrofitInstance.api.getHeroByName(heroName)
    suspend fun getHeroById(heroId : String) = RetrofitInstance.api.getHeroById(heroId)

}