package com.family.heropedia.models.localmodels

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "localFavHeroes",
)
data class localFavoriteHeroes(
    val fullname : String = "",
    val heroname : String = "",
    val thumbUrl : String = "",
    @PrimaryKey()
    val id : Long
) : Serializable
