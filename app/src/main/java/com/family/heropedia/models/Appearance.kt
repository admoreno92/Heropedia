package com.family.heropedia.models

import androidx.room.Embedded
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Appearance(
    val gender: String,
    val race: String,
    val height: List<String>,
    val weight: List<String>,
    @SerializedName("eye-color") val eyeColor: String,
    @SerializedName("hair-color") val hairColor: String
) : Serializable