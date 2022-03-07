package com.family.heropedia.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class svcHeroResponse(
    val response: String,
    val results: List<Hero>,
    val resultsForHero: String
) : Serializable