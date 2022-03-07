package com.family.heropedia.api

import com.family.heropedia.models.Hero
import com.family.heropedia.models.svcHeroResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface HeroAPI {

    @GET("search/{name}")
    suspend fun getHeroByName(
        @Path("name") heroName : String
    ) : Response<svcHeroResponse>

    @GET("{characterid}")
    suspend fun getHeroById (
        @Path("characterid") heroId: String
    ) : Response<Hero>

}