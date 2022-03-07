package com.family.heropedia.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Powerstats(
    val intelligence: String,
    val strength: String,
    val speed: String,
    val durability: String,
    val power: String,
    val combat: String
) : Serializable