package com.family.heropedia.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Work(
    val base: String,
    val occupation: String
) : Serializable