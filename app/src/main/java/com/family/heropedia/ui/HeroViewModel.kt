package com.family.heropedia.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.family.heropedia.models.Hero
import com.family.heropedia.models.localmodels.localFavoriteHeroes
import com.family.heropedia.models.svcHeroResponse
import com.family.heropedia.repository.HeroRepository
import com.family.heropedia.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class HeroViewModel(
    val heroRepository : HeroRepository
) : ViewModel() {
    val heroData : MutableLiveData<Resource<svcHeroResponse>> = MutableLiveData()
    val svcData : MutableLiveData<Resource<Hero>> = MutableLiveData()

    //Network
    fun getHeroByName(heroName : String) {
        viewModelScope.launch {
            heroData.postValue(Resource.Loading())
            val svcResponse = heroRepository.getHeroByName(heroName)
            heroData.postValue(handleHeroByNameResponse(svcResponse))
        }
    }
    fun getHeroById(heroId : String) {
        viewModelScope.launch {
            val svcResponse = heroRepository.getHeroById(heroId)
            svcData.postValue(handleHeroByIdResponse(svcResponse))
        }
    }

    private fun handleHeroByNameResponse(svcResponse: Response<svcHeroResponse>): Resource<svcHeroResponse> {
        if (svcResponse.isSuccessful) {
            if (svcResponse.body() != null) {
                return Resource.Success(svcResponse.body()!!)
            }
        }
        return Resource.Error(svcResponse.message())
    }
    private fun handleHeroByIdResponse(svcResponse: Response<Hero>) : Resource<Hero> {
        if (svcResponse.isSuccessful) {
            if (svcResponse.body() != null) {
                return Resource.Success(svcResponse.body()!!)
            }
        }
        return Resource.Error(svcResponse.message())
    }


    //Room
    fun saveFavHero(favHero: localFavoriteHeroes) = viewModelScope.launch {
        heroRepository.insertFavHero(favHero )
    }
    fun getFavHeroes() = heroRepository.getFavHeroes()
    fun deleteFavHero(favHero: localFavoriteHeroes) = viewModelScope.launch {
        heroRepository.deleteFavHero(favHero)
    }
    fun deleteAll() = viewModelScope.launch {
        heroRepository.deleteAll()
    }
}