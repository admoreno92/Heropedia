package com.family.heropedia.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Connections(
    @SerializedName("group-affiliation") val affiliation: String,
    val relatives: String
) : Serializable