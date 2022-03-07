package com.family.heropedia.models

import androidx.room.Embedded
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Biography(
    @SerializedName("full-name") val fullName: String,
    @SerializedName("alter-egos")val alterEgos: String,
    val aliases: List<String>,
    @SerializedName("place-of-birth") val birthPlace: String,
    @SerializedName("first-appearance") val firstAppearance: String,
    val publisher: String,
    val alignment: String
) : Serializable