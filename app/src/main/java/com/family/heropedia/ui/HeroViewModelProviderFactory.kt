package com.family.heropedia.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.family.heropedia.repository.HeroRepository

class HeroViewModelProviderFactory(
    val repository: HeroRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HeroViewModel(repository) as T
    }
}