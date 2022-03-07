package com.family.heropedia.models

import androidx.room.*
import java.io.Serializable

@Entity(
    tableName = "heroDB"
)
data class Hero(
    @PrimaryKey(autoGenerate = true)
    val localId : Int,
    val id: String,
    val name: String,
    val powerstats: Powerstats,
    val biography: Biography,
    val appearance: Appearance,
    val work: Work,
    val connections: Connections,
    val image: Image
) : Serializable