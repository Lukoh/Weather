package com.goforer.fitpettest.presentation.stateholders.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.goforer.fitpettest.data.Params
import com.goforer.fitpettest.domain.home.GetCityWeatherUseCase
import com.goforer.fitpettest.presentation.stateholders.MediatorViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class GetCityWeatherViewModel
@AssistedInject
constructor(
    useCase: GetCityWeatherUseCase,
    @Assisted private val params: Params
) : MediatorViewModel(useCase, params) {
    @AssistedFactory
    interface AssistedVMFactory {
        fun create(params: Params): GetCityWeatherViewModel
    }

    companion object {
        fun provideFactory(assistedFactory: AssistedVMFactory, params: Params) =
            object :
                ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return assistedFactory.create(params) as T
                }
            }
    }
}