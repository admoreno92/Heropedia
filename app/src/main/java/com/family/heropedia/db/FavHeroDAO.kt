package com.family.heropedia.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.family.heropedia.models.localmodels.localFavoriteHeroes

@Dao
interface FavHeroDAO {

    @Insert
    suspend fun insert(favoriteHeroes: localFavoriteHeroes) : Long
    @Query("SELECT * FROM localFavHeroes")
    fun getFavHeroes() : MutableList<localFavoriteHeroes>
    @Query("DELETE FROM localFavHeroes")
    suspend fun deleteAll()
    @Delete
    suspend fun delete(favoriteHeroes: localFavoriteHeroes)
}