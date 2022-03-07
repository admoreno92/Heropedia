package com.family.heropedia.db

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.family.heropedia.models.localmodels.localFavoriteHeroes

@Database(
    entities = [localFavoriteHeroes::class],
    version = 2
)
abstract class FavHeroDB : RoomDatabase() {

    abstract fun getFavHeroDAO() : FavHeroDAO

    companion object {
        @Volatile
        private var instance : FavHeroDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                FavHeroDB::class.java,
                "localhero.db"
            ).fallbackToDestructiveMigration().build()
    }

}